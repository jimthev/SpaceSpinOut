package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;

public class LevelEventLevelComplete extends LevelEvent {

    public LevelEventLevelComplete() {
        eventType = LEVEL_EVENT_LEVEL_COMPLETE;
    }

    public void doEvent() {
        Director director = Director.getDirector();
        director.sendEvent(SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_LEVEL_COMPLETE_SCENE, null);
    }

    public String getEventTypeName() {
        return "Level End";
    }

}
