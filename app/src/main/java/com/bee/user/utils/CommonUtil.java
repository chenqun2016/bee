package com.bee.user.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.TraceBean;
import com.bee.user.ui.adapter.CommentImagesAdapter;
import com.bee.user.ui.adapter.OrderCancelAdapter;
import com.bee.user.ui.adapter.OrderFoodAdapter;
import com.bee.user.ui.adapter.OrderGridviewItemAdapter;
import com.bee.user.ui.adapter.OrderTraceAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.BuyCardActivity;
import com.bee.user.ui.nearby.ImagesActivity;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.ui.order.OrderDetailActivity;
import com.bee.user.ui.order.OrderDetailMapActivity;
import com.bee.user.ui.order.ShouHouActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    public static void performOrderGridviewClick(BaseActivity activity, OrderGridviewItemAdapter adapter, int i) {
        OrderGridviewItemBean bean = adapter.getList().get(i);
        if(null != bean){
            int type =  bean.type;

            Intent intent;
            switch (type){
                case OrderGridviewItemBean.TYPE_reOrder://再来一单
                    activity. startActivity(OrderingActivity.newIntent(activity,new ArrayList<StoreBean>()));
                    break;
                case OrderGridviewItemBean.TYPE_comment://评价得积分
                    intent = new Intent(activity, OrderCommentActivity.class);
                    activity.  startActivity(intent);
                    break;
                case OrderGridviewItemBean.TYPE_shouhou://申请售后
                    intent = new Intent(activity, ShouHouActivity.class);
                    activity.  startActivity(intent);
                    break;
                case OrderGridviewItemBean.TYPE_contact_shop://联系商家
                    callPhone(activity,"10010");
                    break;
                case OrderGridviewItemBean.TYPE_contact_rider://联系骑手
                    callPhone(activity,"10086");
                    break;
                case OrderGridviewItemBean.TYPE_pay_now://立即支付
                    activity.  startActivity(PayActivity.newIntent(activity,new ArrayList<StoreBean>()));
                    break;
                case OrderGridviewItemBean.TYPE_cancel_Order://取消订单
                    showCancelDialog(activity);
                    break;
                case OrderGridviewItemBean.TYPE_cancel_Order_beihuo://取消订单_正在备货
                    showCancelConfirmDialog(activity,bean);
                    break;
                case OrderGridviewItemBean.TYPE_cuidan://催单

                    break;
                case OrderGridviewItemBean.TYPE_change_order://改订单信息

                    break;

            }
        }
    }

    public static void callPhone(Context c,String s) {
        c.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //    取消订单dialog
    private static void showCancelDialog(BaseActivity activity) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.dialog_order_detail_cancel);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        OrderCancelAdapter orderTraceAdapter = new OrderCancelAdapter();
        recyclerview.setAdapter(orderTraceAdapter);
        orderTraceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                orderTraceAdapter.selected = position;
                orderTraceAdapter.notifyDataSetChanged();

            }
        });

        ArrayList<String> beans = new ArrayList<>();
        beans.add("忘记使用红包");
        beans.add("信息填写错误");
        beans.add("送达时间选错了");
        beans.add("买错了/买少了");
        beans.add("我不想要了");
        orderTraceAdapter.setNewInstance(beans);


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }
    //    取消订单dialog
    private static void showCancelConfirmDialog(BaseActivity activity, OrderGridviewItemBean bean) {
        Dialog dialog = new Dialog(activity, R.style.loadingDialogTheme);
        View inflate = View.inflate(activity, R.layout.dialog_order_detail_cancel_confirm, null);
        inflate.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        inflate.findViewById(R.id.tv_cancer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        inflate.findViewById(R.id.tv_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(activity,"10086");
            }
        });

        dialog.setContentView(inflate);
        dialog.show();
    }
    //购买配送卡省钱事
    public static void initBuyCardView(View view) {
        View buy_card = view.findViewById(R.id.buy_card);
        if(null != buy_card){
            buy_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), BuyCardActivity.class));
                }
            });
        }
    }

    //订单详情页有两种，
    public static void showOrderDetailActivity(Context mContext, int type) {
        if(type == Constants.TYPE_PAY_WAITE ||
                type == Constants.TYPE_READY ||
                type == Constants.TYPE_PEISONG ){
            Intent intent = new Intent(mContext, OrderDetailMapActivity.class);
            intent.putExtra("type",type);
            mContext.startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, OrderDetailActivity.class);
            intent.putExtra("type",type);
            mContext.startActivity(intent);
        }

    }

    //设置标签代码
    public static void initTAGViews(LinearLayout ll_mark) {
        Context mContext =  ll_mark.getContext();

        ll_mark.setVisibility(View.GONE);
        ll_mark.removeAllViews();
        ll_mark.setGravity(Gravity.START);

        try {
//            String strList = bean.getStampContents();
//            if (!TextUtils.isEmpty(strList)) {
//                List<StoreBean.StoreTag> list = new Gson().fromJson(strList, new TypeToken<List<StoreBean.StoreTag>>() {
//                }.getType());

            List<StoreBean.StoreTag> list = new ArrayList<>();
            list.add(new StoreBean.StoreTag("自营品牌",0));
            list.add(new StoreBean.StoreTag("休息中",1));
            list.add(new StoreBean.StoreTag("预定明日",2));

            if ( list.size() > 0) {
                ll_mark.setVisibility(View.VISIBLE);
                TextView textView;
                for (int a = 0; a < (list.size() > 3 ? 3 : list.size()); a++) {
                    StoreBean.StoreTag o = list.get(a);
                    textView = new TextView(mContext);
                    textView.setText(o.tag);
                    textView.setTextSize(10);

                    textView.setBackgroundResource(R.drawable.bg_stroke_tag);
                    GradientDrawable drawable = (GradientDrawable) textView.getBackground();

                    switch (o.type){
                        case 0:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_FF6200));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_FF6200));
                            break;
                        case 1:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_ccc));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_ccc));
                            break;
                        case 2:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_2CD18A));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_2CD18A));
                            break;
                    }

                    textView.setPadding(DisplayUtil.dip2px(mContext, 5), 0, DisplayUtil.dip2px(mContext, 5), 0);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    if (a == 0) {
                        layoutParams.leftMargin = 0;
                    } else {
                        layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 10);
                    }

                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(layoutParams);
                    textView.setGravity(Gravity.CENTER);
                    textView.setSingleLine();
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    ll_mark.addView(textView);

                }
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
