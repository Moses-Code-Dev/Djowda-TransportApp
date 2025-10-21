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


