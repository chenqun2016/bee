package com.bee.user.utils;

import android.view.View;

import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.home.BannerImageHolder;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/8/14
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class BannerUtils {
    public static void initBanner(ConvenientBanner banner, List<BannerBean> bannerList) {
        //        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) banner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        banner.setLayoutParams(params2);

        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerImageHolder(itemView);
            }
            @Override
            public int getLayoutId() {
                return R.layout.item_home_banner_image;
            }
        }, bannerList);
        banner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bannerList.get(position).onClick(banner.getContext());
            }
        });
        banner.setPageIndicator(new int[]{R.drawable.point_banner_grey, R.drawable.point_banner_yellow});
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    public static void getBannerDatas(String place, ConvenientBanner banner, List<BannerBean> bannerList) {
        Api.getClient(HttpRequest.baseUrl_sys).getBanner(place)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<BannerBean>>() {
                    @Override
                    public void onSuccess(List<BannerBean> banners) {
                        if(null != banner && null != bannerList){
                            bannerList.clear();
                            bannerList.addAll(banners);

                            if( bannerList.size()==0 ){
                                banner.setVisibility(View.GONE);
                            }else {
                                banner.setCanLoop(bannerList.size() != 1);
                                banner.setVisibility(View.VISIBLE);
                                banner.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
}
