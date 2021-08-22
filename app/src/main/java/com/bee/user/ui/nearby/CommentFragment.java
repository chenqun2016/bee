package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
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

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/28  15：38
 * 描述：
 */
public class CommentFragment extends BaseFragment {

    private final String storeId;
    @BindView(R.id.tags)
    FlowTagLayout tags;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private Unbinder unbinder;
    CommentAdapter mAdapter;

    LoadmoreUtils loadmoreUtils;

    public CommentFragment(String id) {
        super();
        this.storeId = id;
    }

    @Override
    protected void getDatas() {

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("storeId",storeId);
        stringStringHashMap.put("searchKey","");

        Api.getClient(HttpRequest.baseUrl_eva).commentQueryList(Api.getRequestBody(stringStringHashMap))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> beans) {

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
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

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);

        initLoadMore();

        initTags();

        refresh();


    }

    private void initTags() {

        TagsCommentAdapter<String> tagsAdapter = new TagsCommentAdapter<>(getContext());

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

            }
        });

        List<String> dataSource = new ArrayList<>();
        dataSource.add("全部");
        dataSource.add("最新");
        dataSource.add("推荐");
        dataSource.add("吐槽");
        dataSource.add("有图");
        tagsAdapter.onlyAddAll(dataSource);
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils();
        loadmoreUtils.initLoadmore(mAdapter);
    }

    /**
     * 刷新
     */
    private void refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载

        loadmoreUtils.refresh(mAdapter);
    }
}
