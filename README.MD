# Material-Animations-CN

## **具体介绍：**[【Transition】Android炫酷的页面切换效果](http://www.jianshu.com/p/a43daa1e3d6e)

**此篇 API 均为 Android 5.0（API 级别 21） 以上才可支持。**

此demo一共分为四部分：

> 1.1 普通过渡 Transition；<br />
1.2 Shared Elements Transition 共享元素；<br />
2.0 TransitionManager 控制动画；<br />
3.0 ViewAnimationUtils 显示或隐藏效果。


<br />
***

## Transition 动画

| explode | slide | fade |
|:------:|:------:|:------:|
|从中心移入或移出|从边缘移入或移出|调整透明度产生渐变|
|![](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/Explode.gif)|![](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/Slide.gif)|![](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/Fade.gif)|

### Transition 调用场景

上面的三张图，每张都经历了：

退出 -> 进入  -> 返回   -> 重新进入

Exit -> Enter -> Return -> Reenter

**第一个页面设置：**

android:windowExitTransition      启动新 Activity ，此页面退出的动画

android:windowReenterTransition   重新进入的动画。即第二次进入，可以和首次进入不一样。

**第二个页面设置：**

android:windowEnterTransition     首次进入显示的动画

android:windowReturnTransition    调用 finishAfterTransition() 退出时，此页面退出的动画

如此即可达到以上效果。

<br />
## Shared Elements Transition 共享元素

![Shared Elements Transition](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/SharedElements.gif)

跳转时，增加了参数，即声明要共享的元素：

    protected void transitionTo(Intent i) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                                                        Pair.create(view1, "agreedName1"),
                                                        Pair.create(view2, "agreedName2"));
        startActivity(i, options.toBundle());
    }

设置共享的动画：

    ChangeBounds changeBounds = new ChangeBounds();
    changeBounds.setDuration(500);

    getWindow().setSharedElementEnterTransition(changeBounds);

<br />
## TransitionManager 控制动画

![TransitionManager](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/TransitionManager.gif)

只需要定义布局，调用跳转即可：

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

<br />
## CircularReveal 显示或隐藏 的效果

![CircularReveal](https://github.com/Wing-Li/Material-Animations-CN/blob/master/img/CircularReveal.gif)

显示 View ：

    private void animShow() {
        View myView = findViewById(R.id.my_view);
        // 从 View 的中心开始
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

        //为此视图创建动画设计(起始半径为零)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        // 使视图可见并启动动画
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

隐藏 View ：

    private void animHide() {
        final View myView = findViewById(R.id.my_view);
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        int initialRadius = myView.getWidth();

        // 半径 从 viewWidth -> 0
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }


<br />
# Sample source code

https://github.com/Wing-Li/Material-Animations-CN
