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

package com.djowda.djowdamap.MapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CellData {
    private final Map<Integer, List<Component>> cellDataMap = new HashMap<>();
    private final Map<Integer, Long> positionToCellId = new HashMap<>();

    public void updateCell(int gridPosition, long cellId, Component component) {
        positionToCellId.put(gridPosition, cellId);
        List<Component> comps = cellDataMap.computeIfAbsent(gridPosition, k -> new ArrayList<>());
        comps.add(component);
    }

    public void replaceCell(int gridPosition, long cellId, List<Component> components) {
        positionToCellId.put(gridPosition, cellId);
        cellDataMap.put(gridPosition, new ArrayList<>(components));
    }

    public boolean hasData(int gridPosition) {
        return cellDataMap.containsKey(gridPosition) && !cellDataMap.get(gridPosition).isEmpty();
    }

    public List<Component> getCellData(int gridPosition) {
        return cellDataMap.getOrDefault(gridPosition, new ArrayList<>());
    }

    public long getCellIdForPosition(int gridPosition) {
        return positionToCellId.getOrDefault(gridPosition, -1L);
    }

    // Clear all data from the grid
    public void clearAllData() {
        cellDataMap.clear();
        positionToCellId.clear();
    }

    // Check if any data exists in the grid
    public boolean hasAnyData() {
        return !cellDataMap.isEmpty();
    }

    // Get count of cells with data
    public int getDataCellCount() {
        return cellDataMap.size();
    }

    // Clear specific position
    public void clearPosition(int gridPosition) {
        cellDataMap.remove(gridPosition);
        positionToCellId.remove(gridPosition);
    }

    // Get all populated positions
    public Set<Integer> getPopulatedPositions() {
        return new HashSet<>(cellDataMap.keySet());
    }

    // Get total component count across all cells
    public int getTotalComponentCount() {
        return cellDataMap.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    // Check if specific position has components
    public boolean hasComponents(int gridPosition) {
        List<Component> components = cellDataMap.get(gridPosition);
        return components != null && !components.isEmpty();
    }

    // Get component count for specific position
    public int getComponentCount(int gridPosition) {
        List<Component> components = cellDataMap.get(gridPosition);
        return components != null ? components.size() : 0;
    }

    // Clear all components from a specific position but keep the cell ID mapping
    public void clearComponents(int gridPosition) {
        List<Component> components = cellDataMap.get(gridPosition);
        if (components != null) {
            components.clear();
        }
    }

    // Remove empty cells (positions with no components)
    public void removeEmptyCells() {
        cellDataMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        // Also remove corresponding cell ID mappings for empty positions
        Set<Integer> cellDataPositions = cellDataMap.keySet();
        positionToCellId.entrySet().removeIf(entry -> !cellDataPositions.contains(entry.getKey()));
    }

    public void clear() {
        cellDataMap.clear();
        positionToCellId.clear();
    }

}

//public class CellData {
//    private final Map<Integer, List<Component>> cellDataMap = new HashMap<>();
//    private final Map<Integer, Long> positionToCellId = new HashMap<>();
//
//    public void updateCell(int gridPosition, long cellId, Component component) {
//        positionToCellId.put(gridPosition, cellId);
//        List<Component> comps = cellDataMap.computeIfAbsent(gridPosition, k -> new ArrayList<>());
//        comps.add(component);
//    }
//
//    public void replaceCell(int gridPosition, long cellId, List<Component> components) {
//        positionToCellId.put(gridPosition, cellId);
//        cellDataMap.put(gridPosition, new ArrayList<>(components));
//    }
//
//    public boolean hasData(int gridPosition) {
//        return cellDataMap.containsKey(gridPosition) && !cellDataMap.get(gridPosition).isEmpty();
//    }
//
//    public List<Component> getCellData(int gridPosition) {
//        return cellDataMap.getOrDefault(gridPosition, new ArrayList<>());
//    }
//
//    public long getCellIdForPosition(int gridPosition) {
//        return positionToCellId.getOrDefault(gridPosition, -1L);
//    }
//}

