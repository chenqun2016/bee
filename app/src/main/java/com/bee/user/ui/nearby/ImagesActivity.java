package com.bee.user.ui.nearby;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.home.BannerImageHolder;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/03  16：03
 * 描述：
 */
public class ImagesActivity extends BaseActivity {

    @BindView(R.id.banner2)
    ConvenientBanner banner2;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_images;
    }

    @Override
    public void initViews() {
        base_pre_content.setBackgroundResource(R.color.transparent);
        Intent intent = getIntent();
        List<ImageBean> datas = (List<ImageBean>)intent.getSerializableExtra("datas");

        banner2.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new ImagesHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_food_image;
            }


        }, datas);
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
            }
        });
    }
}
