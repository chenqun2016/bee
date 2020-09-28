package com.bee.user.ui.order;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.adapter.OrderCancelAdapter;
import com.bee.user.ui.adapter.ShouHouAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/27  20：28
 * 描述：
 */
public class ShouHouActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;



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


    private void showCancelDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
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
        OrderCancelAdapter orderTraceAdapter = new OrderCancelAdapter();
        recyclerview.setAdapter(orderTraceAdapter);
        orderTraceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                orderTraceAdapter.selected = position;
                orderTraceAdapter.notifyDataSetChanged();

            }
        });

        ArrayList<String> beans = new ArrayList<>();
        beans.add("忘记使用红包");
        beans.add("信息填写错误");
        beans.add("送达时间选错了");
        beans.add("买错了/买少了");
        beans.add("我不想要了");
        orderTraceAdapter.setNewInstance(beans);


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }

}
