/*
 * Created by the Djowda Project Team
 * Copyright (c) 2017-2025 Djowda. All rights reserved.
 *
 * This file is part of the Djowda Project.
 *
 * Licensed under the Djowda Non-Commercial, Non-Profit License v1.0
 *
 * Permissions:
 * - You may use, modify, and share this file for non-commercial and non-profit purposes only.
 * - Commercial use of this file, in any form, requires prior written permission
 *   from the Djowda Project maintainers.
 *
 * Notes:
 * - This project is community-driven and continuously evolving.
 * - The Djowda Project reserves the right to relicense future versions.
 *
 * Last Modified: 2025-09-16 19:57
 */

package com.djowda.djowdaTransport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.djowda.djowdamap.MapFragment;
import com.google.android.material.button.MaterialButton;

public class MainFragment extends Fragment {

    private MaterialButton btn_openDjowdaMap;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btn_openDjowdaMap = view.findViewById(R.id.btn_openDjowdaMap);

        btn_openDjowdaMap.setOnClickListener(view1 -> {

            OpenDjowdaMap();

        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Show the home fragment by default

    }

    private void OpenDjowdaMap() {

        // Create an instance of the map fragment
        MapFragment mapFragment = new MapFragment();

        // Show the dialog fragment using the parent fragment manager and a unique tag
        mapFragment.show(getParentFragmentManager(), "MapFragment");
    }

}