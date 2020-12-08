package com.bee.user.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.LocationBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.NearbyAdapter;
import com.bee.user.ui.adapter.NearbyStoreFoodAdapter;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.ToastUtil;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/08  21：10
 * 描述：
 */
public class SearchLocationActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.rl_history)
    RelativeLayout rl_history;

    @BindView(R.id.cet_search)
    ClearEditText cet_search;

    @BindView(R.id.tags)
    FlowTagLayout tags;

    private TagsAdapter<String> tagsAdapter;
    private List<String> dataSource = new ArrayList<>();

    private SearchLocationAdapter adapter;

    @OnClick({R.id.iv_clear,R.id.tv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_clear:
                dataSource.clear();
                tagsAdapter.clearAndAddAll(new ArrayList<>());
                break;
            case R.id.tv_search:
                addHistoryDatas();

                getDatas();
                break;
        }

    }


    private void addHistoryDatas() {
        Editable text = cet_search.getText();
        if(null != text && !dataSource.contains(text.toString())){
            dataSource.add(0,text.toString());
            tagsAdapter.clearAndAddAll(dataSource);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_location;
    }

    @Override
    public void initViews() {
        cet_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(TextUtils.isEmpty(charSequence)){
                    rl_history.setVisibility(View.VISIBLE);
                    recyclerview.setVisibility(View.GONE);


                    if(null != adapter){
                        adapter.setNewInstance(null);
                        adapter.removeAllHeaderView();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cet_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    addHistoryDatas();
                    getDatas();
                    return true;
                }
                return false;
            }
        });
        tagsAdapter = new TagsAdapter<>(this);

//        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                cet_search.setText(""+((String) parent.getAdapter().getItem(position)));
                cet_search.setSelection(cet_search.length());

                getDatas();
            }
        });


        dataSource.add("信建大厦");
        dataSource.add("鲁能国际中心");
        dataSource.add("正大广场");
        tagsAdapter.onlyAddAll(dataSource);
    }



    private void getDatas() {
        rl_history.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);

        initDatas();
    }

    private void initDatas() {
        adapter   = new SearchLocationAdapter();

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        LoadmoreUtils loadmoreUtils = new LoadmoreUtils(LocationBean.class);
        loadmoreUtils.initLoadmore(adapter);
        loadmoreUtils.refresh(adapter);

    }





    public class SearchLocationAdapter extends BaseQuickAdapter<LocationBean, BaseViewHolder> implements LoadMoreModule {
        public SearchLocationAdapter() {
            super(R.layout.item_searchlocation);
        }


        @Override
        protected void convert(@NotNull BaseViewHolder helper, LocationBean storeBean) {
            TextView tv_name = helper.getView(R.id.tv_name);

        }
    }

}
