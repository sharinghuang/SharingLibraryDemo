package com.sharing.library.utils;

/**
 * <p>GlideUtils类 1、提供XXX功能；2、提供XXX方法</p>
 *
 * @author hxm<br/>
 * @version 1.0 (2018-5-25 10:18)<br/>
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.sharing.library.R;
import com.sharing.library.listener.CommonRequestListener;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Glide 工具类
 * <p>图片显示</p>
 * <p>
 * 推荐使用：
 * 1）一般本地图片，使用show()
 * 2）广告，大图展示，图片要清晰，使用show()
 * 3) 详情或者列表，图片不需要那么清晰，使用load()
 * 4) 头像这些特别小的图片，使用loadIcon()
 * 5）需要圆形的图片，使用 loadCircle()
 * 6) 当控件不是ImageView，需要设置背景，使用setBackgroundResource()
 * <p>
 * <p>
 * show()        1. 全图加载，可用于网络图片，本地图片
 * load()        2. 网络的图片的 300dp小图加载， 适用于表格，详情，列表等的小图，300x300, 服务器自动生成，方便可备份 （占带宽30KB左右）
 * loadIcon()    3. 网络的图片的 头像小图加载， 适用于表格，详情，列表等的小图，300x300, 服务器自动生成，方便可备份 （占带宽30KB左右）
 * loadBySize()  4. 加载网络的图片，读取imageView的大小，如广告banner长为800x300 ,就显示 url_800x300.jpg
 * loadCircle()  5. 加载圆形的图片，本地和网路都适用
 * showGif()     6. 加载GIF图片，本地和网路都适用
 * setBackgroundResource() 6. 设置背景本地资源，当view不是imageview的时候
 * clearDiskCache() 7.开个线程，清除磁盘缓存
 * clearMemory() 8.UI线程，清除内存缓存
 */
public class GlideUtils {
    /**
     * 图片显示，直接展示
     *
     * @param context   上下文
     * @param imageView 控件
     * @param url       地址
     * @param <T>       类型
     */
    public static <T> void show(Context context, ImageView imageView, T url) {
        show(context, imageView, url, R.drawable.image_selector_default_img);
    }

    /**
     * 图片显示
     *
     * @param context    上下文
     * @param imageView  控件
     * @param url        地址
     * @param placeImage 占位符图片
     * @param <T>        类型
     */
    public static <T> void show(Context context, ImageView imageView, T url,
                                int placeImage) {
        if (url == null) {
            Glide.with(context).load(placeImage).into(imageView);
        } else {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
//                    .centerCrop()
                    .placeholder(placeImage)
                    .error(placeImage)
                    .into(imageView);
        }
    }

    /**
     * 图片显示，加载资源文件
     *
     * @param context   上下文
     * @param imageView 控件
     * @param res       资源
     */
    public static void showResource(Context context, ImageView imageView, int res) {
        Glide.with(context)
                .load(res)
                .asBitmap()
//                .centerCrop()
                .placeholder(R.color.gray_light40)
                .error(R.color.gray_light40)
                .into(imageView);
    }

    /**
     * 图片显示，加载资源文件
     * 其中radius的取值范围是1-25，radius越大，模糊度越高。
     *
     * @param context
     * @param imageView
     * @param res
     * @param radius
     */
    public static void showResourceByBlurTransformation(Context context, ImageView imageView, int res, int radius) {
        Glide.with(context)
                .load(res)
                .bitmapTransform(new BlurTransformation(context, radius))
                .placeholder(R.color.gray_light40)
                .error(R.color.gray_light40)
                .into(imageView);
    }

    /**
     * 图片显示，加载本地完整路径资源文件
     *
     * @param context   上下文
     * @param imageView 控件
     * @param res       资源
     */
    public static void showResourceByBlurTransformation(Context context, ImageView imageView, File res, int radius) {
        Glide.with(context)
                .load(res)
                .bitmapTransform(new BlurTransformation(context, radius))
                .placeholder(R.color.gray_light40)
                .error(R.color.gray_light40)
                .into(imageView);
    }

    /**
     * 图片显示，加载本地完整路径资源文件
     *
     * @param context   上下文
     * @param imageView 控件
     * @param res       资源
     */
    public static void showResource(Context context, ImageView imageView, File res) {
        Glide.with(context)
                .load(res)
                .asBitmap()
//                .centerCrop()
                .placeholder(R.color.gray_light40)
                .error(R.color.gray_light40)
                .into(imageView);
    }


    /**
     * 图片显示，加载资源文件
     *
     * @param context   上下文
     * @param imageView 控件
     * @param res       资源
     */
    public static void showResource(Context context, ImageView imageView, int res, int placeImage) {
        Glide.with(context)
                .load(res)
                .asBitmap()
//                .centerCrop()
                .placeholder(placeImage)
                .error(R.drawable.image_defaule_broken)
                .into(imageView);
    }

    /**
     * 图片显示，加载资源文件
     *
     * @param context   上下文
     * @param imageView 控件
     * @param res       资源
     */
    public static void showResourceFit(Context context, ImageView imageView, int res) {
        Glide.with(context)
                .load(res)
                .asBitmap()
                .fitCenter()
                .into(imageView);
    }


    /**
     * 圆形图片显示
     *
     * @param context   上下文
     * @param imageView 控件
     * @param url       地址
     */
    public static <T> void loadCircle(final Context context, final ImageView imageView, T url) {
        if (url == null) {
            Glide.with(context).load(R.drawable.image_selector_default_img).into(imageView);
        } else {
            Glide.with(context).load(url).asBitmap().centerCrop().error(R.color.gray_light40).into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }

    /**
     * 设置背景
     *
     * @param context 上下文
     * @param view    控件
     * @param res     资源
     * @param <T>     T
     */
    public static <T> void setBackgroundResource(Context context, final View view, T res) {
        if (res == null || res == "") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(context.getResources().getDrawable(R.drawable.image_selector_default_img));
            }
        } else {
            Glide.with(context).load(res).error(R.color.gray_light40).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        try {
                            view.setBackground(resource);
                        } catch (Exception e) {

                        }
                    }
                }
            });
        }
    }


    /**
     * 设置背景
     *
     * @param context 上下文
     * @param view    控件
     * @param res     资源
     * @param <T>     T
     */
    public static <T> void setBackgroundResourceByBlur(Context context, final View view, T res) {
        if (res == null || res == "") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(context.getResources().getDrawable(R.drawable.image_selector_default_img));
            }
        } else {
            Glide.with(context).load(res)
                    .bitmapTransform(new BlurTransformation(context, 10), new CenterCrop(context))
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                try {
                                    view.setBackground(resource);
                                } catch (Exception e) {

                                }
                            }
                        }
                    });
        }
    }


    /**
     * 设置背景
     *
     * @param context 上下文
     * @param view    控件
     * @param res     资源
     * @param width   width
     * @param height  height
     * @param <T>     T
     */
    public static <T> void setBackgroundResource(Context context, final View view, T res, int width, int height) {
        if (res == null || res == "") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(context.getResources().getDrawable(R.drawable.image_selector_default_img));
            }
        } else {
            Glide.with(context).load(res).override(width, height).error(R.color.gray_light40).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        try {
                            view.setBackground(resource);
                        } catch (Exception e) {

                        }
                    }
                }
            });
        }
    }

    /**
     * 给view加载本地背景图，主要是有一些页面需要在根view显示一张大图，防止OOM
     *
     * @param context    上下文
     * @param targetView 需要显示背景的view
     * @param resId      本地的图片资源id
     * @param centerCrop 是否居中裁剪，true表示裁剪，false表示按比例缩放
     */
    public static void loadResToViewBg(Context context, View targetView, int resId, boolean centerCrop) {
        final ViewTarget<View, Bitmap> viewTarget = new ViewTarget<View, Bitmap>(targetView) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    view.setBackgroundDrawable(new BitmapDrawable(resource));
                } catch (Exception e) {

                }
            }
        };
        if (centerCrop) {
            Glide.with(context).load(resId).asBitmap().centerCrop().into(viewTarget);
        } else {
            Glide.with(context).load(resId).asBitmap().fitCenter().into(viewTarget);
        }

    }

    /**
     * 加载动态图
     *
     * @param context   上下文
     * @param imageView 控件
     * @param target    对象
     * @param <T>       类型
     */
    public static <T> void showGif(Context context, ImageView imageView, T target) {
        showGif(context, imageView, target, R.drawable.image_selector_default_img);
    }


    /**
     * 加载动态图
     *
     * @param context    上下文
     * @param imageView  控件
     * @param target     对象
     * @param placeImage 占位符图片
     * @param <T>        类型
     */
    public static <T> void showGif(Context context, ImageView imageView, T target,
                                   int placeImage) {
        if (target == null) {
            Glide.with(context).load(placeImage).into(imageView);
        } else {
            Glide.with(context)
                    .load(target)
                    .asGif()
                    .placeholder(R.color.gray_light40)
                    .error(placeImage)
                    .into(imageView);
        }
    }

    /**
     * 加载gif
     *
     * @param context   上下文
     * @param imageView 控件
     * @param url       地址
     */
    public static void loadGif(final Context context, final ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .asGif()
                .fitCenter()
                .thumbnail(0.5f)
                .placeholder(R.color.gray_light40)
                .error(R.drawable.image_selector_default_img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    /**
     * 开个线程，清除磁盘缓存
     * 目录：SDCardUtils.IMAGE_CACHE_IN_FOLDER;
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context, final CommonRequestListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                listener.getResult(true);
            }
        }).start();
    }

    /**
     * UI线程，清除内存缓存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }
}