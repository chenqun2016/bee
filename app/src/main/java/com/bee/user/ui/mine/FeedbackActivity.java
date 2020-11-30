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

import com.bee.user.PicassoEngine;
import com.bee.user.R;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.ui.adapter.FoodChooseTypeTagsAdapter;
import com.bee.user.ui.adapter.TagsOrderCommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.login.ResetPasswordActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.widget.FlowTagLayout;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function3;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  15：04
 * 描述：
 */
public class FeedbackActivity extends BaseActivity {
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

    @OnClick({R.id.tv_agree,R.id.tv_paizhao})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                Editable text = et_phone.getText();
                if(!CommonUtil.isMobileNoAll(text)){
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                showCancelConfirmDialog();

                break;
            case R.id.tv_paizhao://上传凭证 最多6张
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(PicassoEngine.createPicassoEngine())
                        .setPictureStyle(PictureParameterStyle.ofSelectTotalStyle())
                        .maxSelectNum(6)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                // 结果回调
                            }

                            @Override
                            public void onCancel() {
                                // 取消
                            }
                        });
                break;

        }

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

        initOther();
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
        FoodChooseTypeTagsAdapter<String> tagsAdapter = new FoodChooseTypeTagsAdapter<>(this);

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {


            }
        });
        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

            }
        });
        List<String> dataSource = new ArrayList<>();
        dataSource.add("软件使用问题");
        dataSource.add("会员问题");
        dataSource.add("物流问题");
        dataSource.add("找不到店铺/商品");
        tagsAdapter.onlyAddAll(dataSource);
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
                }
            }
        });


        dialog.setContentView(inflate);
        dialog.show();
    }
}
