package com.sharing.library.helper;

import android.content.Context;
import android.os.Build;

import com.orhanobut.hawk.Hawk;
import com.sharing.library.utils.AppUtils;
import com.sharing.library.utils.DeviceUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import static com.tencent.bugly.crashreport.CrashReport.putUserData;

/**
 * <p>BuglyHelper类 Bugly配置类，Bugly产品页http://bugly.qq.com/
 * <p>
 * 1、 手动检查更新（用于设置页面中检测更新按钮的点击事件）
 * public static synchronized void checkUpgrade()
 * <p>
 * 2、获取本地已有升级策略（非实时，可用于界面红点展示）
 * public static synchronized UpgradeInfo getUpgradeInfo()
 * <p>
 * 3、
 * * 检查更新
 * * @param isManual  用户手动点击检查，非用户点击操作请传false
 * * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗和toast]
 * public static synchronized void checkUpgrade(boolean isManual, boolean isSilence)
 * <p>
 * </p>
 *
 * @author hxm
 * @version 1.0 (2016/01/06 14:38)
 */
public class BuglyHelper {
    /**
     * 当前设备id
     */
    public static final String PHONE_DEVICE_ID = "e_phone_device_id";

    /**=================场景标签相关(在Bugly产品页设置http://bugly.qq.com/manage?app=900017042&pid=1&ptag=1005-10004)=================*/

    /**
     * 初始化配置
     *
     * @param appContext 上下文
     */
    /**
     * @param appContext  appContext
     * @param buglyId     buglyId
     * @param versionName BuildConfig.VERSION_NAME
     * @param packageName BuildConfig.APPLICATION_ID
     * @param isShow      !BuildConfig.IS_SHOW_BUSI_CODE
     *
     * @return
     */
    public static boolean initConfig(Context appContext, String buglyId, String versionName, String packageName, boolean isShow) {
        BuglyStrategy strategy = new BuglyStrategy();
        strategy.setAppChannel(AppUtils.getUmengChannel(appContext));                  //设置渠道
        strategy.setAppVersion(versionName);               //App的版本
        strategy.setAppPackageName(packageName);         //App的包名
//        strategy.setAppReportDelay(60000);             //初始化延迟，启动60s后联网同步数据
        //参数1：参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
        Bugly.init(appContext, buglyId, false, strategy);
        putUserData(appContext, "clientId", Hawk.get(PHONE_DEVICE_ID, ""));//客户端唯一编码，由客户端自己生成
        putUserData(appContext, "clientType", "app");//客户端类型，分为：app/wechat/mobile/pc
        putUserData(appContext, "clientOS", "android");//客户端操作系统类别：ios android等
        putUserData(appContext, "clientOSVersion", Build.VERSION.RELEASE);//客户端操作系统的版本,4.4.2
        putUserData(appContext, "clientChannel", AppUtils.getUmengChannel(appContext));//发布渠道，如：小米商店
        putUserData(appContext, "clientHardware", DeviceUtils.getPhoneModel());//客户端硬件型号
        putUserData(appContext, "clientVersionName", AppUtils.getVersionName(appContext));//客户端版本名称，主要是给用户看，例如4.5.2
        putUserData(appContext, "clientVersionCode", String.valueOf(AppUtils.getVersionCode(appContext)));//客户端版本内部编码，递增，用于升级

        setUserId(Hawk.get(PHONE_DEVICE_ID, ""), isShow);
        return true;

    }


    /**
     * 根据不同crash场景设置对应属性
     *
     * @param appContext 上下文
     * @param sceneTag   场景标签id
     *
     * @return
     */
    public static void setPageConfig(Context appContext, int sceneTag, boolean isShow) {
        //设置标签（用于标记场景），该标签为在Bugly产品页上设置的，上报后的Crash会显示该标签
        if (isShow) {
            CrashReport.setUserSceneTag(appContext, sceneTag);
        }
    }

    /**
     * 设置后，Bugly异常日志用户id都将是这个userId
     *
     * @param userId 用户Id
     *
     * @return
     */
    public static void setUserId(String userId, boolean isShow) {
        if (isShow) {
            CrashReport.setUserId(userId);
        }
    }

    /**
     * 上报自己捕捉到的异常，这样app不崩溃，但是依然上报异常信息
     *
     * @param throwable 异常信息
     */
    public static void reportCatchedException(Throwable throwable) {
        try {
            if (throwable != null) {
                CrashReport.postCatchedException(throwable);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

