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

package com.djowda.djowdamap.MapTest.Utils;

import android.content.Context;

// Optimized TileMap class
public class TileMap {
    private static final int MAP_SIZE = 41;
    private static final int TILE_SIZE_DP = 50;

    // Cache calculated values to avoid repeated calculations
    private static int cachedTileSizePixels = -1;
    private static int cachedTotalSizePixels = -1;
    private static float lastDensity = -1;

    public static int getMapSize() {
        return MAP_SIZE;
    }

    public static int getTotalItems() {
        return MAP_SIZE * MAP_SIZE;
    }

    public static int getTileSizeInPixels(Context context) {
        final float density = context.getResources().getDisplayMetrics().density;

        // Cache the result to avoid repeated calculations
        if (cachedTileSizePixels == -1 || lastDensity != density) {
            cachedTileSizePixels = Math.round(TILE_SIZE_DP * density);
            lastDensity = density;
        }

        return cachedTileSizePixels;
    }

    public static int getTotalSizeInPixels(Context context) {
        // Use cached tile size
        final int tileSize = getTileSizeInPixels(context);
        final float density = context.getResources().getDisplayMetrics().density;

        if (cachedTotalSizePixels == -1 || lastDensity != density) {
            cachedTotalSizePixels = tileSize * MAP_SIZE;
        }

        return cachedTotalSizePixels;
    }

    public static int getCenterPosition() {
        return (MAP_SIZE * MAP_SIZE) / 2;
    }

    /**
     * Clear cache when configuration changes (e.g., screen rotation)
     */
    public static void clearCache() {
        cachedTileSizePixels = -1;
        cachedTotalSizePixels = -1;
        lastDensity = -1;
    }
}
