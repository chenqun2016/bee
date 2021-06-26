package com.bee.user.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.utils.DisplayUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 - @Description:  投稿 投诉等Activity使用
 * 展示添加的相册视频适配器
 - @Author:  bixueyun
 - @Time:  2020/8/5 下午6:09
 */
public class GridImageAdapter extends
        RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final String TAG = "PictureSelector";
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 6;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
        void oDelete();
    }

    /**
     * 删除
     */
    public void delete(int position) {
        try {

            if (position != RecyclerView.NO_POSITION && list.size() > position) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }
    
    public void addList(List<LocalMedia> list) {
        this.list.addAll(list);
    }

    public List<LocalMedia> getData() {
        return list == null ? new ArrayList<>() : list;
    }

    public void remove(int position) {
        if (list != null && position < list.size()) {
            list.remove(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mImg;
        AppCompatImageView mIvDel;
        AppCompatTextView tvCamrea;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.iv_img);
            mIvDel = view.findViewById(R.id.iv_del);
            tvCamrea = view.findViewById(R.id.tv_camrea);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.layout_photo_upload_item,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position==size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        LocalMedia media1 = list.get(0);
        String mimeType = media1.getMimeType();
        int mediaType = PictureMimeType.getMimeType(mimeType);
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            if(PictureConfig.TYPE_VIDEO == mediaType) {
                viewHolder.mImg.setVisibility(View.GONE);
                viewHolder.mIvDel.setVisibility(View.GONE);
                viewHolder.tvCamrea.setVisibility(View.GONE);
            }else {
                viewHolder.mImg.setVisibility(View.VISIBLE);
                viewHolder.tvCamrea.setVisibility(View.VISIBLE);
                viewHolder.tvCamrea.setOnClickListener(v -> mOnAddPicClickListener.onAddPicClick());
                viewHolder.mIvDel.setVisibility(View.INVISIBLE);
            }

        } else {
            viewHolder.mIvDel.setVisibility(View.VISIBLE);
            viewHolder.mImg.setVisibility(View.VISIBLE);
            viewHolder.tvCamrea.setVisibility(View.GONE);
            viewHolder.mIvDel.setOnClickListener(view -> {
                try {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION && list.size() > index) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                        if(list.size() == 0) {
                            mOnAddPicClickListener.oDelete();
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            });
            LocalMedia media = list.get(position);
            if (media == null
                    || TextUtils.isEmpty(media.getPath())) {
                return;
            }
            int chooseModel = media.getChooseModel();
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                if(TextUtils.isEmpty(media.getAndroidQToPath())) {
                    path = media.getPath();
                }else {
                    path = media.getAndroidQToPath();
                }
            }
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.mImg.setImageResource(R.drawable.picture_audio_placeholder);
            } else {
                Picasso.with(viewHolder.mImg.getContext())
                        .load("file://"+ path)
                        .fit()
                        .transform(new PicassoRoundTransform(DisplayUtil.dip2px(viewHolder.mImg.getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                        .into(viewHolder.mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(v, adapterPosition);
                });
            }

        }
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mItemClickListener = l;
    }

}
