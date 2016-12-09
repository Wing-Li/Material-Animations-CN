package com.lyl.animationtest.three;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityAnimations2Binding;

public class AnimationsActivity2 extends BaseActivity implements View.OnClickListener {

    private ActivityAnimations2Binding binding;

    private Scene scene0;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupLayout();
        setupToolbar();
        setWindowAnimations();
    }


    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animations2);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setAnimationsSample(sample);
    }


    private void setupLayout() {
        scene0 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.activity_animations_scene0, this);
        scene1 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.activity_animations_scene1, this);
        scene2 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.activity_animations_scene2, this);
        scene3 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.activity_animations_scene3, this);
        scene4 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.activity_animations_scene4, this);
        binding.sample3Button1.setOnClickListener(this);
        binding.sample3Button2.setOnClickListener(this);
        binding.sample3Button3.setOnClickListener(this);
        binding.sample3Button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sample3_button1:
                TransitionManager.go(scene1, new ChangeBounds());
                break;
            case R.id.sample3_button2:
                TransitionManager.go(scene2, TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds));
                break;
            case R.id.sample3_button3:
                TransitionManager.go(scene3,TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential));
                break;
            case R.id.sample3_button4:
                TransitionManager.go(scene3,TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators));
                break;
        }
    }

    private void setWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        slide.setSlideEdge(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);

        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getEnterTransition().removeListener(this);
                TransitionManager.go(scene0);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }


}
