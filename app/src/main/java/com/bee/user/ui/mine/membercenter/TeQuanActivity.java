package com.bee.user.ui.mine.membercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bee.user.R;
import com.bee.user.bean.MemberCenterBean;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/04  20：11
 * 描述：
 */
public class TeQuanActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_condition)
    TextView tvCondition;
    @BindView(R.id.tv_equity_introduction)
    TextView tvEquityIntroduction;
    private int windowWidth;

    public static void start(Activity activity, List<MemberCenterBean.PrivilegeVOBean> privilegeVOList) {
        Intent intent = new Intent(activity, TeQuanActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("privilegeVOList", (Serializable) privilegeVOList);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarView(status_bar);
        mImmersionBar.statusBarDarkFont(false, 0.2f);
        mImmersionBar.statusBarColor(R.color.color_111111);
        mImmersionBar.init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tequan;
    }


    @Override
    public void initViews() {
        List<MemberCenterBean.PrivilegeVOBean> privilegeVOList = (List<MemberCenterBean.PrivilegeVOBean>) getIntent().getSerializableExtra("privilegeVOList");
        tvCondition.setText(privilegeVOList.get(0).getGetConditions());
        tvEquityIntroduction.setText(privilegeVOList.get(0).getPrivilegeDesc().replace("\\n","\n"));

        windowWidth = DisplayUtil.getWindowWidth(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        TeQuanAdapter  adapter = new TeQuanAdapter(windowWidth);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> a, @NonNull View view, int position) {
                if( adapter.current  != position){
                    adapter.current = position;
                    adapter.notifyDataSetChanged();
                    tvCondition.setText(privilegeVOList.get(position).getGetConditions());
                    tvEquityIntroduction.setText(privilegeVOList.get(position).getPrivilegeDesc().replace("\\n","\n"));
                }
            }
        });
        recyclerview.setAdapter(adapter);

        adapter.setNewInstance(privilegeVOList);

        adapter.current = 0;
        adapter.notifyDataSetChanged();

    }



    public class TeQuanAdapter extends BaseQuickAdapter<MemberCenterBean.PrivilegeVOBean, BaseViewHolder> {

        public int current  = -1;
        int windowWidth;

        public TeQuanAdapter(int windowWidth) {
            super(R.layout.item_tequan);

            this.windowWidth = windowWidth;
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, MemberCenterBean.PrivilegeVOBean bean) {
            LinearLayout ll_content = baseViewHolder.getView(R.id.ll_content);
            ViewGroup.LayoutParams layoutParams = ll_content.getLayoutParams();
            layoutParams.width = windowWidth/4;

            ImageView iv_image = baseViewHolder.getView(R.id.iv_image);
            TextView tv_text = baseViewHolder.getView(R.id.tv_text);
            Picasso.with(iv_image.getContext())
                    .load(bean.getPrivilegeICon())
                    .fit()
                    .into(iv_image);
            tv_text.setText(bean.getPrivilegeName());

            int layoutPosition = baseViewHolder.getLayoutPosition();
            if(current == layoutPosition){
                ll_content.setAlpha(1f);
            }else{
                ll_content.setAlpha(0.5f);
            }
        }
    }

}
