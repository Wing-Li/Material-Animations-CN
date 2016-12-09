package com.lyl.animationtest.base;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lyl.animationtest.R;
import com.lyl.animationtest.TransitionHelper;

/**
 * Wing_Li
 * 2016/9/14.
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 圆球显示属性
     */
    protected static final String EXTRA_SAMPLE = "sample";
    /**
     * 动画类型
     */
    protected static final String EXTRA_TYPE = "type";
    /**
     * 代码 定义动画
     */
    protected static final int TYPE_PROGRAMMATICALLY = 0;
    /**
     * Xml 定义动画
     */
    protected static final int TYPE_XML = 1;

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    protected void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }
}
