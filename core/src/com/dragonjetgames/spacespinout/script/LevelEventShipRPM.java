package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;

public class LevelEventShipRPM extends LevelEvent {
    float shipRPM = 0;

    public LevelEventShipRPM(float shipRPM) {
        this.shipRPM = shipRPM;
        eventType = LEVEL_EVENT_SHIP_RPM;
    }

    public void setEventParam1(float val) {
        shipRPM = val;
    }

    public String getParamText() {
        return ", " + shipRPM;
    }

    public String getParam1() {
        return "" + shipRPM;
    }

    public void doEvent() {
        SpaceSpinOut.spaceSpinOut.setShipRPM(shipRPM);
    }

    public String getEventTypeName() {
        return "Ship RPM";
    }

}
