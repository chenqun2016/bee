package com.bee.user.ui.mine.membercenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.MemberCenterBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.MemberRulesAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.MyLinerProgressbar;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    @BindView(R.id.ll_privage)
    LinearLayout llPrivage;

    private MemberRulesAdapter adapter;
    private MemberCenterBean memberCenterBean;

    @OnClick({R.id.tv_more,R.id.tv_buy_mili})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_more :
               // startActivity(new Intent(this, TeQuanActivity.class));
                TeQuanActivity.start(this,memberCenterBean.getPrivilegeVOList());
                break;
            case R.id.tv_buy_mili :
                startActivity(new Intent(this, MiLiActivity.class));
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
        getMemberInform();
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) viewpager.getLayoutParams();
        layoutParams1.topMargin = ImmersionBar.getStatusBarHeight(this) + DisplayUtil.dip2px(this,65);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MemberCenterBean.RuleVOBean> beans = new ArrayList<>();
       adapter = new MemberRulesAdapter(beans);
        recyclerview.setAdapter(adapter);


        int height = DisplayUtil.dip2px(this,80);


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int totalScrollY, int oldScrollX, int oldScrollY) {

                LogUtil.d("totalScrollY=="+totalScrollY+"/height=="+height);

                if(totalScrollY  <= 0){
                    statusheight.setAlpha(0f);
                    background_title.setAlpha(0f);
                }else if(totalScrollY > height){
                    statusheight.setAlpha(1f);
                    background_title.setAlpha(1f);
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

                    iv_back.setAlpha(1f);
                    toolbar_title.setAlpha(1f);
                }



            }
        });


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int     padding  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);
        int    margin  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);
        viewpager.setPageMargin(margin);
        viewpager.setPadding(margin , 0, padding, 0);
        viewpager.setClipToPadding(false);
    }

    private void getMemberInform() {
        Api.getClient(HttpRequest.baseUrl_member).getMemberLevelMessage().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MemberCenterBean>() {
                    @Override
                    public void onSuccess(MemberCenterBean data) {
                        memberCenterBean = data;
                        viewpager.setAdapter(new MyAdapter());
                        List<MemberCenterBean.RuleVOBean> ruleVOList = data.getRuleVOList();
                        MemberCenterBean.RuleVOBean ruleVOBean = new MemberCenterBean.RuleVOBean();
                        ruleVOBean.setLevelName("会员名称");
                        ruleVOBean.setGrowScope("米粒");
                        ruleVOBean.setExpiredDate("有效期");
                        ruleVOList.add(0,ruleVOBean);
                        adapter.setList(ruleVOList);
                        setPrivilegeVO();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void setPrivilegeVO() {
        List<MemberCenterBean.PrivilegeVOBean> privilegeVOList = memberCenterBean.getPrivilegeVOList();
        for (MemberCenterBean.PrivilegeVOBean privilegeVOBean :privilegeVOList) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            textView.setTextColor(getResources().getColor(R.color.color_3B3838));
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(DisplayUtil.dip2px(this,94), ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = DisplayUtil.dip2px(this,5);
            textView.setText(privilegeVOBean.getPrivilegeName());
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    Drawable drawableTop = new BitmapDrawable(getResources(), bitmap);
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {

                }

                @Override
                public void onPrepareLoad(Drawable drawable) {

                }
            };
            Picasso.with(this).load(privilegeVOBean.getPrivilegeICon()).into(target);
            llPrivage.addView(textView,layoutParams);
        }
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
                TextView huiyuan = view1.findViewById(R.id.huiyuan);
                TextView tvChae = view1.findViewById(R.id.tv_chae);
                ImageView tvIcon = view1.findViewById(R.id.tv_icon);
                TextView title = view1.findViewById(R.id.title);
                view1.findViewById(R.id.title);
                huiyuan.setText(memberCenterBean.getLevelName());
                tvChae.setText(memberCenterBean.getLevelUpGrowDesc());
                title.setText(memberCenterBean.getMemberNickName());
                progressbar.setProgress(new BigDecimal(memberCenterBean.getNowGrowResult()).intValue());
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        //替换背景
                        huiyuan.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable drawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable drawable) {

                    }
                };
                Picasso.with(huiyuan.getContext()).load(memberCenterBean.getLevelIcon()).into(target);

                Picasso.with(tvIcon.getContext())
                        .load(memberCenterBean.getMemberIcon())
                        .placeholder(R.drawable.icon_touxiang)
                        .error(R.drawable.icon_touxiang)
                        .fit()
                        .transform(new PicassoRoundTransform(DisplayUtil.dip2px(tvIcon.getContext(), 50), 0, PicassoRoundTransform.CornerType.ALL))
                        .into(tvIcon);
                container.addView(view1);
                return view1;
            }

            View view2 = View.inflate(container.getContext(), R.layout.item_viewpager_membercenter_2, null);
            TextView tv_huiyuan = view2.findViewById(R.id.tv_huiyuan);
            tv_huiyuan.setText(memberCenterBean.getLevelName());
            container.addView(view2);
            return view2;
        }

    }

}
