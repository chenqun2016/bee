package com.bee.user.ui.base;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/06  14：48
 * 描述：
 */
public abstract class SwipeRefreshBaseActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh_layout)
    @Nullable
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void initViewsPre() {
        super.initViewsPre();
        trySetupSwipeRefresh();
    }

    void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    R.color.colorPrimaryDark, R.color.colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    SwipeRefreshBaseActivity.this.onReflush();
                }
            });
//            mSwipeRefreshLayout.setAnimateToRefreshDuration(1000);
//            mSwipeRefreshLayout.setAnimateToStartDuration(1000);
//            mSwipeRefreshLayout.setDragDistanceConverter(new IDragDistanceConverter() {
//                @Override
//                public float convert(float scrollDistance, float refreshDistance) {
//                    return scrollDistance * 0.5f;
//                }
//            });
//            mSwipeRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    mPresenter.doReflush();
//                }
//            });
        }
    }

    public void onReflush(){

    }

    public void setRefresh(boolean requestDataRefresh) {

        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, Constants.Delay_Reflush_false);
        } else {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            },Constants.Delay_Reflush_true);
        }
    }
}
