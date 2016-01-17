package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved


import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;

public class LevelEvent {
    public static final int LEVEL_EVENT_EMPTY = 0;
    public static final int LEVEL_EVENT_ADD_ASTEROID = 1;
    public static final int LEVEL_EVENT_SCREEN_RPM = 2;
    public static final int LEVEL_EVENT_SHIP_RPM = 3;
    public static final int LEVEL_EVENT_LEVEL_COMPLETE = 4;
    public static final int LEVEL_EVENT_GAME_COMPLETE = 5;
    public static final int LEVEL_EVENT_END_INDEX = 5;


    int eventType;
    float eventTime;

    public void doEvent() {
        Director director = Director.getDirector();
        director.sendEvent(SpaceSpinOutEvents.EVENT_ERROR, null);
    }

    public void setEventParam1(float val) {
    }

    public void setEventParam2(float val) {
    }

    public void setEventParam3(float val) {
    }

    public float getParam1Float() {
        String valF = getParam1();
        if (valF == null) {
            return 0;
        }
        return Float.parseFloat(valF);
    }

    public float getParam2Float() {
        String valF = getParam2();
        if (valF == null) {
            return 0;
        }
        return Float.parseFloat(valF);
    }

    public float getParam3Float() {
        String valF = getParam3();
        if (valF == null) {
            return 0;
        }
        return Float.parseFloat(valF);
    }

    public void setEventTime(float eventTime) {
        this.eventTime = eventTime;
    }

    public float getEventTime() {
        return eventTime;
    }

    public String getParam1() {
        return null;
    }

    public String getParam2() {
        return null;
    }

    public String getParam3() {
        return null;
    }

    public String getParam4() {
        return null;
    }

    public String getEventTypeName() {
        return "UNK";
    }

    public int getEventType() {
        return eventType;
    }

    public LevelEvent getErrorEvent() {
        return new LevelEvent();
    }

    public String getParamText() {
        return "";
    }
}
