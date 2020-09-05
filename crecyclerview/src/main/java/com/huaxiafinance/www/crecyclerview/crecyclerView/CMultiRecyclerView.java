package com.huaxiafinance.www.crecyclerview.crecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  21：56
 * 描述：
 */
public class CMultiRecyclerView <T extends MultiItemEntity> extends CRecyclerView<T> {

    public CMultiRecyclerView(Context context) {
        this(context, null);
    }

    public CMultiRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CRecyclerView<T> setView(Class<? extends BaseCEntity<T>> cla) {
        try {
            model = (BaseCEntity<T>) cla.getConstructor().newInstance();
            model.setContext(mContext);
            if (null == mAdapter) setAdapter(new MultipleItemQuickAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<T , BaseViewHolder> implements LoadMoreModule {

        public MultipleItemQuickAdapter() {
            super();
            // 绑定 layout 对应的 type
            model.addItemType(this);
        }

        public void addItemTypes(int a, int b ) {
            this.addItemType(a,b);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (model == null) {
                Log.e("model", "null");
                return;
            }
            model.convert(this,helper, item, helper.getLayoutPosition() - getHeaderLayoutCount());
        }
    }

}
