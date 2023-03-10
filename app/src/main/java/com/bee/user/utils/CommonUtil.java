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
import java.util.Calendar;
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
 * ????????????????????????
 * ???????????????2020/09/09  15???29
 * ?????????
 */
public class CommonUtil {
    public static final java.text.DecimalFormat myformat = new java.text.DecimalFormat("###,##0.0000");

    public static String moneyType(double d) {
        String format = myformat.format(d);
        int i = format.lastIndexOf(".");
        return format.substring(0, i + 3);
    }



    //??????????????????
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

    //????????????
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

    //????????????
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

//    ????????????food??????
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
                case OrderGridviewItemBean.TYPE_reOrder://????????????
                    activity. startActivity(OrderingActivity.newIntent(activity,0,new ArrayList<>(),new ArrayList<>()));
                    break;
                case OrderGridviewItemBean.TYPE_comment://???????????????
                    OrderCommentActivity.newInstance(activity,id,orderDetailBean.storeId,orderDetailBean,OrderCommentActivity.TYPE_NEW_COMMENT);
                    break;
                case OrderGridviewItemBean.TYPE_shouhou://????????????
                    intent = new Intent(activity, ShouHouActivity.class);
                    activity.  startActivity(intent);
                    break;
                case OrderGridviewItemBean.TYPE_contact_shop://????????????
                    callPhone(activity,"10010");
                    break;
                case OrderGridviewItemBean.TYPE_contact_rider://????????????
                    callPhone(activity,"10086");
                    break;
                case OrderGridviewItemBean.TYPE_pay_now://????????????
                    activity.  startActivity(PayActivity.newIntent(activity,null,0));
                    break;
                case OrderGridviewItemBean.TYPE_cancel_Order://????????????
                    showCancelDialog(activity,id);
                    break;
                case OrderGridviewItemBean.TYPE_cancel_Order_beihuo://????????????_????????????
                    showCancelConfirmDialog(activity,bean);
                    break;
                case OrderGridviewItemBean.TYPE_cuidan://??????

                    break;
                case OrderGridviewItemBean.TYPE_change_order://???????????????

                    break;

            }
        }
    }

    public static void callPhone(Context c,String s) {
        if(null != c){
            c.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    //    ????????????dialog
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
            e.printStackTrace();
        }

        bottomSheetDialog.show();
    }
    //    ????????????dialog
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
    //????????????????????????
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

    //???????????????????????????
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
        list.add(new StoreBean.StoreTag("????????????",0));
        list.add(new StoreBean.StoreTag("?????????",1));
        list.add(new StoreBean.StoreTag("????????????",2));
        initTAGViews(ll_mark,list);
    }
    //??????????????????
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
            //???????????????????????????
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // ?????????????????????ClipData
            ClipData mClipData = ClipData.newPlainText("Label", content);
            // ???ClipData?????????????????????????????????
            cm.setPrimaryClip(mClipData);
            ToastUtil.ToastShort(context,"?????????");
        }catch (Exception e){
            e.printStackTrace();
            ToastUtil.ToastShort(context,"????????????");
        }

    }

    public static String getLocationTag(int tag) {
        String tagStr ;
        switch (tag){
            case 1:
                tagStr = "???";
                break;
            case 2:
                tagStr = "??????";
                break;
            case 3:
                tagStr = "??????";
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
                str = "????????????";
                break;
            case  0:
                str = "???????????????";
                break;
            case  -1:
                str ="????????????";
                break;
            default:
                str ="????????????";
                break;
        }
        return str;
    }

    //????????????[I.?????????????????? S.?????? ????????????C.??????]
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
                str = "??????????????????";
                break;
            case  "P":
                str = "???????????????";
                break;
            case  "X":
                str = "????????????";
                break;
            default:
                str = "??????????????????";
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
     public static final String TYPE_ORDER_SS = "SS";//??????????????????
     public static final String TYPE_ORDER_OFP = "OFP";//???????????????
     public static final String TYPE_ORDER_WMJ = "WMJ";//???????????????
     public static final String TYPE_ORDER_JMJ = "JMJ";//??????????????????
     public static final String TYPE_ORDER_WPS = "WPS";//??????????????????
     public static final String TYPE_ORDER_OPS = "OPS";//??????????????????
     public static final String TYPE_ORDER_QPS = "QPS";//??????????????????
     public static final String TYPE_ORDER_GPS = "GPS";//??????????????????
     public static final String TYPE_ORDER_HPS = "HPS";//??????????????????
     public static final String TYPE_ORDER_OF = "OF";//???????????????

     public static final String TYPE_ORDER_WP = "WP";//???????????????
     public static final String TYPE_ORDER_DPS = "DPS";//???????????????
     public static final String TYPE_ORDER_OMJ = "OMJ";//???????????????
     public static final String TYPE_ORDER_SPS = "SPS";//??????????????????
     public static final String TYPE_ORDER_OC = "OC";//???????????????

     //TODO
     public static final String TYPE_ORDER_TK = "S";//?????????
     public static final String TYPE_ORDER_PJ = "SS";//??????
     */
    public static String getOrderTypeName(String orderItemType) {
        switch (orderItemType){
            case TYPE_ORDER_SS:
                return "??????????????????";
            case TYPE_ORDER_OFP:
                return "???????????????";
            case TYPE_ORDER_WMJ:
                return "???????????????";
            case TYPE_ORDER_JMJ:
                return "??????????????????";
            case TYPE_ORDER_WPS:
                return "??????????????????";
            case TYPE_ORDER_OPS:
                return "??????????????????";
            case TYPE_ORDER_QPS:
                return "??????????????????";
            case TYPE_ORDER_GPS:
                return "??????????????????";
            case TYPE_ORDER_HPS:
                return "??????????????????";
            case TYPE_ORDER_OF:
                return "???????????????";
            case TYPE_ORDER_WP:
                return "???????????????";
            case TYPE_ORDER_DPS:
                return "???????????????";
            case TYPE_ORDER_OMJ:
                return "???????????????";
            case TYPE_ORDER_SPS:
                return "??????????????????";

            case TYPE_ORDER_OC:
                return "???????????????";
            case TYPE_ORDER_TK:
                return "?????????";
            case TYPE_ORDER_PJ:
                return "??????";
        }
        return "";
    }

    /**
     * ????????????????????????????????????
     * time=yyyy-MM-dd
     * @return
     */
    public static String getWeek(int day) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        if(day > 0){
            c.add(Calendar.DAY_OF_WEEK,1);
        }
        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "?????????";
        }
        if (wek == 2) {
            Week += "?????????";
        }
        if (wek == 3) {
            Week += "?????????";
        }
        if (wek == 4) {
            Week += "?????????";
        }
        if (wek == 5) {
            Week += "?????????";
        }
        if (wek == 6) {
            Week += "?????????";
        }
        if (wek == 7) {
            Week += "?????????";
        }
        return Week;
    }
}
