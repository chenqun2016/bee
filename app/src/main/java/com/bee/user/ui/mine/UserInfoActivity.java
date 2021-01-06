package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoEngine;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.RoundedCornersTransform;
import com.bee.user.event.MainEvent;
import com.bee.user.event.UserInfoItemEvent;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.login.CodeLoginActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.bee.user.ui.xiadan.PayStatusActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.PayPassView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  16：34
 * 描述：
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.tv_icon)
    ImageView tv_icon;


    @BindView(R.id.tv_mingcheng_text)
    TextView tv_mingcheng_text;

    @BindView(R.id.tv_xingbie_text)
    TextView tv_xingbie_text;
    @BindView(R.id.tv_shengri_text)
    TextView tv_shengri_text;
    @BindView(R.id.tv_zhiye_text)
    TextView tv_zhiye_text;
    @BindView(R.id.tv_qianming_text)
    TextView tv_qianming_text;
    @BindView(R.id.tv_dizhi_text)
    TextView tv_dizhi_text;
    @BindView(R.id.tv_phone_text)
    TextView tv_phone_text;
    @BindView(R.id.tv_weiixn_text)
    TextView tv_weiixn_text;
    @BindView(R.id.tv_weibo_text)
    TextView tv_weibo_text;

    @OnClick({R.id.tv_icon, R.id.tv_mingcheng_text
            , R.id.tv_xingbie_text, R.id.tv_shengri_text, R.id.tv_zhiye_text, R.id.tv_qianming_text
            , R.id.tv_dizhi_text, R.id.tv_phone_text, R.id.tv_weiixn_text, R.id.tv_weibo_text})
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.tv_icon:
                showTakePhoneDialog();
                break;
            case R.id.tv_mingcheng_text://名称
                intent  = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type",UserInfoItemEvent.TYPE_Name);
                intent.putExtra("str1","修改名称");
                intent.putExtra("str2","昵称为2～32位字符，支持中文、英文、数字");
                intent.putExtra("str3","ss");
                startActivity(intent);
                break;

            case R.id.tv_xingbie_text://性别
                showXingBieDialog();
                break;
            case R.id.tv_shengri_text://生日
                showBirthdayDialog();
                break;
            case R.id.tv_zhiye_text://职业
                intent = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type",UserInfoItemEvent.TYPE_Profession);
                intent.putExtra("str1","职业");
                intent.putExtra("str2","职业为2～32位字符，支持中文、英文、数字");
                intent.putExtra("str3","工程师");
                startActivity(intent);
                break;
            case R.id.tv_qianming_text://个性签名
                intent = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type",UserInfoItemEvent.TYPE_Sign);
                intent.putExtra("str1","个性签名");
                intent.putExtra("str2","签名为2～160位字符，支持中文、英文、数字");
                intent.putExtra("str3","天下没有难做的生意");
                startActivity(intent);
                break;
            case R.id.tv_dizhi_text://收货地址
                intent = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type",UserInfoItemEvent.TYPE_Location);
                intent.putExtra("str1","收货地址");
                intent.putExtra("str2","收货地址为2～160位字符，支持中文、英文、数字");
                intent.putExtra("str3","上海");
                startActivity(intent);
                break;
            case R.id.tv_phone_text://手机号
                break;
            case R.id.tv_weiixn_text://微信号
                break;
            case R.id.tv_weibo_text://微博号
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showXingBieDialog() {
        BaseDialog bottomSheetDialog = new BaseDialog(this) {
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_take_phone;
            }

            @Override
            protected void initViews(BaseDialog dialog) {
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != dialog && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                TextView tv_1 = dialog.findViewById(R.id.tv_1);
                tv_1.setText("男性");
                tv_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_xingbie_text.setText("男");
                        dialog.dismiss();
                    }
                });

                TextView tv_2 = dialog.findViewById(R.id.tv_2);
                tv_2.setText("女性");
                tv_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_xingbie_text.setText("女");
                        dialog.dismiss();
                    }
                });
            }
        };
        bottomSheetDialog.show();
    }
    private TimePickerView pvTime;
    //    //时间选择器
    private void showBirthdayDialog() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR,-100);
        //时间选择器
         pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date.getTime() > new Date().getTime()) {
                    date = new Date();
                }

                tv_shengri_text.setText(getDateLong(date.getTime()));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//分别对应 年月日时分秒，默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")

                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(15)//滚轮文字大小
                .setTitleSize(17)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.color_282525))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.color_3e7dfb))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_7C7877))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRangDate(instance, Calendar.getInstance())//默认是1900-2100年
                .setLineSpacingMultiplier(3.0f)

                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .build();


        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateLong(long d) {
        Date date = new Date(d);
        String nowDate = "";
        try {
            nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }


    private void showTakePhoneDialog() {
        BaseDialog bottomSheetDialog = new BaseDialog(this) {
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_take_phone;
            }

            @Override
            protected void initViews(BaseDialog dialog) {
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != dialog && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                TextView tv_1 = dialog.findViewById(R.id.tv_1);
                tv_1.setText("拍照");
                tv_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PictureSelector.create(UserInfoActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .imageEngine(PicassoEngine.createPicassoEngine())
                                .isCompress(true)

                                .isEnableCrop(true)// 是否裁剪
//                                .cropImageWideHigh(DisplayUtil.dip2px(UserInfoActivity.this,36),DisplayUtil.dip2px(UserInfoActivity.this,36))
                                .circleDimmedLayer(true)
                                .rotateEnabled(false) // 裁剪是否可旋转图片
                                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                                .setCircleDimmedBorderColor(getResources().getColor(R.color.white))//设置圆形裁剪边框色值
                                .setCircleStrokeWidth(1)//设置圆形裁剪边框粗细
                                .setCropDimmedColor(getResources().getColor(R.color.color_FF6200_10))
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false

                                .forResult(new OnResultCallbackListener<LocalMedia>() {
                                    @Override
                                    public void onResult(List<LocalMedia> result) {
                                        // 结果回调
                                        if (null != result && result.size() > 0) {
                                            LocalMedia localMedia = result.get(0);

                                            Picasso.with(UserInfoActivity.this)
                                                    .load(new File(localMedia.getCutPath()))
                                                    .transform(new RoundedCornersTransform())
                                                    .error(R.drawable.icon_touxiang)
                                                    .placeholder(R.drawable.icon_touxiang)
                                                    .into(tv_icon);
                                        }
                                    }

                                    @Override
                                    public void onCancel() {
                                        // 取消
                                    }
                                });

                        dialog.dismiss();
                    }
                });
                TextView tv_2 = dialog.findViewById(R.id.tv_2);
                tv_2.setText("相册选择");
                tv_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PictureSelector.create(UserInfoActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .imageEngine(PicassoEngine.createPicassoEngine())
                                .setPictureStyle(PictureParameterStyle.ofSelectTotalStyle())
                                .selectionMode(PictureConfig.SINGLE)
                                .isCamera(false)
                                .isCompress(true)
                                .isSingleDirectReturn(true)

                                .isEnableCrop(true)// 是否裁剪
//                                .cropImageWideHigh(DisplayUtil.dip2px(UserInfoActivity.this,36),DisplayUtil.dip2px(UserInfoActivity.this,36))
                                .circleDimmedLayer(true)
                                .rotateEnabled(false) // 裁剪是否可旋转图片
                                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                                .setCircleDimmedBorderColor(getResources().getColor(R.color.white))//设置圆形裁剪边框色值
                                .setCircleStrokeWidth(1)//设置圆形裁剪边框粗细
                                .setCropDimmedColor(getResources().getColor(R.color.color_FF6200_10))
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false

                                .forResult(new OnResultCallbackListener<LocalMedia>() {
                                    @Override
                                    public void onResult(List<LocalMedia> result) {
                                        // 结果回调

                                        if (null != result && result.size() > 0) {
                                            LocalMedia localMedia = result.get(0);

                                            Picasso.with(UserInfoActivity.this)
                                                    .load(new File(localMedia.getCutPath()))
                                                    .transform(new RoundedCornersTransform())

                                                    .error(R.drawable.icon_touxiang)
                                                    .placeholder(R.drawable.icon_touxiang)
                                                    .into(tv_icon);
                                        }
                                    }

                                    @Override
                                    public void onCancel() {
                                        // 取消
                                    }
                                });
                        dialog.dismiss();
                    }
                });
            }
        };
        bottomSheetDialog.show();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoItemEvent(UserInfoItemEvent event) {
        switch (event.getType()){
            case UserInfoItemEvent.TYPE_Name:
                tv_mingcheng_text.setText(event.getText());
                break;
            case UserInfoItemEvent.TYPE_Profession:
                tv_zhiye_text.setText(event.getText());
                break;
            case UserInfoItemEvent.TYPE_Sign:
                tv_qianming_text.setText(event.getText());
                break;

            case UserInfoItemEvent.TYPE_Location:
                tv_dizhi_text.setText(event.getText());
                break;
        }
    }
}
