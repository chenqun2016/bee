package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentWrapBean;
import com.bee.user.bean.StoreDetailFullBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.adapter.TagsCommentAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/28  15：38
 * 描述：
 */
public class CommentFragment extends BaseFragment {
    private  int shopProductId = 0;
    private final String storeId;
    @BindView(R.id.tags)
    FlowTagLayout tags;

    @BindView(R.id.store_point)
    ConstraintLayout store_point;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private Unbinder unbinder;
    CommentAdapter mAdapter;

    LoadmoreUtils loadmoreUtils;

    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.mrb)
    MaterialRatingBar mrb;
    @BindView(R.id.tv_manyidu_num)
    TextView tv_manyidu_num;
    @BindView(R.id.tv_baozhuang_num)
    TextView tv_baozhuang_num;
    @BindView(R.id.tv_kouwei_num)
    TextView tv_kouwei_num;

    //1:商品评价列表。 0：店铺评价列表
    int type = 0;
    private StoreDetailFullBean mStoreDatas;

    public CommentFragment(int shopProductId,String storeId, int type) {
        super();
        this.shopProductId = shopProductId;
        this.storeId = storeId;
        this.type = type;
    }

    @Override
    protected void getDatas() {
        loadmoreUtils.refresh(mAdapter);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_store_comment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (type == 1) {
            store_point.setVisibility(View.GONE);
        }else{
            initStoreComments();
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);

        initLoadMore();

        initTags();

        setStorePoint();

//        refresh();

    }

    private void initStoreComments() {
        mrb.setIsIndicator(true);
    }

    private List<String> dataSource = new ArrayList<>();
    private String currentTag = "";

    private void initTags() {
        dataSource.add("全部");
        dataSource.add("最新");
        dataSource.add("推荐");
        dataSource.add("吐槽");
        dataSource.add("有图");

        TagsCommentAdapter<String> tagsAdapter = new TagsCommentAdapter<>(getContext());

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);
        tagsAdapter.onlyAddAll(dataSource);

        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if(selectedList.size()>0){
                    Integer integer = selectedList.get(0);
                    currentTag = dataSource.get(integer);
                    loadmoreUtils.refresh(mAdapter);
                }
            }
        });
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils() {
            @Override
            protected void getDatas(int page) {
                if (type == 0) {
                    CommentFragment.this.getComments(page);
                } else {
                    CommentFragment.this.getComments2(page);
                }

            }
        };
        loadmoreUtils.initLoadmore(mAdapter);
    }

    /**
     * 刷新
     */
    private void getComments(int page) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("orderId", "");
        stringStringHashMap.put("storeId", storeId);
        stringStringHashMap.put("searchKey", currentTag);

        Api.getClient(HttpRequest.baseUrl_eva).commentQueryList(Api.getRequestBody(stringStringHashMap), page, LoadmoreUtils.PAGE_SIZE)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CommentWrapBean>() {
                    @Override
                    public void onSuccess(CommentWrapBean beans) {
                        loadmoreUtils.onSuccess(mAdapter, beans.records);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(mAdapter, fail);
                    }
                });
    }

    /**
     * 刷新
     */
    private void getComments2(int page) {
        Api.getClient(HttpRequest.baseUrl_eva).queryListBySkuId(shopProductId+"", page, LoadmoreUtils.PAGE_SIZE)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CommentWrapBean>() {
                    @Override
                    public void onSuccess(CommentWrapBean beans) {
                        loadmoreUtils.onSuccess(mAdapter, beans.records);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(mAdapter, fail);
                    }
                });
    }

    public void setStorePointData(StoreDetailFullBean storeDetailBean) {
        this.mStoreDatas = storeDetailBean;
        if(null != mrb){
            setStorePoint();
        }
    }

    private void setStorePoint() {
        if(null != mStoreDatas){
            mrb.setRating(mStoreDatas.star);
            tv_point.setText(mStoreDatas.star+"");
            tv_manyidu_num.setText(mStoreDatas.deliverySatisfaction);
            tv_baozhuang_num.setText(mStoreDatas.packStar);
            tv_kouwei_num.setText(mStoreDatas.tasteStar);
        }
    }
}
