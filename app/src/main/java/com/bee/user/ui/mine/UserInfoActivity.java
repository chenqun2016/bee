package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.Constants;
import com.bee.user.PicassoEngine;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.RoundedCornersTransform;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.UploadImageBean;
import com.bee.user.bean.UserBean;
import com.bee.user.event.UserInfoItemEvent;
import com.bee.user.params.UserInfoParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bee.user.ui.xiadan.ChooseAddressActivity.REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING;

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

    //阿里云上传后返回的地址
    private String icon ;
    private boolean isChanged = false;
    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        UserBean userInfo = SPUtils.geTinstance().getUserInfo();
        if(null != userInfo && !TextUtils.isEmpty(userInfo.icon)){
            icon = userInfo.icon;
            Picasso.with(UserInfoActivity.this)
                    .load(userInfo.icon)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(UserInfoActivity.this,100),0, PicassoRoundTransform.CornerType.ALL))
                    .into(tv_icon);

        }
        tv_mingcheng_text.setText(userInfo.nickname+"");
        tv_xingbie_text.setText(userInfo.gender==1?"男":"女");
        tv_shengri_text.setText(userInfo.birthday+"");
        tv_zhiye_text.setText(userInfo.job+"");
        tv_qianming_text.setText(userInfo.personalizedSignature+"");
        tv_dizhi_text.setText(userInfo.city+"");
        tv_phone_text.setText(userInfo.phone+"");
    }

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
                intent = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type", UserInfoItemEvent.TYPE_Name);
                intent.putExtra("str1", "修改名称");
                intent.putExtra("str2", "昵称为2～32位字符，支持中文、英文、数字");
                intent.putExtra("str3", tv_mingcheng_text.getText().toString());
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
                intent.putExtra("type", UserInfoItemEvent.TYPE_Profession);
                intent.putExtra("str1", "职业");
                intent.putExtra("str2", "职业为2～32位字符，支持中文、英文、数字");
                intent.putExtra("str3", tv_zhiye_text.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_qianming_text://个性签名
                intent = new Intent(UserInfoActivity.this, UserInfoItemActivity.class);
                intent.putExtra("type", UserInfoItemEvent.TYPE_Sign);
                intent.putExtra("str1", "个性签名");
                intent.putExtra("str2", "签名为2～160位字符，支持中文、英文、数字");
                intent.putExtra("str3", tv_qianming_text.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_dizhi_text://收货地址
                intent = new Intent(UserInfoActivity.this, ChooseAddressActivity.class);
                intent.putExtra("from",1);
                startActivityForResult(intent, REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING);

                break;
            case R.id.tv_phone_text://手机号
                break;
            case R.id.tv_weiixn_text://微信号
                break;
            case R.id.tv_weibo_text://微博号
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING && resultCode == 1) {

            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            tv_dizhi_text.setText(address.detailAddress+address.houseNumber);
            setUserDatas();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
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
                        setUserDatas();
                    }
                });

                TextView tv_2 = dialog.findViewById(R.id.tv_2);
                tv_2.setText("女性");
                tv_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_xingbie_text.setText("女");
                        dialog.dismiss();
                        setUserDatas();
                    }
                });
            }
        };
        bottomSheetDialog.show();
    }

    private TimePickerView pvTime;

    //    //时间选择器
    private void showBirthdayDialog() {

        //时间选择器
        if (null == pvTime) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.YEAR, -100);
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    if (date.getTime() > System.currentTimeMillis()) {
                        date = new Date();
                    }

                    tv_shengri_text.setText(getDateLong(date.getTime()));
                    setUserDatas();
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

        }

        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }


    public static String getDateLong(long d) {
        Date date = new Date(d);
        String nowDate = "";
        try {
            nowDate =Constants. sdfLong2.format(date);
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
                                .withAspectRatio(1000,1000)
                                .cutOutQuality(60)
                                .minimumCompressSize(1024)
                                .cropImageWideHigh(1000,1000)
                                .forResult(new OnResultCallbackListener<LocalMedia>() {
                                    @Override
                                    public void onResult(List<LocalMedia> result) {
                                        // 结果回调
                                        if (null != result && result.size() > 0) {
                                            LocalMedia localMedia = result.get(0);
                                            upImageToOss(localMedia);
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
                                .withAspectRatio(1000,1000)
                                .cutOutQuality(60)
                                .minimumCompressSize(1024)
                                .cropImageWideHigh(1000,1000)
                                .forResult(new OnResultCallbackListener<LocalMedia>() {
                                    @Override
                                    public void onResult(List<LocalMedia> result) {
                                        // 结果回调

                                        if (null != result && result.size() > 0) {
                                            LocalMedia localMedia = result.get(0);



                                            upImageToOss(localMedia);
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

    /**
     * 上传图片
     * @param media
     */
    private void upImageToOss(LocalMedia media) {
        String path = media.getCutPath();
        File file = new File(path);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.getClient(HttpRequest.baseUrl_file).uploadObj(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UploadImageBean>() {
                    @Override
                    public void onSuccess(UploadImageBean uploadImageBean) {
                        if(uploadImageBean != null) {
                            icon = uploadImageBean.getUrl();
                            Picasso.with(UserInfoActivity.this)
                                    .load(icon)
                                    .transform(new RoundedCornersTransform())
                                    .error(R.drawable.icon_touxiang)
                                    .into(tv_icon);

                            setUserDatas();
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoItemEvent(UserInfoItemEvent event) {
        switch (event.getType()) {
            case UserInfoItemEvent.TYPE_Name:
                tv_mingcheng_text.setText(event.getText());
                break;
            case UserInfoItemEvent.TYPE_Profession:
                tv_zhiye_text.setText(event.getText());
                break;
            case UserInfoItemEvent.TYPE_Sign:
                tv_qianming_text.setText(event.getText());
                break;
            default:
                break;

        }

        setUserDatas();

    }


    private void setUserDatas() {
        UserInfoParams userInfoParams = new UserInfoParams();
        userInfoParams.id = Integer.parseInt(SPUtils.geTinstance().getUid());
        userInfoParams.username = "";
        userInfoParams.nickname = tv_mingcheng_text.getText().toString()+"";
        userInfoParams.phone = tv_phone_text.getText().toString()+"";
        userInfoParams.icon = icon+"";
        userInfoParams.gender = tv_xingbie_text.getText().toString().contains("男")?1:2;
        userInfoParams.birthday = tv_shengri_text.getText().toString()+"";
        userInfoParams.city = tv_dizhi_text.getText().toString();
        userInfoParams.job = tv_zhiye_text.getText().toString()+"";
        userInfoParams.personalizedSignature = tv_qianming_text.getText().toString()+"";

        Api.getClient(HttpRequest.baseUrl_member).modifyMemberInfo(Api.getRequestBody(userInfoParams)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String str) {
                        if(!isChanged){
                            isChanged = true;
                            setResult(Constants.RESULT_CODE_USERINFO);
                        }


                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
}
