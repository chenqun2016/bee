package com.bee.user.ui.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.NearbyAdapter;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/19  14：51
 * 描述：
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.recyclerview_nodata)
    RecyclerView recyclerview_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.rl_history)
    RelativeLayout rl_history;

    @BindView(R.id.cet_search)
    ClearEditText cet_search;

    @BindView(R.id.tags)
    FlowTagLayout tags;

    private  TagsAdapter<String> tagsAdapter;
    private List<String> dataSource = new ArrayList<>();

    private HomeAdapter homeAdapter;
    private NearbyAdapter nearbyAdapter;

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
        return R.layout.activity_search;
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
                    recyclerview_nodata.setVisibility(View.GONE);
                    recyclerview.setVisibility(View.GONE);

                    if(null != homeAdapter){
                        homeAdapter.setNewInstance(null);
                        homeAdapter.removeAllHeaderView();
                    }
                    if(null != nearbyAdapter){
                        nearbyAdapter.setNewInstance(null);
                        nearbyAdapter.removeAllHeaderView();
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


        dataSource.add("不放辣");
        dataSource.add("多一点米饭");
        dataSource.add("请电话给我");
        dataSource.add("不要冰");
        dataSource.add("不要葱");
        dataSource.add("放到前台");
        dataSource.add("放门口");
        tagsAdapter.onlyAddAll(dataSource);
    }



    private void getDatas() {
        rl_history.setVisibility(View.GONE);
        recyclerview_nodata.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);

//        initNoDatas();
        initDatas();
    }

    private void initDatas() {
        nearbyAdapter   = new NearbyAdapter();

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(nearbyAdapter);

        nearbyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(SearchActivity.this, StoreActivity.class));
            }
        });

        ArrayList<StoreBean> beans = new ArrayList<StoreBean>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        nearbyAdapter.setNewInstance(beans);
    }


    private void initNoDatas() {
        homeAdapter  = new HomeAdapter();
        recyclerview_nodata.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview_nodata.setAdapter(homeAdapter);

        View head = View.inflate(this, R.layout.head_fragment_chart_nodata, null);
        ImageView iv_icon = head.findViewById(R.id.iv_icon);
        iv_icon.setImageResource(R.drawable.bg_kongbai5);
        TextView tv_1 = head.findViewById(R.id.tv_1);
        TextView tv_2 = head.findViewById(R.id.tv_2);
        tv_1.setText("没有搜索到你要的结果");
        tv_2.setText("换个关键词试试吧");
        TextView tv_tag = head.findViewById(R.id.tv_tag);
        tv_tag.setText("猜你喜欢");

        View tv_guangguang = head.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 1;
                EventBus.getDefault().post(mainEvent);

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        homeAdapter.addHeaderView(head);

        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(SearchActivity.this, FoodActivity.class));
            }
        });

        ArrayList<HomeBean> homeBeans = new ArrayList<>();
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeAdapter.setNewInstance(homeBeans);
    }
}
