package com.bee.user.ui.mine.membercenter;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bee.user.R;
import com.bee.user.bean.MemberRulesBean;
import com.bee.user.ui.adapter.MemberRulesAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.membercenter.TeQuanActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.MyLinerProgressbar;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/31  22：02
 * 描述：
 */
public class MemberCenterActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;
    @BindView(R.id.background_title)
    View background_title;

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @OnClick({R.id.tv_more})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_more :
                startActivity(new Intent(this, TeQuanActivity.class));
                break;
        }
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_center;
    }


    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MemberRulesBean> beans = new ArrayList<>();
        beans.add(new MemberRulesBean("会员名称","米粒","有效期"));
        beans.add(new MemberRulesBean("蜂拥会员","0","不限"));
        beans.add(new MemberRulesBean("土蜂会员","1-499","不限"));
        beans.add(new MemberRulesBean("绿蜂会员","500-999","不限"));
        beans.add(new MemberRulesBean("黄蜂会员","1000-4999","不限"));
        beans.add(new MemberRulesBean("黑蜂会员","5000-无上限","不限"));
        MemberRulesAdapter adapter = new MemberRulesAdapter(beans);
        recyclerview.setAdapter(adapter);


        int height = DisplayUtil.dip2px(this,80);


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int totalScrollY, int oldScrollX, int oldScrollY) {

                if(totalScrollY  <= 0){
                    statusheight.setAlpha(0);
                    background_title.setAlpha(0);
                }else if(totalScrollY > height){
                    statusheight.setAlpha(1);
                    background_title.setAlpha(1);
                }else{
                    float a = (totalScrollY / (float)height);
                    statusheight.setAlpha(a);
                    background_title.setAlpha(a);
                }

                if(totalScrollY <= (height/3)){
                    iv_back.setImageResource(R.drawable.icon_back_bai);
                    toolbar_title.setTextColor(getResources().getColor(R.color.white));

                    float a = 1 - (totalScrollY / (float)height);
                    iv_back.setAlpha(a);
                    toolbar_title.setAlpha(a);
                }else if(totalScrollY <= height){
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    toolbar_title.setTextColor(getResources().getColor(R.color.color_282626));

                    float a =  (totalScrollY / (float)height);
                    iv_back.setAlpha(a);
                    toolbar_title.setAlpha(a);
                }else{
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    toolbar_title.setTextColor(getResources().getColor(R.color.color_282626));
                }



            }
        });



        viewpager.setAdapter(new MyAdapter());


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int     padding  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);
        int    margin  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);
        viewpager.setPageMargin(margin);
        viewpager.setPadding(margin , 0, padding, 0);
        viewpager.setClipToPadding(false);
    }




    public class MyAdapter extends PagerAdapter {


        public MyAdapter() {
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(0 == position){
                View view1 = View.inflate(container.getContext(), R.layout.item_viewpager_membercenter_1, null);
                MyLinerProgressbar progressbar = view1.findViewById(R.id.progressbar);
                progressbar.setProgress(20);
                container.addView(view1);
                return view1;
            }

            View view2 = View.inflate(container.getContext(), R.layout.item_viewpager_membercenter_2, null);
            container.addView(view2);
            return view2;
        }

    }

}
