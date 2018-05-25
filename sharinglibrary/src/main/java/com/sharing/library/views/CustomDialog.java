package com.sharing.library.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sharing.library.R;
import com.sharing.library.base.BaseDialog;
import com.sharing.library.utils.DeviceUtils;
import com.sharing.library.utils.GlideUtils;
import com.sharing.library.utils.LocalDisplayUtils;

/**
 * <p>CustomDialog类 1、提供XXX功能；2、提供XXX方法</p>
 *
 * @author hxm<br/>
 * @version 1.0 (2018-5-25 9:25)<br/>
 */
public class CustomDialog extends BaseDialog {
    //变量区 start======================================
    /** 上下文 */
    private Context mContext;
    /** 弹出框类型：0加载类；1确认类 */
    private int mType;
    /** 弹出框是否含有输入框：true有，false无 */
    private boolean mHasInput;
    /** 弹出框的输入框是否密文 true是，false否 */
    private boolean mIsSecretText;
    /** 设置所有输入框的输入类型：-1表示不设置，值如：InputType.TYPE_CLASS_TEXT，来自android.text.InputType */
    private int mInputType = -1;

    /** 按钮监听器 */
    private ButtonCallback mCallback;
    /** 是否可点击它处取消 */
    private boolean mCancelable = false;

    /** 标题内容（主要内容），若为null则表示不显示 */
    private String mTitleText = null;
    /** 标题内容（主要内容），若为null则表示不显示，支持设置字体颜色、链接、大小的等 */
    private SpannableString mSpannableTitleText = null;
    /** 提示内容（辅助说明），若为null，则表示不显示 */
    private String mContent = null;
    /** 提示内容（辅助说明），若为null，则表示不显示，支持设置字体颜色、链接、大小的等 */
    private SpannableString mSpannableContent = null;

    /** 提示图片资源id，仅支持本地图片, 若为-1，则表示不显示 */
    private int confirmImageSourceId = -1;
    /** 提示图片资源连接地址 */
    private String confirmImageSourceUrl = "";

    /** 取消类按钮名称（左侧），若为null则表示不显示 */
    private String mCancelText = null;
    /** 取消类按钮名称（左侧），若为null则表示不显示*，支持设置字体颜色、链接、大小的等 */
    private SpannableString mSpannableCancelText = null;
    /** 确认类按钮名称（右侧），若为null则表示不显示 */
    private String mConfirmText = null;
    /** 确认类按钮名称（右侧），若为null则表示不显示，支持设置字体颜色、链接、大小的等 */
    private SpannableString mSpannableConfirmText = null;

    /** 第一个输入框hint内容 */
    private String mHintText = null;
    /** 第一个输入框默认内容 */
    private String mInputDefaultText = null;
    /** 第一个输入框view */
    private EditText mEditText = null;

    /** 第二个输入框view */
    private EditText mSecondEditText = null;
    /** 第二个输入框hint内容 */
    private String mSecondHintText = null;
    /** 第二个输入框默认内容 */
    private String mSecondInputDefaultText = null;

    /** 设置两个输入框的分割符 */
    private String mConfirmViewLineText = null;

    /** 确认类按钮view */
    private Button mConfirmBtn = null;
    /** 确认类按钮是否需要根据输入框的内容来设置可用性：true是，false否 */
    private boolean mControlEnable = false;

    /** 单位（右侧），若为null则表示不显示 */
    private String mConfirmTextView = null;

    /** 弹出框含有的输入框个数：目前最多为2 */
    private int mMultiInputSize = 1;

    /** 输入框值变化监听器，若为null，则默认采用默认处理 */
    private DialogTextWatcher mDialogTextWatcher = null;

    /** 第一个输入框长度限制，若为null则不限制 */
    private InputFilter[] mInputFilterOne = null;
    /** 第二个输入框长度限制，若为null则不限制 */
    private InputFilter[] mInputFilterTwo = null;

    private View rootView;

    // 执行区
    //-----------------------------------------------

    /**
     * 初始化加载类弹出框(默认提示内容为：加载中...)
     *
     * @param context 上下文
     *
     * @return
     */
    public static CustomDialog newLoadingInstance(Context context) {
        return new CustomDialog(context);
    }

    /**
     * 初始化确认类弹出框
     *
     * @param context 上下文
     *
     * @return
     */
    public static CustomDialog newConfirmInstance(Context context) {
        return new CustomDialog(context, null);
    }

    /**
     * 初始化确认类弹出框（含单个输入框）
     *
     * @param context      上下文
     * @param isSecretText 输入框是否密文 true是，false否
     *
     * @return
     */
    public static CustomDialog newConfirmInstance(Context context, boolean isSecretText) {
        return new CustomDialog(context, isSecretText);
    }

    /**
     * 初始化确认类弹出框（含两个输入框）
     *
     * @param context 上下文
     *
     * @return
     */
    public static CustomDialog newMultiInputInstance(Context context) {
        return new CustomDialog(context, 2);
    }

    /**
     * 初始构造方法-加载类弹出框
     *
     * @param context 上下文
     *                <br/>加载类弹出框-默认提示内容为：加载中...)
     */
    private CustomDialog(Context context) {
        //设置dialog的显示风格
        super(context, R.style.loading_dialog);
        this.mContext = context;
        this.mType = 0;
    }

    /**
     * 初始构造方法-确认类弹出框
     *
     * @param context 上下文
     * @param content 显示的内容（主要内容）
     */
    private CustomDialog(Context context, String content) {
        //设置dialog的显示风格
        super(context, R.style.confirm_dialog);
        this.mContext = context;
        this.mContent = content;
        this.mType = 1;
        this.mHasInput = false;
    }

    /**
     * 初始构造方法-确认类弹出框（含输入框）
     *
     * @param context      上下文
     * @param isSecretText 输入框是否密文 true是，false否
     */
    private CustomDialog(Context context, boolean isSecretText) {
        //设置dialog的显示风格
        super(context, R.style.confirm_dialog);
        this.mContext = context;
        this.mType = 1;
        this.mHasInput = true;
        this.mIsSecretText = isSecretText;
        this.mMultiInputSize = 1;
    }

    /**
     * 初始构造方法-确认类弹出框（含两个输入框）
     *
     * @param context   上下文
     * @param inputSize 含输入框个数，目前固定为2
     */
    private CustomDialog(Context context, int inputSize) {
        //设置dialog的显示风格
        super(context, R.style.confirm_dialog);
        this.mContext = context;
        this.mType = 1;
        this.mHasInput = true;
        this.mIsSecretText = false;
        this.mMultiInputSize = inputSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newInstance();
    }

    /**
     * 初始化
     *
     * @return
     */
    private CustomDialog newInstance() {
        Logger.e("CustomDialog newInstance");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (mType == 0) {
            initLoadingView(inflater, mContext.getString(R.string.common_loading_data));
        } else if (mType == 1) {
            initConfirmView(inflater);
        }
        initConfig();
        return this;
    }

    // 方法区
    //-----------------------------------------------

    /**
     * 加载配置
     */
    private void initConfig() {
        //如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，
        //则进行cancel，状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mCancelable);
        //若setCancelable(false)， 则需要监听返回键手动处理dialog的消失(确认类的)


        if (mType == 1) {
            String deviceType = DeviceUtils.getPhoneModel();
            if (TextUtils.isEmpty(deviceType) || !deviceType.equals("GT-I9100")) {
                //设置该值，会导致在某些机型（I9100 4.4.4）下，宽度撑满屏幕
                this.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg_t99);
            }
        }
    }

    /**
     * 加载确认类自定义布局
     *
     * @param inflater 布局处理器
     */
    private void initConfirmView(LayoutInflater inflater) {
        //加载自定义布局
        if (mHasInput) {
            rootView = inflater.inflate(R.layout.custom_dialog_confirm_input, null);
        } else {
            rootView = inflater.inflate(R.layout.custom_dialog_confirm, null);
        }
        //根视图
        LinearLayout confirmDialogView = (LinearLayout) rootView.findViewById(R.id.confirm_dialog_view);
        //宽度占屏幕宽80%
        int mWidth = (LocalDisplayUtils.screenWidthPixels * 8 / 10);
        //设置布局
        confirmDialogView.setLayoutParams(new LinearLayout.LayoutParams(
                mWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        //处理生成自定义布局的具体布局
        createConfirmSubView(rootView);
        //设置布局
        this.setContentView(rootView, new LinearLayout.LayoutParams(
                mWidth, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 处理生成确认类自定义布局的具体布局
     *
     * @param rootView 自定义布局根视图
     */
    private void createConfirmSubView(View rootView) {
        //标题
        TextView confirmTip = (TextView) rootView.findViewById(R.id.confirm_tip);
        if (!TextUtils.isEmpty(mTitleText) || null != mSpannableTitleText) {
            confirmTip.setVisibility(View.VISIBLE);
            confirmTip.setText(!TextUtils.isEmpty(mTitleText) ? mTitleText : mSpannableTitleText);
        } else {
            confirmTip.setVisibility(View.GONE);
        }
        //内容
        TextView confirmTextView = (TextView) rootView.findViewById(R.id.confirm_textView);
        if (!TextUtils.isEmpty(mContent)) {
            confirmTextView.setText(mContent);
            confirmTextView.setVisibility(View.VISIBLE);
        } else {
            if (null != mSpannableContent) {
                confirmTextView.setText(mSpannableContent);
                confirmTextView.setVisibility(View.VISIBLE);
            } else {
                confirmTextView.setVisibility(View.GONE);
            }
        }

        //图片
        ImageView confirmImageView = (ImageView) rootView.findViewById(R.id.confirm_imageView);
        if (confirmImageSourceId != -1) {
            confirmImageView.setImageResource(confirmImageSourceId);
            confirmImageView.setVisibility(View.VISIBLE);
        } else {
            confirmImageView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(confirmImageSourceUrl)) {
            GlideUtils.show(mContext, confirmImageView, confirmImageSourceUrl);
            confirmImageView.setVisibility(View.VISIBLE);
        } else {
            confirmImageView.setVisibility(View.GONE);
        }


        //取消按钮
        Button negativeButton = (Button) rootView.findViewById(R.id.negative_button);
        if (!TextUtils.isEmpty(mCancelText) || null != mSpannableCancelText) {
            negativeButton.setText(!TextUtils.isEmpty(mCancelText) ? mCancelText : mSpannableCancelText);
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mCallback) {
                        mCallback.onNegative(CustomDialog.this);
                    } else {
                        CustomDialog.this.dismiss();
                    }
                }
            });
        } else {
            rootView.findViewById(R.id.v_line).setVisibility(View.GONE);
            negativeButton.setVisibility(View.GONE);
        }
        //确认按钮
        Button positiveButton = (Button) rootView.findViewById(R.id.positive_button);
        if (!TextUtils.isEmpty(mConfirmText) || null != mSpannableConfirmText) {
            //放弃加粗
//            if (!TextUtils.isEmpty(mCancelText) || null != mSpannableCancelText){
//                positiveButton.setTypeface(positiveButton.getTypeface(), Typeface.BOLD);
//            }else{
//                positiveButton.setTypeface(positiveButton.getTypeface(), Typeface.NORMAL);
//            }
            positiveButton.setText(!TextUtils.isEmpty(mConfirmText) ? mConfirmText : mSpannableConfirmText);
            positiveButton.setVisibility(View.VISIBLE);
            if (mControlEnable) {
                positiveButton.setEnabled(false);
            } else {
                positiveButton.setEnabled(true);
            }
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mCallback) {
                        if (null != getEditText()) {
                            if (null != getSecondEditText()) {
                                mCallback.onPositive(CustomDialog.this, getEditText(), getSecondEditText());
                            } else {
                                mCallback.onPositive(CustomDialog.this, getEditText());
                            }
                        } else {
                            mCallback.onPositive(CustomDialog.this);
                        }
                    } else {
                        CustomDialog.this.dismiss();
                    }
                }
            });
        } else {
            rootView.findViewById(R.id.v_line).setVisibility(View.GONE);
            positiveButton.setVisibility(View.GONE);
        }

        this.setConfirmBtn(positiveButton);

        //输入框的处理
        creatConfirmSubInputView(rootView);
    }

    /**
     * 处理生成确认类自定义布局的输入框布局
     * <p>由于输入框的监听会用到positiveButton，因此必须放置于按钮的初始化后面</p>
     *
     * @param rootView 自定义布局根视图
     */
    private void creatConfirmSubInputView(View rootView) {
        if (mHasInput) {
            //第一个输入框
            EditText confirmEditText = (EditText) rootView.findViewById(R.id.confirm_edit_text);
            confirmEditText.setVisibility(View.VISIBLE);
            confirmEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (null == mDialogTextWatcher) {
                        if (checkInput()) {
                            getConfirmBtn().setEnabled(true);
                        } else {
                            if (mControlEnable) {
                                getConfirmBtn().setEnabled(false);
                            }
                        }
                    } else {
                        //监听输入框变化
                        mDialogTextWatcher.onTextChanged(getConfirmBtn(), getEditText(), getSecondEditText());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            //设置输入框输入类型
            if (mInputType != -1) {
                confirmEditText.setInputType(mInputType);
            } else if (mMultiInputSize > 1) {//含有多个输入框，则默认输入类型为数字
                confirmEditText.setInputType(InputType.TYPE_CLASS_NUMBER); //调用数字键盘
            }

            if (mIsSecretText) {//密文显示
                confirmEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {//明文显示
                confirmEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            if (!TextUtils.isEmpty(mHintText)) {
                confirmEditText.setHint(mHintText);
            }
            if (!TextUtils.isEmpty(mInputDefaultText)) {
                confirmEditText.setText(mInputDefaultText);
            }
            if (null != mInputFilterOne) {
                confirmEditText.setFilters(mInputFilterOne);
            }
            this.setEditText(confirmEditText);

            //含有多个输入框
            if (mMultiInputSize > 1) {
                if (!TextUtils.isEmpty(mConfirmViewLineText)) {
                    TextView confirmTextViewLine = (TextView) rootView.findViewById(R.id.confirm_tv_line);
                    confirmTextViewLine.setText(mConfirmViewLineText);
                }
                //两个输入框间的斜线
                rootView.findViewById(R.id.confirm_tv_line).setVisibility(View.VISIBLE);
                //第二个输入框
                EditText secondConfirmEdittext = (EditText) rootView.findViewById(R.id.second_confirm_edit_text);
                secondConfirmEdittext.setVisibility(View.VISIBLE);
                secondConfirmEdittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (null == mDialogTextWatcher) {
                            if (checkInput()) {
                                getConfirmBtn().setEnabled(true);
                            } else {
                                if (mControlEnable) {
                                    getConfirmBtn().setEnabled(false);
                                }
                            }
                        } else {
                            mDialogTextWatcher.onTextChanged(getConfirmBtn(), getEditText(), getSecondEditText());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                //设置输入框输入类型
                if (mInputType != -1) {
                    secondConfirmEdittext.setInputType(mInputType);
                } else if (mMultiInputSize > 1) {//含有多个输入框，则默认输入类型为数字
                    secondConfirmEdittext.setInputType(InputType.TYPE_CLASS_NUMBER); //调用数字键盘
                }

                if (mIsSecretText) {//密文显示
                    secondConfirmEdittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {//明文显示
                    secondConfirmEdittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if (!TextUtils.isEmpty(mSecondHintText)) {
                    secondConfirmEdittext.setHint(mSecondHintText);
                }
                if (!TextUtils.isEmpty(mSecondInputDefaultText)) {
                    secondConfirmEdittext.setText(mSecondInputDefaultText);
                }
                if (null != mInputFilterTwo) {
                    secondConfirmEdittext.setFilters(mInputFilterTwo);
                }
                this.setSecondEditText(secondConfirmEdittext);
            }

            //单位
            if (!TextUtils.isEmpty(mConfirmTextView)) {
                TextView confirmTextView = (TextView) rootView.findViewById(R.id.confirm_text_view);
                confirmTextView.setVisibility(View.VISIBLE);
                confirmTextView.setText(mConfirmTextView);
            }
        }
    }

    /**
     * 加载加载类自定义布局
     *
     * @param inflater 布局处理器
     * @param dContent 默认提示标题内容
     *
     * @return
     */
    private void initLoadingView(LayoutInflater inflater, String dContent) {
        //加载自定义布局
        View rootView = inflater.inflate(R.layout.custom_dialog_loading, null);
        //根视图
        LinearLayout loagdingDialogView = (LinearLayout) rootView.findViewById(R.id.loading_dialog_view);
        //宽度占屏幕宽80%
        int mWidth = (LocalDisplayUtils.screenWidthPixels * 6 / 10);
        //设置布局
        loagdingDialogView.setLayoutParams(new LinearLayout.LayoutParams(
                mWidth, LinearLayout.LayoutParams.WRAP_CONTENT));


        //提示内容
        TextView loadingTip = (TextView) rootView.findViewById(R.id.loading_tip);
        loadingTip.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mContent)) {
            loadingTip.setText(mContent);
        } else {
            if (null != mSpannableContent) {
                loadingTip.setText(mSpannableContent);
            } else {
                loadingTip.setText(dContent);
            }
        }
        //设置布局
        this.setContentView(rootView, new LinearLayout.LayoutParams(
                mWidth, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setConfirmImageSourceUrl(String confirmImageSourceUrl) {
        this.confirmImageSourceUrl = confirmImageSourceUrl;
    }

    /**
     * 设置确认类按钮对象
     *
     * @param confirmBtn 确认类按钮对象
     */
    private void setConfirmBtn(Button confirmBtn) {
        this.mConfirmBtn = confirmBtn;
    }

    /**
     * 获取确认类按钮对象
     *
     * @return
     */
    private Button getConfirmBtn() {
        return mConfirmBtn;
    }

    /**
     * 设置第一个输入框对象
     *
     * @param editText 输入框对象
     */
    private void setEditText(EditText editText) {
        this.mEditText = editText;
    }

    /**
     * 设置第二个输入框对象
     *
     * @param secondEditText 输入框对象
     */
    private void setSecondEditText(EditText secondEditText) {
        this.mSecondEditText = secondEditText;
    }

    /**
     * 获取第一个输入框对象
     *
     * @return
     */
    private EditText getEditText() {
        return mEditText;
    }

    /**
     * 设置第二个输入框对象
     */
    private EditText getSecondEditText() {
        return mSecondEditText;
    }

    /**
     * 检查输入内容是否满足启用按钮（只需要有内容即可）
     *
     * @return true满足，false不满足
     */
    private boolean checkInput() {
        boolean isOK;
        EditText firstEditText = getEditText();
        EditText secondEditText = getSecondEditText();
        String firstInputText = "";
        String secondInputText = "";
        if (null != firstEditText) {
            firstInputText = firstEditText.getText().toString().trim();
        }
        if (null != secondEditText) {
            secondInputText = secondEditText.getText().toString().trim();
        }

        if (mMultiInputSize > 1) {
            if (!TextUtils.isEmpty(firstInputText) && !TextUtils.isEmpty(secondInputText)) {
                isOK = true;
            } else {
                isOK = false;
            }
        } else {
            if (!TextUtils.isEmpty(firstInputText) || !TextUtils.isEmpty(secondInputText)) {
                isOK = true;
            } else {
                isOK = false;
            }
        }
        return isOK;
    }

    //可配置属性方法
    //==============================================================================================

    /**
     * 设置Dialog是否可点击取消（点击dialog外空白处或者按返回键取消）
     *
     * @param cancelable If true, the dialog is cancelable.  The default
     *                   is true.
     */
    public CustomDialog setIsCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置按钮监听回调方法
     *
     * @param bCallback 回调方法
     */
    public CustomDialog setBtnCallback(ButtonCallback bCallback) {
        this.mCallback = bCallback;
        return this;
    }

    /**
     * 设置标题内容，若为null则表示不显示
     *
     * @param title 标题内容
     */
    public CustomDialog setTitle(String title) {
        this.mTitleText = title;
        return this;
    }


    /**
     * 设置标题内容，若为null则表示不显示
     *
     * @param spannableContent 标题内容(与mTitleText互斥)，可设置字体颜色、链接、大小等
     *
     * @return
     */
    public CustomDialog setSpannableTitle(SpannableString spannableContent) {
        this.mSpannableTitleText = spannableContent;
        return this;
    }

    /**
     * 设置提示内容，若为null则显示默认内容
     *
     * @param content 提示内容
     *
     * @return
     */
    public CustomDialog setContent(String content) {
        this.mContent = content;
        return this;
    }

    /**
     * 设置提示内容，若为null则显示默认内容
     *
     * @param spannableContent 提示内容(与Content互斥)，可设置字体颜色、链接、大小等
     *
     * @return
     */
    public CustomDialog setSpannableContent(SpannableString spannableContent) {
        this.mSpannableContent = spannableContent;
        return this;
    }

    /**
     * 设置取消类按钮名称（左侧），若为null则表示不显示
     *
     * @param cancelStr 取消类按钮名称
     */
    public CustomDialog setCancelBtnText(String cancelStr) {
        this.mCancelText = cancelStr;
        return this;
    }

    /**
     * 设置取消类按钮名称（左侧），若为null则表示不显示
     *
     * @param spannableContent 取消类按钮名称(与mCancelText互斥)，可设置字体颜色、链接、大小等
     *
     * @return
     */
    public CustomDialog setSpannableCancelBtnText(SpannableString spannableContent) {
        this.mSpannableCancelText = spannableContent;
        return this;
    }

    /**
     * 确认类按钮名称（右侧），若为null则表示不显示
     *
     * @param confirmStr 确认类按钮名称
     */
    public CustomDialog setConfirmBtnText(String confirmStr) {
        this.mConfirmText = confirmStr;
        return this;
    }

    /**
     * 确认类按钮名称（右侧），若为null则表示不显示
     *
     * @param spannableContent 确认类按钮名称(与mConfirmText互斥)，可设置字体颜色、链接、大小等
     *
     * @return
     */
    public CustomDialog setSpannableConfirmBtnText(SpannableString spannableContent) {
        this.mSpannableConfirmText = spannableContent;
        return this;
    }

    /**
     * 若有输入框，则设置输入框是否密文显示
     *
     * @param mIsSecretText 是否密文显示 true是，false否
     *
     * @return
     */
    public CustomDialog setIsCiphertext(boolean mIsSecretText) {
        this.mIsSecretText = mIsSecretText;
        return this;
    }

    /**
     * (第一个输入框)设置输入框hint提示内容（若有输入框，才生效）
     *
     * @param hintText 提示内容
     *
     * @return
     */
    public CustomDialog setHintText(String hintText) {
        this.mHintText = hintText;
        return this;
    }

    /**
     * (第一个输入框)设置输入框默认内容（若有输入框，才生效）
     *
     * @param inputDefaultText 默认内容
     *
     * @return
     */
    public CustomDialog setInputDefaultText(String inputDefaultText) {
        this.mInputDefaultText = inputDefaultText;
        return this;
    }

    /**
     * 设置Dialog的确认类按钮是否需要根据输入框的内容来设置可用性：true是，false否
     *
     * @param controlEnable true是（则初始化时，该按钮不可用），false否(默认值，则初始化时该按钮可用)
     */
    public CustomDialog setControlEnable(boolean controlEnable) {
        this.mControlEnable = controlEnable;
        return this;
    }

    /**
     * (第二个输入框)设置输入框hint提示内容（若有输入框，才生效）
     *
     * @param hintText 提示内容
     *
     * @return
     */
    public CustomDialog setSecondHintText(String hintText) {
        this.mSecondHintText = hintText;
        return this;
    }

    /**
     * (第二个输入框)设置输入框默认内容（若有输入框，才生效）
     *
     * @param inputDefaultText 默认内容
     *
     * @return
     */
    public CustomDialog setSecondInputDefaultText(String inputDefaultText) {
        this.mSecondInputDefaultText = inputDefaultText;
        return this;
    }

    /**
     * 设置单位（右侧），若为null则表示不显示
     *
     * @param unitText 单位名称
     *
     * @return
     */
    public CustomDialog setConfirmTextView(String unitText) {
        this.mConfirmTextView = unitText;
        return this;
    }

    /**
     * 设置所有输入框的输入类型，若为-1则表示不设置(值如：InputType.TYPE_CLASS_TEXT，来自android.text.InputType)
     *
     * @param inputType 输入类型
     *
     * @return
     */
    public CustomDialog setInputType(int inputType) {
        this.mInputType = inputType;
        return this;
    }

    /**
     * 设置图片资源id
     *
     * @param confirmImageSourceId 图片资源id
     */
    public CustomDialog setConfirmImageSourceId(int confirmImageSourceId) {
        this.confirmImageSourceId = confirmImageSourceId;
        return this;
    }

    /**
     * 设置两个输入框的分割符(仅当有两个输入框时才生效)
     *
     * @param confirmViewLineText 分隔符内容
     */
    public CustomDialog setConfirmViewLineText(String confirmViewLineText) {
        this.mConfirmViewLineText = confirmViewLineText;
        return this;
    }

    /**
     * 设置输入框，内容变化监听器（一般用于控制确认按钮是否可用）
     *
     * @param textWatcher 监听器，若为null，则表示默认处理
     */
    public CustomDialog setDialogTextWatcher(DialogTextWatcher textWatcher) {
        this.mDialogTextWatcher = textWatcher;
        return this;
    }

    /**
     * 设置第一个输入框的长度限制过滤器，若为null，则无限制
     *
     * @param inputFilterOne 过滤器
     */
    public CustomDialog setInputFilterOne(InputFilter[] inputFilterOne) {
        this.mInputFilterOne = inputFilterOne;
        return this;
    }

    /**
     * 设置第二个输入框的长度限制过滤器，若为null，则无限制
     *
     * @param inputFilterTwo 过滤器
     */
    public CustomDialog setInputFilterTwo(InputFilter[] inputFilterTwo) {
        this.mInputFilterTwo = inputFilterTwo;
        return this;
    }

    /**
     * 重置内容
     *
     * @param value
     */
    public void refreshContent(String value) {
        if (rootView != null) {
            TextView confirmTextView = (TextView) rootView.findViewById(R.id.confirm_textView);
            if (confirmTextView != null) {
                confirmTextView.setText(value);
            }
        }

    }

//自定义方法类
    //==============================================================================================

    /**
     * confirmDialog 按钮回调方法类
     */
    public abstract static class ButtonCallback {
        /**
         * 取消按钮的事件
         *
         * @param dialog 弹窗本身
         */
        public void onNegative(CustomDialog dialog) {
            //默认关闭
            if (null != dialog) {
                dialog.dismiss();
            }
        }

        /**
         * 确认按钮的事件
         *
         * @param dialog 弹窗本身
         */
        public void onPositive(CustomDialog dialog) {
            //默认关闭
            if (null != dialog) {
                dialog.dismiss();
            }
        }

        /**
         * 确认按钮的事件
         *
         * @param dialog   弹窗本身
         * @param editText 文本输入框
         */
        public void onPositive(CustomDialog dialog, EditText editText) {
            this.onPositive(dialog);
        }

        /**
         * 确认按钮的事件
         *
         * @param dialog         弹窗本身
         * @param editText       第一个文本输入框
         * @param secondEditText 第二个文本输入框
         */
        public void onPositive(CustomDialog dialog, EditText editText, EditText secondEditText) {
            this.onPositive(dialog);
        }

        /** 按钮回调方法默认构造方法 */
        public ButtonCallback() {
        }
    }

    public interface DialogTextWatcher {
        /**
         * 输入框输入值变化
         *
         * @param confirmBtn     弹出框的确认按钮（需要控制是否可用）
         * @param firstEditText  触发变化的输入框对象1
         * @param secondEditText 触发变化的输入框对象2
         */
        void onTextChanged(Button confirmBtn, EditText firstEditText, EditText secondEditText);
    }

}
