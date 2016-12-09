package com.lyl.animationtest.two;

import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseFragment;

public class SharedElementFragment1 extends BaseFragment {

    public static SharedElementFragment1 newInstance(Sample sample) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_SAMPLE, sample);
        SharedElementFragment1 fragment1 = new SharedElementFragment1();
        fragment1.setArguments(bundle);
        return fragment1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_element_fragment1, container, false);

        final Sample sample = (Sample) getArguments().getSerializable(EXTRA_SAMPLE);

        final ImageView squareBlue = (ImageView) view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(),sample.getColor());

        view.findViewById(R.id.sample2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment(sample, squareBlue, false);
            }
        });

        view.findViewById(R.id.sample2_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment(sample, squareBlue, true);
            }
        });

        return view;
    }

    private void addNextFragment(Sample sample, ImageView squareBlue, boolean overlap) {
        SharedElementFragment2 sharedElementFragment2 = SharedElementFragment2.newInstance(sample);

        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        sharedElementFragment2.setEnterTransition(slide);
        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
        sharedElementFragment2.setSharedElementEnterTransition(changeBounds);

        getFragmentManager().beginTransaction()
                .replace(R.id.sample2_content,sharedElementFragment2)
                .addToBackStack(null)
                .addSharedElement(squareBlue,getString(R.string.square_blue_name))
                .commit();
    }


}
