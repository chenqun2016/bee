package com.bee.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/30  20：30
 * 描述：
 */
public class PicassoEngine implements ImageEngine {

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        VideoRequestHandler videoRequestHandler = new VideoRequestHandler();
        if (PictureMimeType.isContent(url)) {
            Picasso.with(context)
                    .load(Uri.parse(url))
                    .into(imageView);
        } else {
            if (PictureMimeType.isUrlHasVideo(url)) {
                Picasso picasso = new Picasso.Builder(context.getApplicationContext())
                        .addRequestHandler(videoRequestHandler)
                        .build();
                picasso.load(videoRequestHandler.SCHEME_VIDEO + ":" + url)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(new File(url))
                        .into(imageView);
            }
        }
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @param callback      网络图片加载回调监听 {link after version 2.5.1 Please use the #OnImageCompleteCallback#}
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url,
                          @NonNull ImageView imageView,
                          SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        Picasso.with(context)
                .load(PictureMimeType.isContent(url) ? Uri.parse(url) : Uri.fromFile(new File(url)))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
                        if (callback != null) {
                            callback.onHideLoading();
                        }
                        if (resource != null) {
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(),
                                    resource.getHeight());
                            longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                            imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                            if (eqLongImage) {
                                // 加载长图
                                longImageView.setQuickScaleEnabled(true);
                                longImageView.setZoomEnabled(true);
                                longImageView.setDoubleTapZoomDuration(100);
                                longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImageView.setImage(ImageSource.bitmap(resource),
                                        new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        if (callback != null) {
                            callback.onHideLoading();
                        }
                    }



                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        if (callback != null) {
                            callback.onShowLoading();
                        }
                    }
                });
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @ 已废弃
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url,
                          @NonNull ImageView imageView,
                          SubsamplingScaleImageView longImageView) {
        Picasso.with(context)
                .load(PictureMimeType.isContent(url) ? Uri.parse(url) : Uri.fromFile(new File(url)))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
                        if (resource != null) {
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(),
                                    resource.getHeight());
                            longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                            imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                            if (eqLongImage) {
                                // 加载长图
                                longImageView.setQuickScaleEnabled(true);
                                longImageView.setZoomEnabled(true);
                                longImageView.setDoubleTapZoomDuration(100);
                                longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImageView.setImage(ImageSource.bitmap(resource),
                                        new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }

                    @Override
                    public void onBitmapFailed( Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        VideoRequestHandler videoRequestHandler = new VideoRequestHandler();
        if (PictureMimeType.isContent(url)) {
            Picasso.with(context)
                    .load(Uri.parse(url))
                    .resize(180, 180)
                    .centerCrop()
                    .placeholder(R.drawable.picture_image_placeholder)
                    .into(imageView);
        } else {
            if (PictureMimeType.isUrlHasVideo(url)) {
                Picasso picasso = new Picasso.Builder(context.getApplicationContext())
                        .addRequestHandler(videoRequestHandler)
                        .build();
                picasso.load(videoRequestHandler.SCHEME_VIDEO + ":" + url)
                        .resize(180, 180)
                        .centerCrop()
                        .placeholder(R.drawable.picture_image_placeholder)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(new File(url))
                        .resize(180, 180)
                        .centerCrop()
                        .placeholder(R.drawable.picture_image_placeholder)
                        .into(imageView);
            }
        }
    }


    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url,
                               @NonNull ImageView imageView) {
        if (PictureMimeType.isContent(url)) {
            Picasso.with(context)
                    .load(Uri.parse(url))
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(new File(url))
                    .into(imageView);
        }
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        VideoRequestHandler videoRequestHandler = new VideoRequestHandler();
        if (PictureMimeType.isContent(url)) {
            Picasso.with(context)
                    .load(Uri.parse(url))
                    .resize(200, 200)
                    .centerCrop()
                    .placeholder(R.drawable.picture_image_placeholder)
                    .into(imageView);
        } else {
            if (PictureMimeType.isUrlHasVideo(url)) {
                Picasso picasso = new Picasso.Builder(context.getApplicationContext())
                        .addRequestHandler(videoRequestHandler)
                        .build();
                picasso.load(videoRequestHandler.SCHEME_VIDEO + ":" + url)
                        .resize(200, 200)
                        .centerCrop()
                        .placeholder(R.drawable.picture_image_placeholder)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(new File(url))
                        .resize(200, 200)
                        .centerCrop()
                        .placeholder(R.drawable.picture_image_placeholder)
                        .into(imageView);
            }
        }
    }

    private PicassoEngine() {
    }

    private static PicassoEngine instance;

    public static PicassoEngine createPicassoEngine() {
        if (null == instance) {
            synchronized (PicassoEngine.class) {
                if (null == instance) {
                    instance = new PicassoEngine();
                }
            }
        }
        return instance;
    }



    public class VideoRequestHandler extends RequestHandler {
        public String SCHEME_VIDEO = "video";

        @Override
        public boolean canHandleRequest(Request data) {
            String scheme = data.uri.getScheme();
            return (SCHEME_VIDEO.equals(scheme));
        }

        @Nullable
        @Override
        public Result load(Request request, int networkPolicy) throws IOException {
            Uri uri = request.uri;
            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                Bitmap bm = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                return new Result(bm, Picasso.LoadedFrom.DISK);
            }
            return null;
        }
    }
}