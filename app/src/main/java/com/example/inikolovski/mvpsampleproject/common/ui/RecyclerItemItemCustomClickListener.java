package com.example.inikolovski.mvpsampleproject.common.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemItemCustomClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemCustomClickListener itemCustomClickListener;

    public interface OnItemCustomClickListener {

        void onSingleTapUp(int position);
    }

    private GestureDetector gestureDetector;

    public RecyclerItemItemCustomClickListener(Context context, final RecyclerView recyclerView, OnItemCustomClickListener listener) {
        itemCustomClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && itemCustomClickListener != null) {
                    itemCustomClickListener.onSingleTapUp(recyclerView.getChildAdapterPosition(child));
                }
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}