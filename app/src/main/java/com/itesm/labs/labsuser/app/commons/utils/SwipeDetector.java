package com.itesm.labs.labsuser.app.commons.utils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mgradob on 7/3/15.
 */
public class SwipeDetector implements View.OnTouchListener {

    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    private SwipeListener swipeListener;

    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return false;
            case MotionEvent.ACTION_MOVE:
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (deltaX < 0) {
                        Log.d("SWIPE", "Swipe Left to Right");
                        swipeListener.swipedRight();
                        return true;
                    } else if (deltaX > 0) {
                        Log.d("SWIPE", "Swipe Right to Left");
                        swipeListener.swipedLeft();
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    public interface SwipeListener {
        void swipedRight();

        void swipedLeft();
    }
}
