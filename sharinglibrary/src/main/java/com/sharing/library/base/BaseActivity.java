package com.sharing.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharing.library.R;
import com.sharing.library.views.SlideFinishLayout;

/**
 * <p>基础Activity类 ，统一规范Activity并提供通用方法
 * <br/>1.所有Activity的基类，保证全部是该类的子类
 * <br/>2.管理通用功能比如，Activity队列(完全退出程序使用)
 * </p>
 *
 * @author zmingchun
 * @version 1.0（2015-10-19）
 */
public class BaseActivity extends AppCompatActivity {
    //变量区 start======================================
    protected Context mContext;
    protected Activity activity;
    private boolean hasInvokedSlideFinish = false;
    /** 记录上次点击返回键的时间 */
    private long lastTime;
    protected boolean isDoubleClickClose = false;



    //执行区 start======================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //禁止显示默认的title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = this;
        mContext = this;

        //Activity进入自定义栈
        BaseApplication.getInstance().addActivity(this);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
//            if (System.currentTimeMillis() - lastTime > 1500 && isDoubleClickClose) {
//                CustomToast.showToast(this, "再次点击返回上一层");
//                lastTime = System.currentTimeMillis();
//            } else {
//                onBackEventClick();
//            }
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        handleSlideFinish();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        handleSlideFinish();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        handleSlideFinish();
    }

    private void handleSlideFinish() {
        if (!hasInvokedSlideFinish && enableSlideFinish()) {
            SlideFinishLayout layout = (SlideFinishLayout) LayoutInflater.from(this).inflate(
                    R.layout.activity_container, null);
            layout.attachToActivity(this);
            hasInvokedSlideFinish = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity移除自定义栈
        BaseApplication.getInstance().removeActivity(this);
    }

    //方法区 start========================================

    /**
     * 是否允许滑动删除
     *
     * @return 是否允许
     */
    protected boolean enableSlideFinish() {
        return true;
    }
    //事件区 start========================================
}