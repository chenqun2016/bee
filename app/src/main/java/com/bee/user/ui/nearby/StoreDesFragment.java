package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.StoreTimeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/29  18：22
 * 描述：商家信息页面
 */
public class StoreDesFragment extends BaseFragment {

    private final String storeId;
    private Unbinder unbinder;

    @BindView(R.id.gv_time)
    MyGridView gv_time;

    public StoreDesFragment(String storeId) {
        super();
        this.storeId = storeId;
    }
    @Override
    protected void getDatas() {
        Api.getClient(HttpRequest.baseUrl_shop).shop_getDetail(storeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StoreDetailBean>() {
                    @Override
                    public void onSuccess(StoreDetailBean storeDetailBean) {
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_store_des, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gv_time.setAdapter(new StoreTimeAdapter());
    }
}
