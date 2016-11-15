package sing.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘相关
 */
public class KeyboardUtil {

    // 控制键盘显示 true为隐藏，false为显示
    public static void hideInputMethod(Activity activity, boolean isHiden) {
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isHiden) {
            if (mInputMethodManager.isActive()) {
                final View v = activity.getWindow().peekDecorView();
                if (v != null && v.getWindowToken() != null) {
                    mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        } else {
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打卡软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 打开键盘
     *
     * @param context the context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭键盘事件.
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}