package com.rvsoftlab.kanoon.materialradio;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Checkable;
import android.widget.ImageButton;

/**
 * Created by ravik on 09-02-2018.
 */

public class MaterialStateButton extends ImageButton implements Checkable {
    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    private static final boolean DEBUG = false;

    private static final String LOG_TAG = "MaterialStateButton:";


    private boolean mChecked;

    private OnStateButtonCheckedListener mOnStateButtonCheckedListener;

    public MaterialStateButton(Context context) {
        super(context);
    }

    public MaterialStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        if(DEBUG){
            Log.d(LOG_TAG,"setChecked :"+checked);
        }
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            if(mOnStateButtonCheckedListener != null){
                mOnStateButtonCheckedListener.onCheckedChanged(mChecked);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
        if(mOnStateButtonCheckedListener != null){
            mOnStateButtonCheckedListener.onCheckedToggle();
        }
    }

    @Override
    public boolean performClick() {
        if(DEBUG){
            Log.d(LOG_TAG,"performClick");
        }
        if(!isChecked()){
            toggle();
        }
        return super.performClick();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setOnStateButtonCheckedListener(OnStateButtonCheckedListener listener) {
        this.mOnStateButtonCheckedListener = listener;
    }
}
