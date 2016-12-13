package com.lyl.animationtest.one;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseActivity;
import com.lyl.animationtest.databinding.ActivityTransition1Binding;

public class TransitionActivity1 extends BaseActivity {

    private Sample sample;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition1);
        bindData();
        setupWindowAnimations();
        setupLayout();
        setupToolbar();
    }


    private void bindData() {
        ActivityTransition1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setTransition1Sample(sample);
    }


    private void setupWindowAnimations() {
        // 首次进入显示的动画
        Visibility visibility = buildEnterTransition();
        getWindow().setEnterTransition(visibility);

        // 重新进入的动画。即第二次进入，可以和首次进入不一样。
        // visibility = buildReturnTransition();
        // getWindow().setReenterTransition(visibility);

        // 启动新 Activity ，此页面退出的动画
        visibility = buildReturnTransition();
        getWindow().setExitTransition(visibility);

        // 调用 finishAfterTransition() 退出时，此页面退出的动画
        // visibility = buildReturnTransition();
        // getWindow().setReturnTransition(visibility);
    }


    private void setupLayout() {
        // 前两个使用 Explode ，代码 控制动画
        findViewById(R.id.sample1_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(i);
            }
        });
        // 前两个使用 Explode ，XML 控制动画
        findViewById(R.id.sample1_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(i);
            }
        });
        // 使用 Slide(侧滑) ，代码 控制动画
        findViewById(R.id.sample1_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity3.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(i);
            }
        });
        // 使用 Slide(侧滑) ，XML 控制动画
        findViewById(R.id.sample1_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity3.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(i);
            }
        });

        // 如果定义了 return transition ，将使用 定义的动画过渡
        findViewById(R.id.sample1_button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility returnTransition = buildReturnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
            }
        });
        // 如果没有 return transition 被定义，将使用 反进入 的动画
        findViewById(R.id.sample1_button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

    }


    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(1500);
        // 此视图将不会受到输入过渡动画的影响
        // enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }


    private Visibility buildReturnTransition() {
        Visibility visibility = new Slide();
        visibility.setDuration(1000);
        return visibility;
    }
}
