package com.bee.user.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.OrderCommentFoodAdapter;
import com.bee.user.ui.adapter.TagsOrderCommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 创建时间：2021/9/19
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class MyCommentDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.iv_name)
    TextView iv_name;

    @BindView(R.id.ll_store_reply)
    LinearLayout ll_store_reply;


    @BindView(R.id.iv_goods_img)
    ImageView iv_goods_img;


    @BindView(R.id.et_content)
    TextView et_content;

    @BindView(R.id.tv_store_reply)
    TextView tv_store_reply;
    @BindView(R.id.iv_pinfen)
    TextView iv_pinfen;


    @BindView(R.id.ratin1)
    MaterialRatingBar ratin1;
    @BindView(R.id.ratin2)
    MaterialRatingBar ratin2;
    @BindView(R.id.ratin3)
    MaterialRatingBar ratin3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    OrderCommentFoodAdapter orderCommentFoodAdapter;
    TagsOrderCommentAdapter<DictByTypeBean> tagsAdapter;
    int id;

    public static void newInstance(Context context, int id) {
        Intent intent = new Intent(context, MyCommentDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        orderCommentFoodAdapter = new OrderCommentFoodAdapter();
        recyclerview.setAdapter(orderCommentFoodAdapter);

        tagsAdapter = new TagsOrderCommentAdapter<>(this);

        ratin1.setEnabled(false);
        ratin2.setEnabled(false);
        ratin3.setEnabled(false);
        initImage();
        getCommentData();
    }

    private void getCommentData() {
        Api.getClient(HttpRequest.baseUrl_eva).queryCommentById(id).subscribeOn(Schedulers.io())
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
            iv_name.setText(eva.storeName);
            ll_store_reply.setVisibility(TextUtils.isEmpty(eva.replyContent) ? View.GONE : View.VISIBLE);
            tv_store_reply.setText(eva.replyContent + "");
            iv_pinfen.setText("综合评分" + eva.star.toString());
            ratin1.setRating(eva.star);

            List<FoodBean> data = new ArrayList<>();
            for (CommentBean.Detail detail : bean.details) {
                if (detail.commentType == 1) {
                    if (detail.commentObjId == 1) {
                        ratin2.setRating(detail.give);
                    } else if (detail.commentObjId == 2) {
                        ratin3.setRating(detail.give);
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
                gridImageAdapter.setList(strings);
            } else {
                rc_view.setVisibility(View.GONE);
            }
        }
    }

    MyGridAdapter gridImageAdapter;

    private void initImage() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyCommentDetailActivity.this, 4, GridLayoutManager.VERTICAL, false);
        gridImageAdapter = new MyGridAdapter();
        rc_view.setLayoutManager(gridLayoutManager);
        rc_view.setAdapter(gridImageAdapter);
    }

    public class MyGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyGridAdapter() {
            super(R.layout.item_pic);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
            ImageView view = baseViewHolder.findView(R.id.iv_pic);
            Picasso.with(view.getContext())
                    .load(s)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                    .into(view);

        }
    }
}
