package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

public class LevelEventEmpty extends LevelEvent {

    public LevelEventEmpty() {
        eventType = LEVEL_EVENT_EMPTY;
    }

    public void doEvent() {
    }

    public String getEventTypeName() {
        return "Empty";
    }

}
