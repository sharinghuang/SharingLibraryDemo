package com.sharing.library.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * <p>BaseDialog类 1、提供XXX功能；2、提供XXX方法</p>
 *
 * @author hxm<br/>
 * @version 1.0 (2018-5-25 9:23)<br/>
 */
public class BaseDialog extends Dialog {
    /** 上下文 */
    private Context bContext;

    /**
     * 默认构造器
     *
     * @param context 上下文
     */
    public BaseDialog(Context context) {
        super(context);
        this.bContext = context;
    }

    /**
     * 构造器
     *
     * @param context    上下文
     * @param themeResId 主题资源文件id
     */
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.bContext = context;
    }

    /**
     * 构造器
     *
     * @param context        上下文
     * @param cancelable     是否可取消
     * @param cancelListener 取消监听器
     */
    protected BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.bContext = context;
    }

    /**
     * 重写show方法，事先判断activity是否finish，避免IllegalArgumentException:View not attached to window
     * manager（窗口泄露-leaded window）
     */
    @Override
    public void show() {
        try {
            if (bContext instanceof Activity) {
                if (!((Activity) bContext).isFinishing()) {
                    super.show();
                }
            } else {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //noting to do
        }
    }

    /**
     * 重写dismiss方法，事先判断activity是否finish，避免IllegalArgumentException:View not attached to window
     * manager（窗口泄露-leaded window）
     */
    @Override
    public void dismiss() {
        try {
            if (bContext instanceof Activity) {
                if (!((Activity) bContext).isFinishing()) {
                    super.dismiss();
                }
            } else {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //noting to do
        }
    }
}
