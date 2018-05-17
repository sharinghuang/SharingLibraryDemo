package com.sharing.library.helper;

import android.content.Context;

import com.sharing.library.utils.AppUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * <p>UmengHelper ,友盟组件帮助类
 * <p>
 *
 * @author hxm
 * @version 1.0 (2016/01/06 14:38)
 */
public class UmengHelper {

    /**=================场景标签相关(在Bugly产品页设置http://bugly.qq.com/manage?app=900017042&pid=1&ptag=1005-10004)=================*/

    /**
     * 初始化配置
     *
     * @param appContext 上下文
     */
    public static boolean initConfig(Context appContext, String umengId) {
        MobclickAgent.UMAnalyticsConfig umAnalyticsConfig =
                new MobclickAgent.UMAnalyticsConfig(appContext, umengId, AppUtils.getUmengChannel(appContext));
        MobclickAgent.startWithConfigure(umAnalyticsConfig);
        return true;

    }

    /**
     * 使用计数事件需要在后台添加事件时选择“计数事件”。
     * 统计eventId的发生次数
     *
     * @param context context
     * @param eventId 为当前统计的事件ID。
     */
    public static void onEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }


    /**
     * 统计点击行为各属性被触发的次数
     *
     * @param context context
     * @param eventId 为当前统计的事件ID。
     * @param map     为当前事件的属性和取值（Key-Value键值对）
     *                例子：统计电商应用中“购买”事件发生的次数，以及购买的商品类型及数量，那么在购买的函数里调用：
     *                HashMap<String,String> map = new HashMap<String,String>();
     *                map.put("type","book");
     *                map.put("quantity","3");
     *                MobclickAgent.onEvent(mContext, "purchase", map);
     */
    public static void onEvent(Context context, String eventId, HashMap map) {
        MobclickAgent.onEvent(context, eventId, map);
    }

    /**
     * 统计点击行为各属性被触发的次数
     *
     * @param context context
     * @param eventId 为当前统计的事件ID。
     * @param value   事件对应的值
     */
    public static void onEvent(Context context, String eventId, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(eventId, value);
        MobclickAgent.onEvent(context, eventId, map);
    }


}

