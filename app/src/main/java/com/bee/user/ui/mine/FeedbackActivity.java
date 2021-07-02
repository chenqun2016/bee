package com.bee.user.ui.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoEngine;
import com.bee.user.R;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.UploadImageBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.FoodChooseTypeTagsAdapter;
import com.bee.user.ui.adapter.GridImageAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.MyExecutors;
import com.bee.user.widget.FlowTagLayout;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bee.user.bean.DictByTypeBean.TYPE_FEEDBACK_TYPE;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  15：04
 * 描述：
 */
public class FeedbackActivity extends BaseActivity implements GridImageAdapter.onAddPicClickListener {
    @BindView(R.id.tags)
    FlowTagLayout tags;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.tv_paizhao)
    TextView tv_paizhao;

    private FoodChooseTypeTagsAdapter<DictByTypeBean> tagsAdapter = null;
    private GridImageAdapter gridImageAdapter;
    private int size = 0;
    private List<String> urlList = new ArrayList<>();
    private String dictKey = "";

    @OnClick({R.id.tv_agree,R.id.tv_paizhao})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                Editable text = et_phone.getText();
                if(!CommonUtil.isMobileNoAll(text)){
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                toSubmit();
                break;
            case R.id.tv_paizhao://上传凭证 最多6张
                openPhoto();
                break;
        }
    }

    private void toSubmit() {
        urlList.clear();
        size = gridImageAdapter.getData().size();
        if (size > 0) {
            showLoadingDialog();
                for (int i = 0; i < size; i++) {
                    LocalMedia media = gridImageAdapter.getData().get(i);
                    MyExecutors.getInstance().execute(() -> upImageToOss(media));
                }
            }else {
            toSubmitFeedback();
        }
        }

    /**
     * 上传图片
      * @param media
     */
    private void upImageToOss(LocalMedia media) {
        String path = "";
        if(media.isCompressed()) {
            path = media.getCompressPath();
        }else {
            if(TextUtils.isEmpty(media.getAndroidQToPath())) {
                path = media.getPath();
            }else {
                path = media.getAndroidQToPath();
            }
        }
        File file = new File(path);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.getClient(HttpRequest.baseUrl_file).uploadObj(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UploadImageBean>() {
                    @Override
                    public void onSuccess(UploadImageBean uploadImageBean) {
                        if(uploadImageBean != null) {
                            urlList.add(uploadImageBean.getUrl());
                            if(urlList.size() == size) {
                                runOnUiThread(FeedbackActivity.this::toSubmitFeedback);

                            }
                        }
                    }
                });
    }

    /**
     * 提交反馈
     */
    private void toSubmitFeedback() {
        Map map = new HashMap<>();
        map.put("content", et_content.getText().toString().trim());
        map.put("feedbackType", Integer.parseInt(dictKey));
        if(urlList.size()>0) {
            map.put("images", urlList);
        }
        map.put("mobile", et_phone.getText().toString().trim());
        map.put("realName", et_name.getText().toString().trim());
        Api.getClient(HttpRequest.baseUrl_sys).submitFeedback(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        closeLoadingDialog();
                        showCancelConfirmDialog();
                    }
                });
    }

    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(PicassoEngine.createPicassoEngine())
                .setPictureStyle(PictureParameterStyle.ofSelectTotalStyle())
                .maxSelectNum(6)
                .isCompress(true)// 是否压缩
                .compressQuality(100)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .selectionData(gridImageAdapter.getData())// 是否传入已选图片
                .minimumCompressSize(500)// 小于多少kb的图片不压缩
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if(result.size()==0) {
                            return;
                        }
                        tv_paizhao.setVisibility(View.GONE);
                        rc_view.setVisibility(View.VISIBLE);
                        gridImageAdapter.setList(result);
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        initTags();
        initImage();
        initOther();
        toDictByType();
    }

    private void initImage() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        gridImageAdapter = new GridImageAdapter(this, this);
        rc_view.setLayoutManager(gridLayoutManager);
        rc_view.setAdapter(gridImageAdapter);
    }

    /**
     * 获取反馈类型
     */
    private void toDictByType() {
        Api.getClient(HttpRequest.baseUrl_sys).getDictByType(TYPE_FEEDBACK_TYPE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DictByTypeBean>>() {
                    @Override
                    public void onSuccess(List<DictByTypeBean> dictByType) {
                        if(null != dictByType && dictByType.size()>0){
                            tagsAdapter.onlyAddAll(dictByType);
                            dictKey = dictByType.get(0).getDictKey();
                        }
                    }
                });
    }

    private void initOther() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_error.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(et_content);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(et_name);
        InitialValueObservable<CharSequence> c3 = RxTextView.textChanges(et_phone);

        Observable.combineLatest(c1, c2,c3, (Function3<CharSequence, CharSequence, CharSequence, Boolean>) (charSequence, charSequence2, charSequence3) -> {

            return !TextUtils.isEmpty(charSequence)
                    && !TextUtils.isEmpty(charSequence2)
                    && !TextUtils.isEmpty(charSequence3);
        }).subscribe(new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                setButtonStatus(aBoolean);
            }
        });
    }

    private void initTags() {
        tagsAdapter = new FoodChooseTypeTagsAdapter<>(this);

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener((parent, view, position) -> {


        });
        tags.setOnTagSelectListener((parent, selectedList) -> {
            Integer integer = selectedList.get(0);
            DictByTypeBean dictByTypeBean = (DictByTypeBean) tagsAdapter.getItem(integer);
            dictKey = dictByTypeBean.getDictKey();
        });

    }


    private void setButtonStatus(Boolean aBoolean) {
        if (aBoolean) {
            tv_agree.setEnabled(true);
            tv_agree.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            tv_agree.setEnabled(false);
            tv_agree.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }


    //    取消订单dialog
    private  void showCancelConfirmDialog() {
        Dialog dialog = new Dialog(this, R.style.loadingDialogTheme);
        View inflate = View.inflate(this, R.layout.dialog_feedback, null);
        inflate.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                    finish();
                }
            }
        });


        dialog.setContentView(inflate);
        dialog.show();
    }

    @Override
    public void onAddPicClick() {
        openPhoto();
    }

    @Override
    public void oDelete() {
        tv_paizhao.setVisibility(View.VISIBLE);
        rc_view.setVisibility(View.GONE);
    }
}
