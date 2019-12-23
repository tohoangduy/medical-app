package com.android.hoangduy.medical.views;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.hoangduy.medical.R;

/**
 * Created by To Hoang Duy
 */
public class SpinnerButtons extends LinearLayout implements View.OnClickListener {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
    private TextView tvValue;
    private OnValueChange listener;
    private int mValue = 0,
            minValue = Integer.MIN_VALUE,
            maxValue = Integer.MAX_VALUE;

    public SpinnerButtons(Context context) {
        super(context);
        createCustomWidget(null);
    }

    public SpinnerButtons(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createCustomWidget(attrs);
    }

    public SpinnerButtons(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createCustomWidget(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpinnerButtons(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createCustomWidget(attrs);
    }

//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateSpinnerButton(context, appWidgetManager, appWidgetId);
//        }
//    }

//    static void updateSpinnerButton(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.empty_layout.new_app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    private void createCustomWidget(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpinnerButtons);
            minValue = a.getInteger(R.styleable.SpinnerButtons_minValue, Integer.MIN_VALUE);
            maxValue = a.getInteger(R.styleable.SpinnerButtons_maxValue, Integer.MAX_VALUE);
        }

        LayoutInflater.from(getContext()).inflate(R.layout.spinner_two_buttons, this);

        // Get minus, plus buttons
        ImageButton prevButton = findViewById(R.id.btnMinus);
        ImageButton nextButton = findViewById(R.id.btnPlus);
        tvValue = findViewById(R.id.tvValue);

        // add listeners
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMinus:
                if (mValue - 1 > minValue) {
                    tvValue.setText(String.valueOf(--mValue));
                }
                if (listener != null) {
                    listener.onChange(getId(), mValue);
                }
                break;

            case R.id.btnPlus:
                if (mValue + 1 < maxValue) {
                    tvValue.setText(String.valueOf(++mValue));
                }
                if (listener != null) {
                    listener.onChange(getId(), mValue);
                }
                break;
        }
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
        tvValue.setText(String.valueOf(mValue));
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setOnValueChangeListener(OnValueChange listener) {
        this.listener = listener;
    }

    public interface OnValueChange {
        public void onChange(int id, int value);
    }
}
