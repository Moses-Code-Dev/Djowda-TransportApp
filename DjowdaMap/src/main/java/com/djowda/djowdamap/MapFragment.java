package com.djowda.djowdamap;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.djowda.djowdamap.MapTest.Bubbles.ChatFragment;
import com.djowda.djowdamap.MapTest.Bubbles.ComponentOverViewFragment;
import com.djowda.djowdamap.MapTest.Bubbles.MapToolsFragment;
import com.djowda.djowdamap.MapTest.Bubbles.StatisticsFragment;
import com.djowda.djowdamap.MapTest.Custom2DScrollView;
import com.djowda.djowdamap.MapTest.GridAdapter;
import com.djowda.djowdamap.MapTest.Utils.TileMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapFragment extends DialogFragment implements GridAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private Custom2DScrollView scrollView;
    private GridAdapter adapter;
    private FloatingActionButton btn_exitMap;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // This makes the dialog full-screen
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Change status bar color
            Window window = dialog.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // Resolve colorPrimary attribute to actual color
            TypedValue typedValue = new TypedValue();
            requireContext().getTheme().resolveAttribute(
                    com.djowda.shared_res.R.attr.colorPrimary, typedValue, true
            );
            @ColorInt int color = typedValue.data;

            window.setStatusBarColor(color);


        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        btn_exitMap = view.findViewById(R.id.btn_exitMap);


        btn_exitMap.setOnClickListener(view1 -> ExitMapView());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.gridRecyclerView);
        scrollView = view.findViewById(R.id.customScrollView);

        setupRecyclerView();
//        AttachBubbles();
    }


    private void ExitMapView() {
        dismiss();
    }

    private void setupRecyclerView() {
//        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), TileMap.getMapSize());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new GridAdapter(requireContext());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemViewCacheSize(50);
////        recyclerView.setDrawingCacheEnabled(true);
////        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        recyclerView.setAdapter(adapter);
//
//        adapter.setClickListener(this);
//
//        // Center the grid
//        scrollView.post(() -> scrollView.centerOnGrid());


        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), TileMap.getMapSize());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        // --- OPTIMIZATIONS ---
        recyclerView.setHasFixedSize(true); // Set this BEFORE setAdapter
        recyclerView.setItemViewCacheSize(20); // Lowered cache size (optional, test this)

        // REMOVE THESE - They are deprecated and harm performance
        // recyclerView.setDrawingCacheEnabled(true);
        // recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter = new GridAdapter(requireContext());
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        // Center the grid
        scrollView.post(() -> scrollView.centerOnGrid());

    }


    @Override
    public void onItemClick(View view, int position, long cellId) {

        if (cellId != -1) {
            // Option 1: Just show the cell ID
            Toast.makeText(requireContext(), "Cell ID: " + cellId, Toast.LENGTH_SHORT).show();

            // Option 2: Navigate to the clicked cell (uncomment if desired)
            // navigateToCell(cellId, 20);
        } else {
            Toast.makeText(requireContext(), "Empty cell at position: " + position, Toast.LENGTH_SHORT).show();
        }

    }


    private void AttachBubbles() {
        AttachChatFragment();
        AttachComponentOverViewFragment();
        AttachMapToolsFragment();
        AttachStatisticsFragment();
//        AttachCommunityFragment();
    }

    private void AttachChatFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.DjowdaChatHostFragment, new ChatFragment(), "ChatFragment");
        fragmentTransaction.commit();
    }

    private void AttachComponentOverViewFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ComponentOverViewHostFragment, new ComponentOverViewFragment(), "ComponentOverViewFragment");
        fragmentTransaction.commit();
    }

    private void AttachMapToolsFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MapToolsHostFragment, new MapToolsFragment(), "MapToolsFragment");
        fragmentTransaction.commit();
    }

    private void AttachStatisticsFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.StatisticsHostFragment, new StatisticsFragment(), "StatisticsFragment");
        fragmentTransaction.commit();
    }

//    private void AttachCommunityFragment() {
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.CommunityHostFragment, new CommunityFragment(), "CommunityFragment");
//        fragmentTransaction.commit();
//    }
}