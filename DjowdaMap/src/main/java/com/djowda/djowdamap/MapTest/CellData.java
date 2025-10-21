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

