package com.bee.user.ui.mine.membercenter;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.TeQuanBean;
import com.bee.user.ui.adapter.BuyCardAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/04  20：11
 * 描述：
 */
public class TeQuanActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    private int windowWidth;

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
        windowWidth = DisplayUtil.getWindowWidth(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        TeQuanAdapter  adapter = new TeQuanAdapter(windowWidth);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> a, @NonNull View view, int position) {
                if( adapter.current  != position){
                    adapter.current = position;
                    adapter.notifyDataSetChanged();
                }
            }
        });


        recyclerview.setAdapter(adapter);


        ArrayList<TeQuanBean> objects = new ArrayList<>();
        objects.add(new TeQuanBean(R.drawable.icon_chongzhi_lvfeng,"充值赠送"));
        objects.add(new TeQuanBean(R.drawable.icon_shengri_lvfeng,"生日礼包"));
        objects.add(new TeQuanBean(R.drawable.con_tuikuan_lvfeng,"极速退款"));
        objects.add(new TeQuanBean(R.drawable.icon_youhui_lvfeng,"领取优惠"));
        adapter.setNewInstance(objects);

        adapter.current = 0;
        adapter.notifyDataSetChanged();

    }



    public class TeQuanAdapter extends BaseQuickAdapter<TeQuanBean, BaseViewHolder> {

        public int current  = -1;
        int windowWidth;

        public TeQuanAdapter(int windowWidth) {
            super(R.layout.item_tequan);

            this.windowWidth = windowWidth;
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, TeQuanBean bean) {
            LinearLayout ll_content = baseViewHolder.getView(R.id.ll_content);
            ViewGroup.LayoutParams layoutParams = ll_content.getLayoutParams();
            layoutParams.width = windowWidth/4;


            ImageView iv_image = baseViewHolder.getView(R.id.iv_image);
            TextView tv_text = baseViewHolder.getView(R.id.tv_text);

            iv_image.setImageResource(bean.getResouse());
            tv_text.setText(bean.getText());

            int layoutPosition = baseViewHolder.getLayoutPosition();
            if(current == layoutPosition){
                ll_content.setAlpha(1f);
            }else{
                ll_content.setAlpha(0.5f);
            }
        }
    }

}
