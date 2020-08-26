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
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @OnClick({R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_crecyclerview);

        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
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
        }


    }

}
