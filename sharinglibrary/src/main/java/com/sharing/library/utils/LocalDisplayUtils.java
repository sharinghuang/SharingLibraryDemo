package com.sharing.library.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * <p>LocalDisplayUtils类 1、提供XXX功能；2、提供XXX方法</p>
 *
 * @author hxm<br/>
 * @version 1.0 (2018-5-24 15:33)<br/>
 */
public class LocalDisplayUtils {
    /** 屏幕宽度，单位像素px */
    public static int screenWidthPixels;
    /** 屏幕高度，单位像素px */
    public static int screenHeightPixels;
    /** 屏幕像素 */
    public static float screenDensity;
    /** 屏幕宽度，单位像素dp */
    public static int screenWidthDp;
    /** 屏幕高度，单位像素dp */
    public static int screenHeightDp;
    private static boolean sInitialed;

    /** 默认构造方法 */
    public LocalDisplayUtils() {
    }

    /**
     * 初始化，可以在全局Application中调用，只需要实例化一次
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        if (!sInitialed && context != null) {
            sInitialed = true;
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidthPixels = dm.widthPixels;
            screenHeightPixels = dm.heightPixels;
            screenDensity = dm.density;
            screenWidthDp = (int) ((float) screenWidthPixels / dm.density);
            screenHeightDp = (int) ((float) screenHeightPixels / dm.density);
        }
    }

    /**
     * 根据手机的分辨率，将px(像素)值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue px值
     */
    public static int px2dp(float pxValue) {
        final float scale = screenDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dp值
     */
    public static int dp2px(float dipValue) {
        final float scale = screenDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue 像素值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = screenDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue 字体sp值
     */
    public static int sp2px(float spValue) {
        final float fontScale = screenDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取layout资源id
     * 打包.jar包的时候,有时候会需要引用到res中的资源,这时候不能将资源一起打包,只能动态的获取资源
     *
     * @param context 上下文
     * @param name    资源名称如（tab_layout.xml 名称为tab_layout ）
     *
     * @return 资源id
     */
    public static int getLayoutId(Context context, String name) {
        return context.getResources().getIdentifier(name, "layout",
                context.getPackageName());
    }

    /**
     * 获取string资源id
     *
     * @param context 上下文
     * @param name    资源名称
     *
     * @return 资源id
     */
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string",
                context.getPackageName());
    }

    /**
     * 获取drawable资源id
     *
     * @param context 上下文
     * @param name    资源名称
     *
     * @return 资源id
     */
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

    /**
     * 获取style资源id
     *
     * @param context 上下文
     * @param name    资源名称
     *
     * @return 资源id
     */
    public static int getStyleId(Context context, String name) {
        return context.getResources().getIdentifier(name,
                "style", context.getPackageName());
    }

    /**
     * 获取id资源id
     *
     * @param context 上下文
     * @param name    资源名称）
     *
     * @return 资源id
     */
    public static int getId(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", context.getPackageName());
    }

    /**
     * 获取color资源id
     *
     * @param context 上下文
     * @param name    资源名称
     *
     * @return 资源id
     */
    public static int getColorId(Context context, String name) {
        return context.getResources().getIdentifier(name,
                "color", context.getPackageName());
    }

    /**
     * 获取array资源id
     *
     * @param context 上下文
     * @param name    资源名称
     *
     * @return 资源id
     */
    public static int getArrayId(Context context, String name) {
        return context.getResources().getIdentifier(name,
                "array", context.getPackageName());
    }
}
