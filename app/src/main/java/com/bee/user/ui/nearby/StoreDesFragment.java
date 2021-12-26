package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreDetailFullBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.StoreTimeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.MyGridView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.tv_contact)
    TextView tv_contact;

    @BindView(R.id.image)
    ViewPager2 image;
    @BindView(R.id.tv_pingpai_content)
    TextView tv_pingpai_content;
    @BindView(R.id.iv_zizhi1)
    RecyclerView iv_zizhi1;
    @BindView(R.id.gv_time)
    MyGridView gv_time;
    private StoreDetailFullBean mStoreDatas;

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

    @OnClick(R.id.tv_contact)
    public void onClick(View view){
        if(null != mStoreDatas){
            CommonUtil.callPhone(getContext(),mStoreDatas.contactMobile);
        }
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

        setStoreDataView();

    }

    public void setStoreData(StoreDetailFullBean storeDetailBean) {
        this.mStoreDatas = storeDetailBean;
        if(null != tv_pingpai_content){
            setStoreDataView();
        }
    }

    private void setStoreDataView() {
        if(null != mStoreDatas){
            tv_pingpai_content.setText(mStoreDatas.description);
            gv_time.setAdapter(new StoreTimeAdapter(mStoreDatas.businessTimes));

            if(mStoreDatas.shopPicList == null || mStoreDatas.shopPicList.size() == 0){
                image.setVisibility(View.GONE);
            }else{
                image.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) image.getLayoutParams();
                params2.height = 150  * params2.width / 355;
                image.setLayoutParams(params2);
                image.setAdapter(new Viewpager2Adapter(mStoreDatas.shopPicList));
            }

            iv_zizhi1.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
            iv_zizhi1.setAdapter(new ZhizhiAdapter());
        }
    }

    static final class Viewpager2Adapter extends RecyclerView.Adapter<HorizontalVpViewHolder> {

        private final List<String> mDatas;

        public Viewpager2Adapter(List<String> datas) {
            this.mDatas = datas;
        }

        @NonNull
        @NotNull
        @Override
        public HorizontalVpViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new HorizontalVpViewHolder(LayoutInflater.from(parent.getContext()).inflate((R.layout.item_home_banner_image), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull HorizontalVpViewHolder holder, int position) {
            Picasso.with(holder.tv_image.getContext())
                    .load(mDatas.get(position))
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(holder.tv_image.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(holder.tv_image);
        }

        @Override
        public int getItemCount() {
            return mDatas==null?0:mDatas.size();
        }
    }

    static class HorizontalVpViewHolder extends RecyclerView.ViewHolder {
        ImageView tv_image;

        HorizontalVpViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_image = itemView.findViewById(R.id.tv_image);
        }
    }

    static class ZhizhiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ZhizhiAdapter() {
            super(R.layout.item_food_image);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
            ImageView tv_image = baseViewHolder.getView(R.id.tv_image);
            ViewGroup.LayoutParams layoutParams = tv_image.getLayoutParams();
            layoutParams.height = DisplayUtil.dip2px(tv_image.getContext(),100);
            layoutParams.width = DisplayUtil.dip2px(tv_image.getContext(),150);
            Picasso.with(tv_image.getContext())
                    .load(s)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(tv_image.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(tv_image);
        }
    }
}
