package com.bee.user.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoEngine;
import com.bee.user.R;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.OrderCommentFoodAdapter;
import com.bee.user.ui.adapter.TagsOrderCommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.FlowTagLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.bean.DictByTypeBean.TYPE_ORDER_APPRAISE;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  16：07
 * 描述：
 */
public class OrderCommentActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tags)
    FlowTagLayout tags;


    @BindView(R.id.tv_tijiao)
    TextView tv_tijiao;


    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.tv_num)
    TextView tv_num;

    @BindView(R.id.et_people_content)
    EditText et_people_content;

    TagsOrderCommentAdapter<DictByTypeBean> tagsAdapter;

    @OnClick({R.id.tv_tijiao,R.id.tv_paizhao})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_tijiao:
                startActivity(new Intent(this,OrderCommentStatusActivity.class));
                break;
            case R.id.tv_paizhao:
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
        return R.layout.activity_order_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        OrderCommentFoodAdapter orderCommentFoodAdapter = new OrderCommentFoodAdapter();
        recyclerview.setAdapter(orderCommentFoodAdapter);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("王牌鸭腿饭");
        strings.add("金牌牛肉面");
        orderCommentFoodAdapter.setNewInstance(strings);





        tagsAdapter   = new TagsOrderCommentAdapter<>(this);

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tags.setAdapter(tagsAdapter);


        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {

            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if(null == selectedList || selectedList.size()==0){
                    et_people_content.setText("");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for(Integer i : selectedList){
                    sb.append(((DictByTypeBean)tagsAdapter.getItem(i)).getDictValue());
                    sb.append(" ");
                    et_people_content.setText(sb.toString());
                }
            }
        });

        et_content.setSelection(et_content.getText().length());

        String strs = "评论文字/图片 ";
        SpannableString msp1 = new SpannableString(strs+"12积分");
        msp1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), strs.length(), msp1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_num.setText(msp1);


        toDictByType();
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
                        if(null != dictByType && dictByType.size()>0){
                            tagsAdapter.onlyAddAll(dictByType);
                        }
                    }
                });
    }
}
