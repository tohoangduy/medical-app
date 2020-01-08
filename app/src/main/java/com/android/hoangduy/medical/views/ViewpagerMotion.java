package com.android.hoangduy.medical.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.hoangduy.medical.R;

public class ViewpagerMotion extends MotionLayout
        implements ViewPager.OnPageChangeListener {
    private static int PAGE_NUMBER = 3;

    public ViewpagerMotion(Context context) {
        super(context);
    }

    public ViewpagerMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewpagerMotion(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float progress = position + positionOffset;
        Log.d(ViewpagerMotion.class.getSimpleName(), "progress = " + progress + ", position: " + position + ", positionOffset: " + positionOffset);
//        Log.d(ViewpagerMotion.class.getSimpleName(), "position: " + position + ", positionOffset: " + positionOffset);

        if (progress < 1) {
            setTransition(R.id.start, R.id.middle);
        } else if (progress >= 1) {
            setTransition(R.id.middle, R.id.end);
        }

        setProgress(positionOffset);
//        setProgress((position + positionOffset) / (PAGE_NUMBER - 1));
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(ViewpagerMotion.class.getSimpleName(), "page = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
