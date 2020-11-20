package com.bee.user.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.YouhuiquanBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  18：18
 * 描述：
 */
public class ChooseAddressAdapter extends BaseMultiItemQuickAdapter<AddressBean, BaseViewHolder> {
    public ChooseAddressAdapter() {
        super();
        addItemType(AddressBean.type1,R.layout.item_chooose_address);
        addItemType(AddressBean.type2,R.layout.item_choose_address_type2);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressBean addressBean) {
        switch (baseViewHolder.getItemViewType()) {
            case AddressBean.type1:
                TextView tv_address = baseViewHolder.findView(R.id.tv_address);
                TextView tv_tag = baseViewHolder.findView(R.id.tv_tag);
                TextView tv_address2 = baseViewHolder.findView(R.id.tv_address2);
                TextView tv_name = baseViewHolder.findView(R.id.tv_name);

                if(addressBean.type == 0){
                    tv_address.setTextColor(tv_address.getResources().getColor(R.color.color_282525));
                    tv_tag.setTextColor(tv_tag.getResources().getColor(R.color.color_2CD18A));
                    tv_address2.setTextColor(tv_address2.getResources().getColor(R.color.color_282525));
                    tv_name.setTextColor(tv_name.getResources().getColor(R.color.color_7C7877));


                }else{
                    tv_address.setTextColor(tv_address.getResources().getColor(R.color.color_CBCBCB));
                    tv_tag.setTextColor(tv_tag.getResources().getColor(R.color.color_CBCBCB));
                    tv_address2.setTextColor(tv_address2.getResources().getColor(R.color.color_CBCBCB));
                    tv_name.setTextColor(tv_name.getResources().getColor(R.color.color_CBCBCB));
                }



                break;
            case AddressBean.type2:
                break;
            default:
                break;
        }
    }
}
