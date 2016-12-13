package com.lyl.animationtest.four;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityRevealBinding;

public class RevealActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private static final int DELAY = 100;

    private ActivityRevealBinding binding;

    private Interpolator interpolator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setWinodwAnimations();
        setupLayout();
        setupToolbar();
    }


    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reveal);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setReveal1Sample(sample);
    }


    private void setWinodwAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        setupEnterAnimations();
        setupExitAnimations();
    }


    private void setupLayout() {
        binding.squareGreen.setOnClickListener(this);
        binding.squareRed.setOnClickListener(this);
        binding.squareBlue.setOnClickListener(this);
        binding.squareYellow.setOnTouchListener(this);// 从手触摸的位置，开始发散
    }


    private void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition
                .changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }


            @Override
            public void onTransitionEnd(Transition transition) {
                // 在这里移除侦听器是非常重要的，因为共享元素过渡在退出时再次向后执行。 如果我们不删除侦听器，这个代码将再次被触发。
                transition.removeListener(this);
                hideTarget();
                animateRevealShow(binding.toolbar);
                animateButtonsIn();
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


    private void hideTarget() {
        binding.sharedTarget.setVisibility(View.GONE);
    }


    private void animateRevealShow(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator anima = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        anima.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anima.setInterpolator(new AccelerateInterpolator());
        anima.start();
    }


    private void animateRevealHide(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.start();
    }


    private void animateButtonsIn() {
        for (int i = 0; i < binding.revealRoot.getChildCount(); i++) {
            View view = binding.revealRoot.getChildAt(i);
            view.animate().setStartDelay(100 + i * DELAY).setInterpolator(interpolator).alpha(1).scaleX(1).scaleY(1);
        }
    }


    private void animateButtonsOut() {
        for (int i = 0; i < binding.revealRoot.getChildCount(); i++) {
            View view = binding.revealRoot.getChildAt(i);
            view.animate().setStartDelay(i).setInterpolator(interpolator).alpha(0).scaleX(0).scaleY(0);
        }
    }


    private void setupExitAnimations() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        fade.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(binding.revealRoot);
            }


            @Override
            public void onTransitionEnd(Transition transition) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.square_green:// 从中心散开
                revealGreen();
                break;
            case R.id.square_red:// 红圈 先做一个动画，然后散开
                revealRed();
                break;
            case R.id.square_blue:
                revealBlue();
                break;

        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() == R.id.square_yellow) {
                revealYellow(motionEvent.getRawX(), motionEvent.getRawY());
            }
        }
        return false;
    }


    private void revealGreen() {
        animateRevealColor(binding.revealRoot, R.color.sample_green);
        binding.sampleBody.setText(R.string.reveal_body1);
    }


    private void revealRed() {
        // 保存最开始的状态的参数
        final ViewGroup.LayoutParams saveParams = binding.squareRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition
                .changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }


            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(binding.revealRoot, R.color.sample_red);
                binding.sampleBody.setText(R.string.reveal_body2);
                // 动画结束之后，将 红圈再设回以前的参数
                binding.squareRed.setLayoutParams(saveParams);
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
        // 保存 每个 View 当前的可见状态(Visibility)。
        TransitionManager.beginDelayedTransition(binding.revealRoot, transition);

        // 移动红圈到中央
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
        binding.squareRed.setLayoutParams(layoutParams1);
    }


    private void revealBlue() {
        animateButtonsOut();//  先 让底部四个圆圈消失

        Animator animator = animateRevealColorFromCoordinates(binding.revealRoot, R.color.sample_blue, binding
                .revealRoot.getWidth() / 2, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束之后，让底部四个圆圈进入
                animateButtonsIn();
            }
        });
        binding.sampleBody.setText(R.string.reveal_body3);
    }


    private void revealYellow(float x, float y) {
        animateRevealColorFromCoordinates(binding.revealRoot, R.color.sample_yellow, (int) x, (int) y);
        binding.sampleBody.setText(R.string.reveal_body4);
    }


    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }


    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }
}
