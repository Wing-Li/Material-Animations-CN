package com.lyl.animationtest.base;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Sample> samples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        setupSamples();
        setupToolbar();
        setupLayout();
    }

    private void setupWindowAnimations() {
        // 侧滑动画
        Slide transition = new Slide();
        transition.setSlideEdge(Gravity.LEFT);
        transition.setDuration(getResources().getInteger(R.integer.anim_duration_long));

        // 爆炸效果的动画
        // Explode transition = new Explode();
        // transition.setDuration(getResources().getInteger(R.integer.anim_duration_long));

        // 渐变动画
        // Fade transition = new Fade();
        // transition.setDuration(getResources().getInteger(R.integer.anim_duration_long));

        // 这两个方法在 TransitionActivity1 详解
        getWindow().setReenterTransition(transition);
        getWindow().setExitTransition(transition);
    }

    private void setupSamples() {
        samples = Arrays.asList(new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"), //
                new Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"), //
                new Sample(ContextCompat.getColor(this, R.color.sample_green), "View animations"),//
                new Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation"));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sample_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SamplesRecyclerAdapter samplesRecyclerAdapter = new SamplesRecyclerAdapter(this, samples);
        recyclerView.setAdapter(samplesRecyclerAdapter);
    }

}
