/*
 *
 *  * Created by the Djowda Project Team
 *  * Copyright (c) 2017-2025 Djowda. All rights reserved.
 *  *
 *  * This file is part of the Djowda Project.
 *  *
 *  * Licensed under the GNU Affero General Public License v3.0 (AGPL-3.0)
 *  *
 *  * You may redistribute and/or modify this file under the terms of the
 *  * GNU Affero General Public License as published by the Free Software Foundation,
 *  * either version 3 of the License, or (at your option) any later version.
 *  *
 *  * This file is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU Affero General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Affero General Public License
 *  * along with this file. If not, see <https://www.gnu.org/licenses/>.
 *  *
 *  * Notes:
 *  * - The Djowda Project is a community-driven initiative evolving toward a decentralized food ecosystem.
 *  * - Contributions are welcome under the AGPL terms.
 *  *
 *  * Last Modified: 2025-10-28 13:30
 *
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
