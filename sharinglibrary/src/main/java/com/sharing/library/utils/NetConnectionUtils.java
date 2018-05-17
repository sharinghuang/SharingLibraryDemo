package com.sharing.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * <p>网络连接工具类</p>
 *
 * @author hxm
 * @version 1.0 (2015/10/19)
 */
public class NetConnectionUtils {

    /**
     * 网络状态枚举
     */
    public enum NetworkConnectedState {
        /**
         * wifi网络
         */
        WIFI,
        /**
         * 移动网络
         */
        MOBILE,
        /**
         * 断开
         */
        DISCONNECT,
        /**
         * 其他
         */
        OTHER
    }

    /**
     * 判断是否有网络
     *
     * @param context 上下文
     *
     * @return true 有网络，false 没网络
     */
    public static boolean isNetworkStatus(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            State mobile = State.UNKNOWN;
            if (mobileInfo != null) {
                mobile = mobileInfo.getState();
            }
            State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (mobile == State.CONNECTED || wifi == State.CONNECTED) {
                return true;
            }
            NetworkInfo networkInfoEthernet = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
            if (networkInfoEthernet != null) {
                State defaultNetWork = networkInfoEthernet.getState();
                if (defaultNetWork == State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 获取网络连接状态，是3g、wifi还是未连接
     *
     * @param context 上下文
     *
     * @return NetworkConnectedState对象
     */
    public static NetworkConnectedState getNetworkState(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        // 未连接
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return NetworkConnectedState.DISCONNECT;
        }
        // wifi连接
        State state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state == State.CONNECTED) {
            return NetworkConnectedState.WIFI;
        }
        //mobile连接
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            state = mobileInfo.getState();
            if (state == State.CONNECTED) {
                return NetworkConnectedState.MOBILE;
            }
        }
        return NetworkConnectedState.OTHER;
    }

    /**
     * Determine whether to use wifi
     *
     * @param context 上下文
     */
    public static boolean isConnectedByWifi(Context context) {
        NetworkConnectedState info = getNetworkState(context);
        return info != null && info == NetworkConnectedState.WIFI;
    }
}
