package com.lyl.animationtest.two;

import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lyl.animationtest.R;
import com.lyl.animationtest.Sample;
import com.lyl.animationtest.base.BaseFragment;

public class SharedElementFragment2 extends BaseFragment {


    public static SharedElementFragment2 newInstance(Sample sample) {
        SharedElementFragment2 fragment = new SharedElementFragment2();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SAMPLE, sample);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_element_fragment2, container, false);
        Sample sample = (Sample) getArguments().getSerializable(EXTRA_SAMPLE);

        ImageView squareBlue = (ImageView) view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), sample.getColor());
        return view;
    }

}
