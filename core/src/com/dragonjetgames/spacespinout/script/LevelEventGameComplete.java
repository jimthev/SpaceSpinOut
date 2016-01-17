package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;

public class LevelEventGameComplete extends LevelEvent {

    public LevelEventGameComplete() {
        eventType = LEVEL_EVENT_GAME_COMPLETE;
    }

    public void doEvent() {
        Director director = Director.getDirector();
        director.sendEvent(SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_GAME_COMPLETE_SCENE, null);
    }

    public String getEventTypeName() {
        return "Game End";
    }

}

