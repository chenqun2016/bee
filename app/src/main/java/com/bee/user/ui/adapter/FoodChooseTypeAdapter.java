package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.widget.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/18  20：55
 * 描述：
 */
public class FoodChooseTypeAdapter extends BaseQuickAdapter<FoodTypeBean, BaseViewHolder> {

    public FoodChooseTypeAdapter( ) {
        super(R.layout.item_store_food_type);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodTypeBean foodBean) {
        TextView tv_title = baseViewHolder.findView(R.id.tv_title);
        FlowTagLayout tags = baseViewHolder.findView(R.id.tags);
        tv_title.setText(foodBean.title);


        FoodChooseTypeTagsAdapter<String> tagsAdapter = new FoodChooseTypeTagsAdapter<String>(tv_title.getContext());

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);
        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                foodBean.selected = selectedList.get(0);
            }
        });
        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                foodBean.selected = position;
//                String string = et_content.getText().toString();
//                string += " "+((String) parent.getAdapter().getItem(position));
//                et_content.setText(string);
//                et_content.setSelection(et_content.length());
            }
        });


        tagsAdapter.onlyAddAll(foodBean.lists);

    }
}
