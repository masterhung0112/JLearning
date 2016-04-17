package com.free.jlearning.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.widget.SlidingPaneLayout;

public class ContentLinearLayout extends LinearLayout {

    public ContentLinearLayout(Context context) {
        super(context);
    }

    public ContentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout)getParent();
        if (slidingPaneLayout.isSecondChildUnder((int)ev.getX(), (int)ev.getY())) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
