package com.android.hoangduy.medical.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.activity.MainActivity;
import com.android.hoangduy.medical.adapter.SlideShowAdapter;
import com.android.hoangduy.medical.base.BaseFragment;
import com.android.hoangduy.medical.views.DepthPageTransformer;
import com.android.hoangduy.medical.views.ZoomInViewTransformer;
import com.android.hoangduy.medical.utils.Const;
import com.android.hoangduy.medical.utils.SharePref;

import java.util.ArrayList;
import java.util.List;

public class IntroFrgmt extends BaseFragment
        implements View.OnClickListener {

    private ViewPager contentPager;
    private RecyclerView imgPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button btnGetStarted;
    private TextView tvSkip;
    private DepthPageTransformer depthPageTransformer;

    public static IntroFrgmt newInstance() {
        return new IntroFrgmt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_intro, container, false);

        contentPager = contentView.findViewById(R.id.content_pager);
        imgPager = contentView.findViewById(R.id.img_pager);
        dotsLayout = contentView.findViewById(R.id.layoutDots);
        tvSkip = contentView.findViewById(R.id.tvSkip);
        btnGetStarted = contentView.findViewById(R.id.btnGetStarted);
        imgPager.setLayoutManager(new ZoomInViewTransformer(getContext(), LinearLayoutManager.HORIZONTAL, false));

//        UIHelper.setTextUnderLine(tvSkip, getString(R.string.skip));

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.intro_slide1,
                R.layout.intro_slide2,
                R.layout.intro_slide3
        };
        int[] images = new int[] {
                R.layout.intro_img_1,
                R.layout.intro_img_2,
                R.layout.intro_img_3
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        List<Integer> datasetSlideShow = new ArrayList<>();
        datasetSlideShow.add(R.drawable.img_intro1);
        datasetSlideShow.add(R.drawable.img_intro2);
        datasetSlideShow.add(R.drawable.img_intro3);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(getContext(), datasetSlideShow);
        imgPager.setAdapter(slideShowAdapter);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(layouts);
        contentPager.setAdapter(myViewPagerAdapter);
        contentPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnGetStarted.setOnClickListener(this);
        tvSkip.setOnClickListener(this);

        return contentView;
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 0, 15, 0);
            dots[i].setLayoutParams(params);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
    }

    private void gotoMainMenu() {
        SharePref.putBoolean(getContext(), Const.KEY_SKIP_INTRO, true);

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnGetStarted.setVisibility(View.VISIBLE);
                tvSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnGetStarted.setVisibility(View.GONE);
                tvSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetStarted:
            case R.id.tvSkip:
                gotoMainMenu();
                break;
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {

        private int[] contains;

        MyViewPagerAdapter(int[] contains) {
            this.contains = contains;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(contains[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return contains.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
