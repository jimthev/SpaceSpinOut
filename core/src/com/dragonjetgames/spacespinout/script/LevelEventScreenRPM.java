package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;

public class LevelEventScreenRPM extends LevelEvent {
    float screenRPM = 0;

    public LevelEventScreenRPM(float screenRPMy) {
        this.screenRPM = screenRPMy;
        eventType = LEVEL_EVENT_SCREEN_RPM;
    }

    public void setEventParam1(float val) {
        screenRPM = val;
    }

    public String getParamText() {
        return ", " + screenRPM;
    }

    public String getParam1() {
        return "" + screenRPM;
    }

    public void doEvent() {
        SpaceSpinOut.spaceSpinOut.setViewRPM(screenRPM);
    }

    public String getEventTypeName() {
        return "Screen RPM";
    }

}
