package com.lyl.animationtest.three;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityAnimations1Binding;

public class AnimationsActivity1 extends BaseActivity {

    private ActivityAnimations1Binding binding;
    private Sample sample;

    private int savedWidth;
    private boolean sizeChanged;

    private boolean positionChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setWindowAnimations();
        setupLayout();
        setupToolbar();
    }


    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animations1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setAnimationsSample(sample);
    }


    private void setWindowAnimations() {
        getWindow().setEnterTransition(new Fade());
    }


    private void setupLayout() {
        binding.sample3Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout();
            }
        });

        binding.sample3Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosition();
            }
        });

        binding.sample3Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationsActivity1.this, AnimationsActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                transitionTo(i);
            }
        });
    }


    private void changeLayout() {
        // 此方法会为每个 View 保存其当前的可见状态(Visibility)。
        TransitionManager.beginDelayedTransition(binding.sample3Root);

        ViewGroup.LayoutParams layoutParams = binding.squareGreen.getLayoutParams();
        if (sizeChanged) {
            layoutParams.width = savedWidth;
        } else {
            savedWidth = layoutParams.width;
            layoutParams.width = 200;
        }
        sizeChanged = !sizeChanged;
        binding.squareGreen.setLayoutParams(layoutParams);
    }


    private void changePosition() {
        TransitionManager.beginDelayedTransition(binding.sample3Root);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.squareGreen.getLayoutParams();
        if (positionChanged) {
            params.gravity = Gravity.CENTER;
        } else {
            params.gravity = Gravity.LEFT;
        }
        positionChanged = !positionChanged;
        binding.squareGreen.setLayoutParams(params);
    }
}
