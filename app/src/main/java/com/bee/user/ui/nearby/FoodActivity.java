package com.bee.user.ui.nearby;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.CommentBean;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.home.BannerImageHolder;
import com.bee.user.utils.LogUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  20：48
 * 描述：
 */
public class FoodActivity extends BaseActivity {
    @BindView(R.id.app_barbar)
    AppBarLayout app_barbar;

    @BindView(R.id.background)
    View background;
    @BindView(R.id.ll_toolbar)
    Toolbar ll_toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tv_index)
    TextView tv_index;

    @BindView(R.id.banner2)
    ConvenientBanner banner2;


    @OnClick({R.id.tv_choosetype})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_choosetype:
                showChooseTypeDialog();
                break;
        }
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = ll_toolbar.getLayoutParams();
        layoutParams.height += ImmersionBar.getStatusBarHeight(this);
        ll_toolbar.invalidate();


        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);

                if (verticalOffset == 0) {
                    background.setAlpha(0);
                } else if(Math.abs(verticalOffset) >= scrollRangle){
                    background.setAlpha(1);
                }else{
                    //保留一位小数
                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }


            }
        });



        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        CommentAdapter mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);


        View head = View.inflate(this, R.layout.head_activity_food_comment, null);
        TextView tv_food_comment = head.findViewById(R.id.tv_food_comment);
        tv_food_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodActivity.this,CommentActivity.class));
            }
        });
        mAdapter.addHeaderView(head);

        View bottom = View.inflate(this, R.layout.bottom_food_list, null);
        mAdapter.addFooterView(bottom);

        List<CommentBean> sampleData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CommentBean status = new CommentBean();
            sampleData.add(status);
        }

        mAdapter.setList(sampleData);

        initBanner();
    }

    private int totalPage = 0;
    private void initBanner() {
//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        mBanner.setLayoutParams(params2);
        BannerBean bannerBean = new BannerBean();
        bannerBean.url = R.drawable.food;

        List<BannerBean> adsList = new ArrayList<>();//banner数据
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        adsList.add(bannerBean);

        totalPage = adsList.size();
        tv_index.setText(1+"/"+totalPage);
        banner2.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_food_image;
            }


        }, adsList);
        banner2.setPointViewVisible(false);
        banner2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        banner2.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onPageSelected(int index) {
                tv_index.setText((index+1)+"/"+totalPage);
            }
        });

    }

    private void showChooseTypeDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom_choose);
        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }catch (Exception e){

        }
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });

        bottomSheetDialog.show();
    }

}
