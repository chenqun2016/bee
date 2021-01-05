package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
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
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.bee.user.ui.xiadan.PayStatusActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.PayPassView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.List;

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


    @OnClick({R.id.tv_icon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_icon:
                showTakePhoneDialog();
                break;
        }
    }

    private void showTakePhoneDialog() {
        BaseDialog bottomSheetDialog = new BaseDialog(this){
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_take_phone;
            }

            @Override
            protected void initViews(BaseDialog dialog) {
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != dialog && dialog.isShowing()){
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
                                        if(null != result && result.size()>0){
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

                                        if(null != result && result.size()>0){
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


    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initViews() {

    }
}
