package com.bee.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.GoodsBySectionBean;
import com.bee.user.bean.MemberCenterBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.LunchAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.membercenter.MemberCenterActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2018/12/5
 * 编写人： chenqun
 * 功能描述：简单列表页面统一调用
 * 必传参数：
 * title    String类型    ：页面title
 * entity   String类型    : 实体类
 * loadmore  boolean类型   : 是否加载更多  默认true
 * row       int类型      : 每一页加载条数  默认8
 * ...      : Entity中请求接口s时传的参数  所有参数String类型
 * <p>
 * 例如：
 * Intent intent = new Intent(getContext(), CRecyclerViewActivity.class);
 * intent.putExtra("title","带匹配列表");
 * intent.putExtra("entity", "ClaimEntity");
 * intent.putExtra("row",8);
 * intent.putExtra("loadmore",false);
 * getContext().startActivity(intent);
 */
public class CRecyclerViewActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    private  LunchAdapter lunchAdapter;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_model_crecyclerview;
    }

    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        switch (title) {
            case "销量排行榜":
            case "精选午餐":
                lunchAdapter = new LunchAdapter();
                recyclerView.setAdapter(lunchAdapter);
                getLunchInform(title);
                break;
        }
       /* ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        try {

            Intent intent = getIntent();

            String title = intent.getStringExtra("title");
            String entity = intent.getStringExtra("entity");
            int row = intent.getIntExtra("row",8);
            boolean loadmore = intent.getBooleanExtra("loadmore",true);
            intent.removeExtra("title");
            intent.removeExtra("entity");
            intent.removeExtra("loadmore");
            intent.removeExtra("row");
            toolbar_title.setText(title+"");

            if (TextUtils.isEmpty(entity)) {
                finish();
                return;
            }

            Class<?> aClass = Class.forName( entity);

            crecyclerview.setView(aClass);
            if(null != intent.getExtras()){
                Set<String> keys = intent.getExtras().keySet();
                if (null != keys) {
                    for (String str : keys) {
                        crecyclerview.setParams(str, intent.getStringExtra(str));
                    }
                }
            }
            crecyclerview.setRow(row);
            crecyclerview.setCanLoadMore(loadmore);

            crecyclerview.start();


            ArrayList foodBeans = new ArrayList();
            ParameterizedType genericSuperclass = (ParameterizedType) aClass.getGenericSuperclass();
            Class c = (Class) genericSuperclass.getActualTypeArguments()[0];
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());
            foodBeans.add(c.newInstance());


            crecyclerview.getBaseAdapter().setList(foodBeans);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * 获取精选午餐与销量排行榜信息
     * @param title
     */
    private void getLunchInform(String title) {
        if(Objects.deepEquals("精选午餐",title)) {
            Api.getClient(HttpRequest.baseUrl_goods).goodFoodList().
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<List<GoodsBySectionBean.RecordBean>>() {
                        @Override
                        public void onSuccess(List<GoodsBySectionBean.RecordBean> data) {
                            lunchAdapter.setList(data);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }else if(Objects.deepEquals("销量排行榜",title)) {
            Api.getClient(HttpRequest.baseUrl_goods).salesList().
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<List<GoodsBySectionBean.RecordBean>>() {
                        @Override
                        public void onSuccess(List<GoodsBySectionBean.RecordBean> data) {
                            lunchAdapter.setList(data);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }
    }

}
