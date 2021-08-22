package com.bee.user.ui.mine;

import com.bee.user.R;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.PointDetailAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.blankj.utilcode.util.ObjectUtils;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  19：16
 * 描述：
 */
public class PointDetailListActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private PointDetailAdapter pointDetailAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_point_list;
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        pointDetailAdapter = new PointDetailAdapter();
        recyclerview.setAdapter(pointDetailAdapter);
        topointsRecord();
    }


    /**
     * 获取积分记录
     */
    private void topointsRecord() {
        Api.getClient(HttpRequest.baseUrl_activity).pointsRecord().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PointDetailBen>>() {
                    @Override
                    public void onSuccess(List<PointDetailBen> data) {
                       if(!ObjectUtils.isEmpty(data)) {
                           pointDetailAdapter.setList(data);
                       }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

}
