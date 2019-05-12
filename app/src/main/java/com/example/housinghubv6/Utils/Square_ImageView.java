package com.example.housinghubv6.Utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class Square_ImageView extends AppCompatImageView {

    public Square_ImageView(Context context) {
        super(context);
    }

    public Square_ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Square_ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}