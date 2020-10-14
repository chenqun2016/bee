package com.bee.user.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.bean.TraceBean;
import com.bee.user.ui.adapter.CommentImagesAdapter;
import com.bee.user.ui.adapter.OrderFoodAdapter;
import com.bee.user.ui.adapter.OrderTraceAdapter;
import com.bee.user.ui.nearby.ImagesActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/09  15：29
 * 描述：
 */
public class CommonUtil {
    public static final java.text.DecimalFormat myformat = new java.text.DecimalFormat("###,##0.0000");

    public static String moneyType(double d) {
        String format = myformat.format(d);
        int i = format.lastIndexOf(".");
        return format.substring(0, i + 3);
    }



    //校验手机号码
    public static final boolean isMobileNoAll(CharSequence mobiles) {
//        Pattern p = Pattern.compile("^(([0-9]))\\d{10}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
        return !TextUtils.isEmpty(mobiles) && mobiles.length() == 11;
    }



    public static void showKeyBoard(Activity a) {
        View view = a.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
        }
    }

    //评论图片
    public static void initCommentAdapter(RecyclerView images) {
        images.setLayoutManager(new LinearLayoutManager(images.getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<ImageBean> imageBeans = new ArrayList<>();
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        imageBeans.add(new ImageBean());
        CommentImagesAdapter commentImagesAdapter = new CommentImagesAdapter(imageBeans);
        images.setAdapter(commentImagesAdapter);

        commentImagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent(images.getContext(), ImagesActivity.class);
                intent.putExtra("datas",imageBeans);
                images.getContext().startActivity(intent);
            }
        });



    }

    //订单跟踪
    public static void initTraceAdapter(RecyclerView recyclerview) {
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        OrderTraceAdapter orderTraceAdapter = new OrderTraceAdapter();
        recyclerview.setAdapter(orderTraceAdapter);

        ArrayList<TraceBean> traceBeans = new ArrayList<>();
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(1));
        orderTraceAdapter.setNewInstance(traceBeans);
    }

//    下单时的food列表
    public static void initOrderFoodAdapter(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        List<FoodBean> foodBeans = new ArrayList<>();
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());

        OrderFoodAdapter adapter = new OrderFoodAdapter(foodBeans);
        recyclerView.setAdapter(adapter);
    }
}
