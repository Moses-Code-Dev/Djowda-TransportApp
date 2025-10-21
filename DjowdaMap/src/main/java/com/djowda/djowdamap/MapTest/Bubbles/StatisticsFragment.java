package com.djowda.djowdamap.MapTest.Bubbles;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.djowda.djowdamap.R;


public class StatisticsFragment extends Fragment {

    private ImageView toggleButton;
    private LinearLayout contentLayout;
    private LinearLayout collapsedLayout;
    private boolean isExpanded = false;
    private static final int ANIMATION_DURATION = 300;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupClickListeners();
    }

    private void initViews(View view) {
        toggleButton = view.findViewById(R.id.btn_toggle_statistics);
        contentLayout = view.findViewById(R.id.statistics_content_layout);
        collapsedLayout = view.findViewById(R.id.statistics_collapsed_layout);

        // Initially collapsed
        contentLayout.setVisibility(View.GONE);
        collapsedLayout.setVisibility(View.VISIBLE);
    }

    private void setupClickListeners() {
        View.OnClickListener toggleListener = v -> toggleContent();
        toggleButton.setOnClickListener(toggleListener);
        collapsedLayout.setOnClickListener(toggleListener);
    }

    private void toggleContent() {
        if (isExpanded) {
            collapseContent();
        } else {
            expandContent();
        }
        isExpanded = !isExpanded;
    }

    private void expandContent() {
        collapsedLayout.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);

        // Measure the content width
        contentLayout.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(((View) contentLayout.getParent()).getHeight(), View.MeasureSpec.AT_MOST)
        );
        final int targetWidth = contentLayout.getMeasuredWidth();

        // Start from 0 width
        contentLayout.getLayoutParams().width = 0;
        contentLayout.requestLayout();

        // Animate to target width
        ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(animation -> {
            contentLayout.getLayoutParams().width = (int) animation.getAnimatedValue();
            contentLayout.requestLayout();
        });

        // Rotate arrow
        toggleButton.animate().rotation(180f).setDuration(ANIMATION_DURATION).start();

        animator.start();
    }

    private void collapseContent() {
        final int initialWidth = contentLayout.getWidth();

        ValueAnimator animator = ValueAnimator.ofInt(initialWidth, 0);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            if (value == 0) {
                contentLayout.setVisibility(View.GONE);
                collapsedLayout.setVisibility(View.VISIBLE);
            } else {
                contentLayout.getLayoutParams().width = value;
                contentLayout.requestLayout();
            }
        });

        // Rotate arrow back
        toggleButton.animate().rotation(0f).setDuration(ANIMATION_DURATION).start();

        animator.start();
    }
}