package com.lyl.animationtest.one;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityTransition3Binding;

public class TransitionActivity3 extends BaseActivity {

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition3);
        bindData();
        setupWindowAnimations();
        setupLayout();
    }


    private void bindData() {
        ActivityTransition3Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition3);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        type = getIntent().getExtras().getInt(EXTRA_TYPE);
        binding.setTransition3Sample(sample);
    }


    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
            transition = buildEnterTransition();
        } else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }

        getWindow().setEnterTransition(transition);
    }


    private void setupLayout() {
        findViewById(R.id.exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }


    private Visibility buildEnterTransition() {
        Slide slide = new Slide();
        slide.setDuration(800);
        slide.setSlideEdge(Gravity.RIGHT);
        slide.setInterpolator(new BounceInterpolator());
        return slide;
    }

}
