package com.lyl.animationtest.one;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityTransition2Binding;

public class TransitionActivity2 extends BaseActivity {

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lyl.animationtest.R.layout.activity_transition2);
        bindData();
        setupWindowAnimations();
        setupLayout();
        setupToolbar();
    }


    private void bindData() {
        ActivityTransition2Binding transition2Binding = DataBindingUtil.setContentView(this, R.layout
                .activity_transition2);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        type = getIntent().getExtras().getInt(EXTRA_TYPE);
        transition2Binding.setTransition2Sample(sample);
    }


    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
            transition = buildEnterTransition();
        } else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
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


    private Transition buildEnterTransition() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        // 修饰动画，定义动画的变化率
        enterTransition.setInterpolator(new AccelerateInterpolator());
        return enterTransition;
    }
}
