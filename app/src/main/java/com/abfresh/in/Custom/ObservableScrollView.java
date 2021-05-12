package com.abfresh.in.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;


final public class ObservableScrollView extends ScrollView  {
    private ScrollCallbacks mCallbacks;

    private static final int MAX_Y_OVERSCROLL_DISTANCE = 80;

    private Context mContext;
    private int mMaxYOverscrollDistance;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initBounceScrollView();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(ScrollCallbacks listener) {
        mCallbacks = listener;
    }

    private void initBounceScrollView() {
        // get the density of the screen and do some maths with it on the max
        // overscroll distance
        // variable so that you get similar behaviors no matter what the screen
        // size

        final DisplayMetrics metrics = mContext.getResources()
                .getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public interface ScrollCallbacks {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    @SuppressLint("NewApi")
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // This is where the magic happens, we have replaced the incoming
        // maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX,
                mMaxYOverscrollDistance, isTouchEvent);
    }
}
