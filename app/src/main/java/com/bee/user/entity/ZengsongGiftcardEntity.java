package com.bee.user.entity;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bee.user.R;
import com.bee.user.bean.GiftcardRecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/06  15：40
 * 描述：
 */
public class ZengsongGiftcardEntity extends BaseCEntity<GiftcardRecordBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<GiftcardRecordBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);

        GiftcardRecordBean item = mAdapter.getItem(position);
        item.isSelected = !item.isSelected;
        mAdapter.notifyItemChanged(position);
    }



    @Override
    public int getItemLayou() {
        return R.layout.item_giftcard_zengsong;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, GiftcardRecordBean item, int position) {
        super.convert(adapter, helper, item, position);

        CheckBox checkbox = helper.findView(R.id.checkbox);
        checkbox.setChecked(item.isSelected);
    }
}
