package com.bee.user.ui.order;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.OrderCancelAdapter;
import com.bee.user.ui.adapter.ShouHouAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.bean.DictByTypeBean.TYPE_REFUND_REASON;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/27  20：28
 * 描述：
 */
public class ShouHouActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    OrderCancelAdapter orderTraceAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shouhou;
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ShouHouAdapter shouHouAdapter = new ShouHouAdapter();
        recyclerview.setAdapter(shouHouAdapter);
        View foot = View.inflate(this, R.layout.foot_activity_shouhou, null);
        initFoot(foot);
        shouHouAdapter.addFooterView(foot);

        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        shouHouAdapter.setNewInstance(beans);

        toDictByType();
    }

    private void initFoot(View foot) {

        TextView tv_tuikuan = foot.findViewById(R.id.tv_tuikuan);
        tv_tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog();
            }
        });

        RecyclerView images = foot.findViewById(R.id.images);
        CommonUtil.initCommentAdapter(images);
    }

    BottomSheetDialog bottomSheetDialog;

    private void showCancelDialog() {
        if (null == bottomSheetDialog) {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.dialog_order_detail_cancel);
            bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                }
            });
            RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            orderTraceAdapter = new OrderCancelAdapter();
            recyclerview.setAdapter(orderTraceAdapter);
            orderTraceAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    orderTraceAdapter.selected = position;
                    orderTraceAdapter.notifyDataSetChanged();

                }
            });


            bottomSheetDialog.setCanceledOnTouchOutside(false);
            try {
                bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                        .setBackgroundResource(android.R.color.transparent);
            } catch (Exception e) {

            }
        }

        orderTraceAdapter.setNewInstance(mStringList);
        bottomSheetDialog.show();
    }

    private List<DictByTypeBean> mStringList;

    /**
     * 获取反馈类型
     */
    private void toDictByType() {
        Api.getClient(HttpRequest.baseUrl_sys).getDictByType(TYPE_REFUND_REASON).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DictByTypeBean>>() {
                    @Override
                    public void onSuccess(List<DictByTypeBean> dictByType) {
                        mStringList = dictByType;
                    }
                });
    }
}
