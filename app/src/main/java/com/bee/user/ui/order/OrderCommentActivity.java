package com.bee.user.ui.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoEngine;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.OrderDetailBean;
import com.bee.user.bean.UploadImageBean;
import com.bee.user.params.CommentParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.GridImageAdapter;
import com.bee.user.ui.adapter.OrderCommentFoodAdapter;
import com.bee.user.ui.adapter.TagsOrderCommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.MyExecutors;
import com.bee.user.widget.FlowTagLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bee.user.bean.DictByTypeBean.TYPE_ORDER_APPRAISE;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  16：07
 * 描述：
 */
public class OrderCommentActivity extends BaseActivity implements GridImageAdapter.onAddPicClickListener {

    public static final int TYPE_NEW_COMMENT = 0;
    public static final int TYPE_RE_COMMENT = 1;
    public static final int TYPE_NO_COMMENT = 2;


    String[] starDes = {"非常差", "差", "一般", "满意", "超赞"};

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.iv_name)
    TextView iv_name;

    @BindView(R.id.iv_goods_img)
    ImageView iv_goods_img;


    @BindView(R.id.tags)
    FlowTagLayout tags;


    @BindView(R.id.tv_tijiao)
    TextView tv_tijiao;

    @BindView(R.id.iv_pinfen)
    TextView iv_pinfen;
    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.ratin_text1)
    TextView ratin_text1;
    @BindView(R.id.ratin_text2)
    TextView ratin_text2;
    @BindView(R.id.ratin_text3)
    TextView ratin_text3;

    @BindView(R.id.tv_num)
    TextView tv_num;

    @BindView(R.id.et_people_content)
    EditText et_people_content;


    @BindView(R.id.cb_1)
    CheckBox cb_1;

    @BindView(R.id.ratin1)
    MaterialRatingBar ratin1;

    @BindView(R.id.ratin2)
    MaterialRatingBar ratin2;

    @BindView(R.id.ratin3)
    MaterialRatingBar ratin3;

    @BindView(R.id.tv_paizhao)
    TextView tv_paizhao;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    OrderCommentFoodAdapter orderCommentFoodAdapter;
    TagsOrderCommentAdapter<DictByTypeBean> tagsAdapter;
    int id;
    int storeId;
    int type;

    public static void newInstance(Context context, int id, int storeId, OrderDetailBean orderDetailBean, int type) {
        Intent intent = new Intent(context, OrderCommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("storeId", storeId);
        intent.putExtra("orderDetailBean", (Serializable) orderDetailBean);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_tijiao, R.id.tv_paizhao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tijiao:
                toSubmit();
                break;
            case R.id.tv_paizhao:
                openPhoto();
                break;
        }

    }

    private int size = 0;
    private List<String> urlList = new ArrayList<>();

    //提交图片到服务器
    private void toSubmit() {
        urlList.clear();
        size = gridImageAdapter.getData().size();
        if (size > 0) {
            showLoadingDialog();
            for (int i = 0; i < size; i++) {
                LocalMedia media = gridImageAdapter.getData().get(i);
                MyExecutors.getInstance().execute(() -> upImageToOss(media));
            }
        } else {
            toSubmitComment();
        }
    }

    //提交评论
    private void toSubmitComment() {

        CommentParams commentParams = new CommentParams();
        commentParams.content = et_content.getText().toString();
        commentParams.orderId = id;
        commentParams.isAnonymous = cb_1.isChecked() ? 1 : 0;
        commentParams.star = (int) ratin1.getRating();
        StringBuilder builder = new StringBuilder();
        for (String s : urlList) {
            builder.append(s + ",");
        }
        commentParams.pics = builder.toString();
        commentParams.storeId = storeId;

        List<CommentParams.DetailsBean> details = new ArrayList<>();

        List<FoodBean> data = orderCommentFoodAdapter.getData();


        CommentParams.DetailsBean detailsBean1 = new CommentParams.DetailsBean();
        detailsBean1.commentObjId = 1;
        detailsBean1.commentObj = "包装";
        detailsBean1.give = ratin2.getRating();
        detailsBean1.commentType = 1;
        details.add(detailsBean1);
        CommentParams.DetailsBean detailsBean2 = new CommentParams.DetailsBean();
        detailsBean2.commentObjId = 2;
        detailsBean2.commentObj = "味道";
        detailsBean2.give = ratin2.getRating();
        detailsBean2.commentType = 1;
        details.add(detailsBean2);
        for (FoodBean bean : data) {
            if (bean.commentType == -1) {
                continue;
            }
            CommentParams.DetailsBean detailsBean = new CommentParams.DetailsBean();
            detailsBean.commentObjId = bean.id;
            detailsBean.commentObj = bean.productName;
            detailsBean.give = bean.commentType;
            detailsBean.commentType = 2;
            details.add(detailsBean);
        }
        commentParams.details = details;

        Api.getClient(HttpRequest.baseUrl_eva).commentCreate(Api.getRequestBody(commentParams))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object str) {
                        closeLoadingDialog();
                        startActivity(new Intent(OrderCommentActivity.this, OrderCommentStatusActivity.class));
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        closeLoadingDialog();
                    }
                });
    }

    /**
     * 上传图片
     *
     * @param media
     */
    private void upImageToOss(LocalMedia media) {
        String path = "";
        if (media.isCompressed()) {
            path = media.getCompressPath();
        } else {
            if (TextUtils.isEmpty(media.getAndroidQToPath())) {
                path = media.getPath();
            } else {
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
                        if (uploadImageBean != null) {
                            urlList.add(uploadImageBean.getUrl());
                            if (urlList.size() == size) {
                                runOnUiThread(OrderCommentActivity.this::toSubmitComment);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        closeLoadingDialog();
                    }
                });
    }

    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(PicassoEngine.createPicassoEngine())
                .setPictureStyle(PictureParameterStyle.ofSelectTotalStyle())
                .maxSelectNum(4)
                .isCompress(true)// 是否压缩
                .compressQuality(100)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .selectionData(gridImageAdapter.getData())// 是否传入已选图片
                .minimumCompressSize(500)// 小于多少kb的图片不压缩
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调

                        if (result.size() == 0) {
                            return;
                        }
                        tv_paizhao.setVisibility(View.GONE);
                        rc_view.setVisibility(View.VISIBLE);
                        gridImageAdapter.setList(result);
                        gridImageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        storeId = intent.getIntExtra("storeId", 0);
        type = intent.getIntExtra("type", 0);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        orderCommentFoodAdapter = new OrderCommentFoodAdapter();
        recyclerview.setAdapter(orderCommentFoodAdapter);

        try {
            Serializable bean = getIntent().getSerializableExtra("orderDetailBean");
            if (null != bean) {
                OrderDetailBean list = (OrderDetailBean) bean;
                orderCommentFoodAdapter.setNewInstance(list.orderItemList);
                iv_name.setText(list.storeName);
//                Picasso.with(this)
//                        .load(list.)
//                        .fit()
//                        .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,100),0, PicassoRoundTransform.CornerType.ALL))
//                        .into(iv_goods_img);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        tagsAdapter = new TagsOrderCommentAdapter<>(this);

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tags.setAdapter(tagsAdapter);


        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {

            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (null == selectedList || selectedList.size() == 0) {
                    et_people_content.setText("");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (Integer i : selectedList) {
                    sb.append(((DictByTypeBean) tagsAdapter.getItem(i)).getDictValue());
                    sb.append(" ");
                    et_people_content.setText(sb.toString());
                }
            }
        });

        et_content.setSelection(et_content.getText().length());

        String strs = "评论文字/图片 ";
        SpannableString msp1 = new SpannableString(strs + "12积分");
        msp1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), strs.length(), msp1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_num.setText(msp1);


        ratin1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratin_text1.setText(starDes[(int) (rating == 0 ? 0 : rating - 1)]);
            }
        });
        ratin2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratin_text2.setText(starDes[(int) (rating == 0 ? 0 : rating - 1)]);
            }
        });
        ratin3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratin_text3.setText(starDes[(int) (rating == 0 ? 0 : rating - 1)]);
            }
        });
        toDictByType();

        initImage();

        if (type == TYPE_RE_COMMENT || type == TYPE_NO_COMMENT) {
            getCommentData();
        }
    }

    private void getCommentData() {
        Api.getClient(HttpRequest.baseUrl_eva).queryCommentByOrder(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CommentBean>() {
                    @Override
                    public void onSuccess(CommentBean bean) {
                        setCommentData(bean);
                    }
                });
    }

    private void setCommentData(CommentBean bean) {
        if (null != bean && null != bean.eva && null != bean.details) {
            CommentBean.EvaBean eva = bean.eva;
            et_content.setText(eva.content);
            Picasso.with(iv_goods_img.getContext())
                    .load(eva.icon)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                    .into(iv_goods_img);
            cb_1.setChecked(bean.eva.isAnonymous != 0);
            iv_name.setText(eva.storeName);
            iv_pinfen.setText("综合评分" + eva.star);
            ratin1.setRating(eva.star);
            ratin_text1.setText(starDes[eva.star - 1]);
            List<FoodBean> data = new ArrayList<>();
            for (CommentBean.Detail detail : bean.details) {
                if (detail.commentType == 1) {
                    if (detail.commentObjId == 1) {
                        ratin2.setRating(detail.give);
                        ratin_text2.setText(starDes[detail.give - 1]);
                    } else if (detail.commentObjId == 2) {
                        ratin3.setRating(detail.give);
                        ratin_text3.setText(starDes[detail.give - 1]);
                    }
                } else {
                    FoodBean foodBean = new FoodBean();
                    foodBean.productName = detail.commentObj;
                    foodBean.commentType = detail.give;
                    data.add(foodBean);
                }
            }
            orderCommentFoodAdapter.setNewInstance(data);
            String pics = eva.pics;
            String[] picss = pics.split(",");

            List<String> strings = Arrays.asList(picss);
            if (strings.size() > 0) {
                rc_view.setVisibility(View.VISIBLE);
//                gridImageAdapter.setList(strings);
            } else {
                rc_view.setVisibility(View.GONE);
            }
        }
    }

    private GridImageAdapter gridImageAdapter;

    private void initImage() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        gridImageAdapter = new GridImageAdapter(this, this);
        gridImageAdapter.setSelectMax(4);
        rc_view.setLayoutManager(gridLayoutManager);
        rc_view.setAdapter(gridImageAdapter);
    }

    /**
     * 获取反馈类型
     */
    private void toDictByType() {
        Api.getClient(HttpRequest.baseUrl_sys).getDictByType(TYPE_ORDER_APPRAISE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DictByTypeBean>>() {
                    @Override
                    public void onSuccess(List<DictByTypeBean> dictByType) {
                        if (null != dictByType && dictByType.size() > 0) {
                            tagsAdapter.onlyAddAll(dictByType);
                        }
                    }
                });
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
