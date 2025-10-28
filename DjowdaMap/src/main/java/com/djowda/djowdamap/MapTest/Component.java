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

import java.util.Map;

public class Component {
    private String id;       // Firebase key: THh4RfNRP3Rpqn1wZR55lDen6wq1
    private String name;     // cn in DB
    private boolean isOpen;  // o in DB, "1" = open, "0" = closed
    private long cellId;     // Cell this component belongs to

    public Component() {
        // Default constructor for Firebase
    }

    public Component(String id, String name, boolean isOpen, long cellId) {
        this.id = id;
        this.name = name;
        this.isOpen = isOpen;
        this.cellId = cellId;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isOpen() { return isOpen; }
    public void setOpen(boolean open) { isOpen = open; }

    public long getCellId() { return cellId; }
    public void setCellId(long cellId) { this.cellId = cellId; }

    // Utility to parse from DB value
    public static Component fromFirebase(String id, Map<String, Object> data, long cellId) {
        String name = (String) data.get("cn");
        boolean open = "1".equals(data.get("o"));
        return new Component(id, name, open, cellId);
    }
}


