package com.sharing.library.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


/**
 * <p>手机信息工具类 1、提供App常用的操作；</p>
 *
 * @author hxm
 * @version 1.0 (2015/10/19)
 */
public class DeviceUtils {

    /***
     * 获取手机设备ID
     *
     * @param ctx 上下文
     * @return 设备ID
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceID(Context ctx) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        return ((TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }


    /**
     * 获取手机串号(IMEI)
     *
     * @param ctx 上下文
     *
     * @return 手机串号
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String imei = tm.getDeviceId();
        if (imei == null) {
            return "";
        } else {
            return imei;
        }
    }

    /**
     * 获取用户识别码（IMSI）
     *
     * @param ctx 上下文
     *
     * @return 用户识别码
     */
    public static String getSubscriberId(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String tel = tm.getSubscriberId();
        return TextUtils.isEmpty(tel) ? "" : tel;
    }

    /**
     * 获取手机号码
     *
     * @param ctx 上下文
     *
     * @return 手机号码
     */
    public static String getPhoneNumber(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        return tm.getLine1Number();
    }

    /**
     * 获取运营商
     * 其中46000、46002和46007标识中国移动，46001标识中国联通，46003标识中国电信
     *
     * @param ctx 上下文
     *
     * @return 运营商
     */
    public static String getMNC(Context ctx) {
        String providersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            providersName = telephonyManager.getSimOperator();
            providersName = providersName == null ? "" : providersName;
        }
        return providersName;
    }

    private static String phoneModelCache;

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static synchronized String getPhoneModel() {
        if (phoneModelCache == null) {
            StringBuilder stringBuilder = new StringBuilder(32);
            String manufacturer = Build.MANUFACTURER;
            String brand = Build.BRAND, model = Build.MODEL;
            if (manufacturer != null) {
                //代表公司名称
                manufacturer = manufacturer.toLowerCase();
                stringBuilder.append(manufacturer).append(" ## ");
            }

            if (brand != null) {
                // 一般代表手机品牌，
                brand = brand.toLowerCase();
                if (stringBuilder.indexOf(brand) < 0) {
                    stringBuilder.append(brand).append(" ## ");
                }
            }
            if (model != null) {
                // 一般代表具体手机型号
                model = model.toLowerCase();
                if (stringBuilder.indexOf(model) < 0) {
                    stringBuilder.append(model).append(" ## ");
                }
            }
            phoneModelCache = stringBuilder.toString();
        }
        return phoneModelCache;
    }


    /**
     * 获取MAC地址
     *
     * @param ctx 上下文
     *
     * @return MAC地址
     */
    public static String getWifiMacAddr(Context ctx) {
        String macAddr = "";
        try {
            WifiManager wifi = (WifiManager) ctx
                    .getSystemService(Context.WIFI_SERVICE);
            macAddr = wifi.getConnectionInfo().getMacAddress();
            if (macAddr == null) {
                macAddr = "";
            }
        } catch (Exception e) {
        }
        return macAddr;
    }


    /**
     * 跳转到拨号界面，让用户选择是否拨号
     * by zyp
     *
     * @param activity    上下文
     * @param phoneNumber 拨打的电话号码
     */
    public static void forwardToDial(Activity activity, String phoneNumber) {
        if (activity != null && !TextUtils.isEmpty(phoneNumber)) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
