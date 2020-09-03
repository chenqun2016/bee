package com.bee.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  15：11
 * 描述：
 */
public class NearbyStoreFoodAdapter extends RecyclerView.Adapter<NearbyStoreFoodAdapter.ViewHolder>{


    private OnItemClickListener mListener;
    List<StoreBean.StoreFood> list ;

    public NearbyStoreFoodAdapter(List<StoreBean.StoreFood> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_store_food, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreBean.StoreFood storeFood = list.get(position);

        holder.tv_name.setText(storeFood.name);
        holder.tv_money.setText(storeFood.money);

        Picasso.with(holder.iv_image.getContext())
                .load(R.drawable.food2)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(holder.iv_image.getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                .into(holder.iv_image);

        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemClick(position+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_content;
        public ImageView iv_image;
        public TextView tv_name;
        public TextView tv_money;
        public ViewHolder(View view) {
            super(view);
            iv_image =  view.findViewById(R.id.iv_image);
            tv_name =  view.findViewById(R.id.tv_name);
            tv_money =  view.findViewById(R.id.tv_money);
            ll_content = view.findViewById(R.id.ll_content);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }
}
