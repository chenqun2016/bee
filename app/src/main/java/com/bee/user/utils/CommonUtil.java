package com.bee.user.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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

import com.bee.base.AppGlobals;
import com.bee.user.R;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.OrderDetailBean;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.TraceBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
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
import com.bee.user.ui.order.ShouHouActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.Constants.TYPE_ORDER_DPS;
import static com.bee.user.Constants.TYPE_ORDER_GPS;
import static com.bee.user.Constants.TYPE_ORDER_HPS;
import static com.bee.user.Constants.TYPE_ORDER_JMJ;
import static com.bee.user.Constants.TYPE_ORDER_OC;
import static com.bee.user.Constants.TYPE_ORDER_OF;
import static com.bee.user.Constants.TYPE_ORDER_OFP;
import static com.bee.user.Constants.TYPE_ORDER_OMJ;
import static com.bee.user.Constants.TYPE_ORDER_OPS;
import static com.bee.user.Constants.TYPE_ORDER_PJ;
import static com.bee.user.Constants.TYPE_ORDER_QPS;
import static com.bee.user.Constants.TYPE_ORDER_SPS;
import static com.bee.user.Constants.TYPE_ORDER_SS;
import static com.bee.user.Constants.TYPE_ORDER_TK;
import static com.bee.user.Constants.TYPE_ORDER_WMJ;
import static com.bee.user.Constants.TYPE_ORDER_WP;
import static com.bee.user.Constants.TYPE_ORDER_WPS;
import static com.bee.user.bean.DictByTypeBean.TYPE_REFUND_REASON;

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
    public static void initCommentAdapter(RecyclerView images,String[] list) {
        if(null == list || list.length==0){
            return;
        }
        images.setLayoutManager(new LinearLayoutManager(images.getContext(),LinearLayoutManager.HORIZONTAL,false));
        List<String> strings = Arrays.asList(list);
        CommentImagesAdapter commentImagesAdapter = new CommentImagesAdapter(strings);
        images.setAdapter(commentImagesAdapter);

        commentImagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent(images.getContext(), ImagesActivity.class);
                intent.putExtra("datas", (Serializable) strings);
                intent.putExtra("position", position);
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

    public static void performOrderGridviewClick(BaseActivity activity, OrderGridviewItemAdapter adapter, int i, int id, OrderDetailBean orderDetailBean) {
        OrderGridviewItemBean bean = adapter.getList().get(i);
        if(null != bean){
            int type =  bean.type;

            Intent intent;
            switch (type){
                case OrderGridviewItemBean.TYPE_reOrder://再来一单
                    activity. startActivity(OrderingActivity.newIntent(activity,0,new ArrayList<>(),new ArrayList<>()));
                    break;
                case OrderGridviewItemBean.TYPE_comment://评价得积分
                    OrderCommentActivity.newInstance(activity,id,orderDetailBean.storeId,orderDetailBean,true);
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
                    activity.  startActivity(PayActivity.newIntent(activity,null,0));
                    break;
                case OrderGridviewItemBean.TYPE_cancel_Order://取消订单
                    showCancelDialog(activity,id);
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
    private static void showCancelDialog(BaseActivity activity,int id) {
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

        Api.getClient(HttpRequest.baseUrl_sys).getDictByType(TYPE_REFUND_REASON).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DictByTypeBean>>() {
                    @Override
                    public void onSuccess(List<DictByTypeBean> dictByType) {
                        orderTraceAdapter.setNewInstance(dictByType);
                    }
                });

        bottomSheetDialog.setCanceledOnTouchOutside(false);

        bottomSheetDialog.findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DictByTypeBean> data = orderTraceAdapter.getData();
                DictByTypeBean dictByTypeBean = data.get(orderTraceAdapter.selected);

                Api.getClient(HttpRequest.baseUrl_order).closeOrder(dictByTypeBean.getDictValue(),id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Boolean aBoolean) {
                                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                                    bottomSheetDialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });
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
    public static void showOrderDetailActivity(Context mContext, OrderBean bean) {
        String type = bean.getOrderItemType();
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("id",bean.id);
        mContext.startActivity(intent);
//        if(Constants.TYPE_ORDER_WP.equals(type) ||
//                Constants.TYPE_ORDER_OMJ.equals(type) ||
//                Constants.TYPE_ORDER_SPS.equals(type)){
//            Intent intent = new Intent(mContext, OrderDetailMapActivity.class);
//            intent.putExtra("type",type);
//            intent.putExtra("id",bean.id);
//            mContext.startActivity(intent);
//        }else{
//            Intent intent = new Intent(mContext, OrderDetailActivity.class);
//            intent.putExtra("type",type);
//            intent.putExtra("id",bean.id);
//            mContext.startActivity(intent);
//        }

    }

    public static void initTAGViews(LinearLayout ll_mark) {
        List<StoreBean.StoreTag> list = new ArrayList<>();
        list.add(new StoreBean.StoreTag("自营品牌",0));
        list.add(new StoreBean.StoreTag("休息中",1));
        list.add(new StoreBean.StoreTag("预定明日",2));
        initTAGViews(ll_mark,list);
    }
    //设置标签代码
    public static void initTAGViews(LinearLayout ll_mark,List<StoreBean.StoreTag> list) {
        Context mContext =  ll_mark.getContext();

        ll_mark.setVisibility(View.GONE);
        ll_mark.removeAllViews();
        ll_mark.setGravity(Gravity.START);

        try {
//            String strList = bean.getStampContents();
//            if (!TextUtils.isEmpty(strList)) {
//                List<StoreBean.StoreTag> list = new Gson().fromJson(strList, new TypeToken<List<StoreBean.StoreTag>>() {
//                }.getType());



            if ( list.size() > 0) {
                ll_mark.setVisibility(View.VISIBLE);
                TextView textView;
                for (int a = 0; a < (list.size() > 3 ? 3 : list.size()); a++) {
                    StoreBean.StoreTag o = list.get(a);
                    if(TextUtils.isEmpty(o.tag)){
                        continue;
                    }
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


    public static  void copyContentToClipboard(String content, Context context) {
        try {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", content);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtil.ToastShort(context,"已复制");
        }catch (Exception e){
            e.printStackTrace();
            ToastUtil.ToastShort(context,"复制失败");
        }

    }

    public static String getLocationTag(int tag) {
        String tagStr ;
        switch (tag){
            case 1:
                tagStr = "家";
                break;
            case 2:
                tagStr = "公司";
                break;
            case 3:
                tagStr = "学校";
                break;
            default:
                tagStr = "";
                break;
        }
        return tagStr;
    }

    public static String getNomalMoneyType(Integer orderAmount) {
        return "" + orderAmount/100 ;
    }

    public static String getTradeType(int status){
        String str ;
        switch (status){
            case  1:
                str = "交易成功";
                break;
            case  0:
                str = "交易处理中";
                break;
            case  -1:
                str ="交易失败";
                break;
            default:
                str ="交易成功";
                break;
        }
        return str;
    }

    //交易类型[I.收入（米粒） S.支出 （米粒）C.充值]
    public static String getTradeType(String type){
        String str ;
        switch (type){
            case  "I":
                str = "+";
                break;
            case  "S":
                str = "-";
                break;
            case  "C":
                str ="+";
                break;
            default:
                str ="+";
                break;
        }
        return str;
    }
    public static String getBizTypeStr(String bizType) {
        String str;
        switch (bizType){
            case  "C":
                str = "普通账户充值";
                break;
            case  "P":
                str = "配送卡充值";
                break;
            case  "X":
                str = "消费米粒";
                break;
            default:
                str = "普通账户充值";
                break;
        }
        return str;
    }

    //yyyy-MM-dd HH:mm:ss
    public static String getNomalTime(Date createTime) {
        if(null == createTime){
            return "";
        }
        return TimeUtils.date2String(createTime);
    }
    //yyyy-MM-dd
    public static String getNomalTime2(Date createTime) {
        if(null == createTime){
            return "";
        }
        return TimeUtils.date2String(createTime,new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
    }

    public static boolean jumpToApp(Context context, String deepLink) {
        try {
            if (context == null) {
                context = AppGlobals.getApplication();
            }
            Intent intent = Intent.parseUri(deepLink, Intent.URI_INTENT_SCHEME);
            intent.setComponent(null);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     public static final String TYPE_ORDER_SS = "SS";//订单提交成功
     public static final String TYPE_ORDER_OFP = "OFP";//订单已支付
     public static final String TYPE_ORDER_WMJ = "WMJ";//商家待接单
     public static final String TYPE_ORDER_JMJ = "JMJ";//商家分拣完成
     public static final String TYPE_ORDER_WPS = "WPS";//配送员待接单
     public static final String TYPE_ORDER_OPS = "OPS";//配送员已接单
     public static final String TYPE_ORDER_QPS = "QPS";//配送员取货中
     public static final String TYPE_ORDER_GPS = "GPS";//配送员已到店
     public static final String TYPE_ORDER_HPS = "HPS";//配送员已取货
     public static final String TYPE_ORDER_OF = "OF";//订单已完成

     public static final String TYPE_ORDER_WP = "WP";//订单待支付
     public static final String TYPE_ORDER_DPS = "DPS";//商品已送达
     public static final String TYPE_ORDER_OMJ = "OMJ";//商家已接单
     public static final String TYPE_ORDER_SPS = "SPS";//配送员送货中
     public static final String TYPE_ORDER_OC = "OC";//订单已取消

     //TODO
     public static final String TYPE_ORDER_TK = "S";//待评价
     public static final String TYPE_ORDER_PJ = "SS";//退款
     */
    public static String getOrderTypeName(String orderItemType) {
        switch (orderItemType){
            case TYPE_ORDER_SS:
                return "订单提交成功";
            case TYPE_ORDER_OFP:
                return "订单已支付";
            case TYPE_ORDER_WMJ:
                return "商家待接单";
            case TYPE_ORDER_JMJ:
                return "商家分拣完成";
            case TYPE_ORDER_WPS:
                return "配送员待接单";
            case TYPE_ORDER_OPS:
                return "配送员已接单";
            case TYPE_ORDER_QPS:
                return "配送员取货中";
            case TYPE_ORDER_GPS:
                return "配送员已到店";
            case TYPE_ORDER_HPS:
                return "配送员已取货";
            case TYPE_ORDER_OF:
                return "订单已完成";
            case TYPE_ORDER_WP:
                return "订单待支付";
            case TYPE_ORDER_DPS:
                return "商品已送达";
            case TYPE_ORDER_OMJ:
                return "商家已接单";
            case TYPE_ORDER_SPS:
                return "配送员送货中";

            case TYPE_ORDER_OC:
                return "订单已取消";
            case TYPE_ORDER_TK:
                return "待评价";
            case TYPE_ORDER_PJ:
                return "退款";
        }
        return "";
    }
}
