package com.abfresh.in.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    private KeyboardHideListener listener;

    public interface KeyboardHideListener { void onKeyboardHide(); }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setKeyboardListener(KeyboardHideListener listener){

        this.listener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // User has pressed Back key. So hide the keyboard
//            InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            mgr.hideSoftInputFromWindow(this.getWindowToken(), 0);

            listener.onKeyboardHide();
            // TODO: Hide your view as you do it in your activity
        }
        return false;
    }
}