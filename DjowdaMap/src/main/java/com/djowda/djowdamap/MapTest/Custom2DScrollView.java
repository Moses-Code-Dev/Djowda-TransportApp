/*
 *
 *  * Created by the Djowda Project Team
 *  * Copyright (c) 2017-2025 Djowda. All rights reserved.
 *  *
 *  * This file is part of the Djowda Project.
 *  *
 *  * Licensed under the Djowda Non-Commercial, Non-Profit License v1.0
 *  *
 *  * Permissions:
 *  * - You may use, modify, and share this file for non-commercial and non-profit purposes only.
 *  * - Commercial use of this file, in any form, requires prior written permission
 *  *   from the Djowda Project maintainers.
 *  *
 *  * Notes:
 *  * - This project is community-driven and continuously evolving.
 *  * - The Djowda Project reserves the right to relicense future versions.
 *  *
 *  * Last Modified: 2025-08-16 18:01
 *
 */

package com.djowda.djowdamap.MapTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.djowda.djowdamap.MapTest.Utils.TileMap;


public class Custom2DScrollView extends ViewGroup {
    // Cache frequently used values
    private final int totalSize;
    private final int touchSlop;
    private final VelocityTracker velocityTracker;
    private final Scroller scroller;
    private final OverScroller overScroller; // Better than Scroller for fling gestures

    // Touch handling optimization
    private float lastX, lastY; // Use float for better precision
    private float downX, downY;
    private boolean isDragging = false;
    private boolean isScrolling = false;

    // Performance flags
    private boolean isLayoutRequested = false;
    private int lastScrollX = -1, lastScrollY = -1; // Cache to avoid unnecessary redraws

    // Constants for better performance
    private static final int MINIMUM_VELOCITY = 50;
    private static final int MAXIMUM_VELOCITY = 8000;

    public Custom2DScrollView(Context context) {
        this(context, null);
    }

    public Custom2DScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize once in constructor
        scroller = new Scroller(context);
        overScroller = new OverScroller(context);
        velocityTracker = VelocityTracker.obtain();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();

        totalSize = TileMap.getTotalSizeInPixels(context);

        // Enable hardware acceleration if not already enabled
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // Optimize drawing
        setWillNotDraw(false); // Enable onDraw if needed
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Cache the resolved dimensions to avoid repeated calculations
        int width = resolveSize(totalSize, widthMeasureSpec);
        int height = resolveSize(totalSize, heightMeasureSpec);

        if (getChildCount() > 0) {
            View child = getChildAt(0);
            // Only measure child if dimensions changed
            if (child.getMeasuredWidth() != totalSize || child.getMeasuredHeight() != totalSize) {
                child.measure(
                        MeasureSpec.makeMeasureSpec(totalSize, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(totalSize, MeasureSpec.EXACTLY)
                );
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Only layout if actually changed or requested
        if ((changed || isLayoutRequested) && getChildCount() > 0) {
            View child = getChildAt(0);
            child.layout(0, 0, totalSize, totalSize);
            isLayoutRequested = false;
        }
    }

    @Override
    public void requestLayout() {
        isLayoutRequested = true;
        super.requestLayout();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        // Don't intercept if we're already scrolling
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isDragging = false;
            isScrolling = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = downX = ev.getX();
                lastY = downY = ev.getY();
                isDragging = false;
                isScrolling = false;

                // Stop any ongoing scroll animation
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    return true;
                }

                final float x = ev.getX();
                final float y = ev.getY();
                final float deltaX = Math.abs(x - downX);
                final float deltaY = Math.abs(y - downY);

                // Use touch slop for better touch detection
                if (deltaX > touchSlop || deltaY > touchSlop) {
                    isDragging = true;
                    isScrolling = true;

                    // Update last position to current for smooth scrolling
                    lastX = x;
                    lastY = y;

                    // Request parent to not intercept further events
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
        }

        return isDragging;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Add velocity tracking for better fling gestures
        velocityTracker.addMovement(event);

        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = downX = x;
                lastY = downY = y;
                isDragging = false;

                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (!isDragging) {
                    final float deltaX = Math.abs(x - downX);
                    final float deltaY = Math.abs(y - downY);

                    if (deltaX > touchSlop || deltaY > touchSlop) {
                        isDragging = true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                if (isDragging) {
                    final int deltaX = (int) (lastX - x);
                    final int deltaY = (int) (lastY - y);

                    scrollByInternal(deltaX, deltaY);

                    lastX = x;
                    lastY = y;
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDragging) {
                    // Calculate velocity for fling
                    velocityTracker.computeCurrentVelocity(1000, MAXIMUM_VELOCITY);
                    final int velocityX = (int) velocityTracker.getXVelocity();
                    final int velocityY = (int) velocityTracker.getYVelocity();

                    // Start fling if velocity is significant
                    if (Math.abs(velocityX) > MINIMUM_VELOCITY || Math.abs(velocityY) > MINIMUM_VELOCITY) {
                        fling(velocityX, velocityY);
                    }

                    isDragging = false;
                }

                // Reset velocity tracker
                velocityTracker.clear();
                return true;
        }

        return true;
    }

    /**
     * Optimized scroll method that avoids unnecessary invalidations
     */
    private void scrollByInternal(int deltaX, int deltaY) {
        final int oldScrollX = getScrollX();
        final int oldScrollY = getScrollY();

        int newScrollX = oldScrollX + deltaX;
        int newScrollY = oldScrollY + deltaY;

        // Clamp to bounds
        final int maxScrollX = Math.max(0, totalSize - getWidth());
        final int maxScrollY = Math.max(0, totalSize - getHeight());

        newScrollX = Math.max(0, Math.min(newScrollX, maxScrollX));
        newScrollY = Math.max(0, Math.min(newScrollY, maxScrollY));

        // Only scroll if position actually changed
        if (newScrollX != oldScrollX || newScrollY != oldScrollY) {
            scrollTo(newScrollX, newScrollY);
        }
    }

    /**
     * Implement fling gesture for better user experience
     */
    private void fling(int velocityX, int velocityY) {
        final int maxX = Math.max(0, totalSize - getWidth());
        final int maxY = Math.max(0, totalSize - getHeight());

        overScroller.fling(
                getScrollX(), getScrollY(),
                -velocityX, -velocityY, // Negative because we want opposite direction
                0, maxX,
                0, maxY,
                0, 0 // No over-scroll for now
        );

        invalidate();
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = overScroller.getCurrX();
            int y = overScroller.getCurrY();

            if (oldX != x || oldY != y) {
                scrollTo(x, y);
            }

            // Continue animation
            if (!overScroller.isFinished()) {
                postInvalidateOnAnimation();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        // Cache scroll values to avoid unnecessary redraws
        if (lastScrollX != x || lastScrollY != y) {
            super.scrollTo(x, y);
            lastScrollX = x;
            lastScrollY = y;
        }
    }

    public void centerOnGrid() {
        post(() -> {
            final int centerX = (totalSize - getWidth()) / 2;
            final int centerY = (totalSize - getHeight()) / 2;
            scrollTo(centerX, centerY);
        });
    }

    /**
     * Smooth scroll to center with animation
     */
    public void smoothCenterOnGrid() {
        post(() -> {
            final int centerX = (totalSize - getWidth()) / 2;
            final int centerY = (totalSize - getHeight()) / 2;

            overScroller.startScroll(
                    getScrollX(), getScrollY(),
                    centerX - getScrollX(), centerY - getScrollY(),
                    500 // 500ms animation
            );

            invalidate();
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Clean up resources
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
    }
}


