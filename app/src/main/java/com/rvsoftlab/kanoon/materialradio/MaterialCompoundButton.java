package com.rvsoftlab.kanoon.materialradio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.OvershootInterpolator;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;
import com.rvsoftlab.kanoon.R;

/**
 * Created by ravik on 09-02-2018.
 */

public class MaterialCompoundButton extends FrameLayout implements Checkable,OnStateButtonCheckedListener {

    private boolean mBroadcasting;

    protected int     mPadding;

    protected int       mButtonRes;

    protected Drawable mButtonDrawable;

    protected CharSequence mText;

    protected float     mTextSize;

    protected ColorStateList mColorColorStateList;

    protected MaterialStateButton mButtonView;

    protected MaterialStateText  mButtonText;

    protected View mSpaceView;

    protected boolean   mAnimator;

    protected long      mDuration = 300;

    protected boolean   mChecked;

    private OnButtonCheckedChangeListener mOnButtonCheckedChangeListener;

    private OnButtonCheckedChangeListener mOnCheckedChangeWidgetListener;

    public MaterialCompoundButton(Context context) {
        this(context, null);
    }

    public MaterialCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.mcl_compound_button, this, true);
        mButtonView = (MaterialStateButton) findViewById(R.id.mci_compound_button_image);
        mSpaceView = findViewById(R.id.mci_compound_button_padding);
        mButtonText = (MaterialStateText) findViewById(R.id.mci_compound_button_text);
        mButtonView.setOnStateButtonCheckedListener(this);
        applyAttributeSet(context, attrs);
        if(isAnimator()){
            ViewHelper.setScaleX(this, 0.85f);
            ViewHelper.setScaleY(this, 0.85f);
        }
    }

    private void applyAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialCompoundButton);
        mChecked = typedArray.getBoolean(R.styleable.MaterialCompoundButton_mcChecked, mChecked);
        setAnimator(typedArray.getBoolean(R.styleable.MaterialCompoundButton_mcAnimator, isAnimator()));
        mButtonRes = typedArray.getResourceId(R.styleable.MaterialCompoundButton_mcButton,0);
        if(mButtonRes != 0){
            setButtonRes(mButtonRes);
        }
        setButtonPadding(typedArray.getDimensionPixelOffset(R.styleable.MaterialCompoundButton_mcPadding, 0));
        setText(typedArray.getText(R.styleable.MaterialCompoundButton_mcText));
        setTextSize(typedArray.getDimensionPixelSize(R.styleable.MaterialCompoundButton_mcTextSize,15));
        setTextColor(typedArray.getColorStateList(R.styleable.MaterialCompoundButton_mcTextColor));
        typedArray.recycle();
    }

    public void setAnimator(boolean isAnim) {
        this.mAnimator = isAnim;
    }

    public boolean isAnimator() {
        return mAnimator;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public long getDuration() {
        return mDuration;
    }

    @Override
    public void setChecked(boolean checked) {
        mButtonView.setChecked(checked);
        mButtonText.setChecked(checked);
    }

    @Override
    public void onCheckedChanged(boolean isChecked) {
        mButtonText.setChecked(isChecked);
        if (mBroadcasting) {
            return;
        }
        mBroadcasting = true;
        if (mOnButtonCheckedChangeListener != null) {
            mOnButtonCheckedChangeListener.onCheckedChanged(MaterialCompoundButton.this, isChecked);
        }
        if (mOnCheckedChangeWidgetListener != null) {
            mOnCheckedChangeWidgetListener.onCheckedChanged(MaterialCompoundButton.this, isChecked);
        }
        mBroadcasting = false;
        if(isAnimator()){
            if(isChecked()){
                start();
            }else {
                end();
            }
        }
    }

    private void start() {
        if(canAnimate()){
            clearAnimation();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleX", 1.0f));
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleY", 1.0f));
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(getDuration());
        animatorSet.start();
    }

    private void end() {
        if(canAnimate()){
            clearAnimation();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleX", 0.85f));
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleY", 0.85f));
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(getDuration());
        animatorSet.start();
    }

    @Override
    public void onCheckedToggle() {
        mButtonText.toggle();
    }

    @Override
    public boolean isChecked() {
        return mButtonView.isChecked();
    }

    @Override
    public void toggle() {
        mButtonView.toggle();
        mButtonText.toggle();
    }


    public void setButtonPadding(int padding) {
        this.mPadding = padding;
        mSpaceView.getLayoutParams().height = mPadding;
        mSpaceView.requestLayout();
    }

    public void setButtonRes(int mButtonRes) {
        this.mButtonRes = mButtonRes;
        setButtonDrawable(getResources().getDrawable(mButtonRes));
    }

    public void setButtonDrawable(Drawable drawable) {
        this.mButtonDrawable = drawable;
        this.mButtonView.setImageDrawable(mButtonDrawable);
    }

    public void setText(@StringRes int resId) {
        setText(getResources().getString(resId));
    }

    public void setText(CharSequence text) {
        this.mText = text;
        this.mButtonText.setText(mText);
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        this.mButtonText.setTextSize(mTextSize);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mColorColorStateList = colorStateList;
        this.mButtonText.setTextColor(mColorColorStateList);
    }

    public void setTextColor(@ColorInt int color) {
        setTextColor(ColorStateList.valueOf(color));
    }

    public void setOnCheckedChangeListener(OnButtonCheckedChangeListener mOnButtonCheckedChangeListener) {
        this.mOnButtonCheckedChangeListener = mOnButtonCheckedChangeListener;
    }

    public void setOnCheckedChangeWidgetListener(OnButtonCheckedChangeListener mOnCheckedChangeWidgetListener) {
        this.mOnCheckedChangeWidgetListener = mOnCheckedChangeWidgetListener;
        setChecked(mChecked);
    }


    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MaterialCompoundButton.class.getName());
        event.setChecked(mButtonView.isChecked());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MaterialCompoundButton.class.getName());
        info.setCheckable(true);
        info.setChecked(mButtonView.isChecked());
    }
}
