package com.rvsoftlab.kanoon.helper;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.rvsoftlab.kanoon.R;

/**
 * Created by RVishwakarma on 2/1/2018.
 */

public class ProgressDialog{
    private Dialog dialog;
    private LottieAnimationView lottie;

    public ProgressDialog(@NonNull Context context) {
        dialog = new Dialog(context,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    public void show(){
        lottie = dialog.findViewById(R.id.lottie_loading);
        lottie.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lottie.loop(true);
        lottie.playAnimation();
        dialog.show();
    }

    public void dismiss(){
        if (dialog.isShowing()){
            if (lottie!=null && lottie.isAnimating())
                lottie.cancelAnimation();
            dialog.dismiss();
        }
    }
}
