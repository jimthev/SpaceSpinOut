package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOutConstants;
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;

public class LevelEventAddAsteroid extends LevelEvent {
    float asteroidRadius = 8;
    float asteroidRPM = -5;
    float asteroidVelocity = 30;

    public LevelEventAddAsteroid(float asteroidRadius, float asteroidRPM, float asteroidVelocity) {
        this.asteroidRadius = asteroidRadius;
        this.asteroidRPM = asteroidRPM;
        this.asteroidVelocity = asteroidVelocity;
        eventType = LEVEL_EVENT_ADD_ASTEROID;
    }

    public String getParamText() {
        return ", " + asteroidRadius + ", " + asteroidRPM + ", " + asteroidVelocity;
    }

    public void setEventParam1(float val) {
        asteroidRadius = val;
    }

    public void setEventParam2(float val) {
        asteroidRPM = val;
    }

    public void setEventParam3(float val) {
        asteroidVelocity = val;
    }

    public String getEventTypeName() {
        return "Asteroid";
    }

    public String getParam1() {
        return "" + asteroidRadius;
    }

    public String getParam2() {
        return "" + asteroidRPM;
    }

    public String getParam3() {
        return "" + asteroidVelocity;
    }


    public void doEvent() {
        SpaceSpinOutConstants.iniAsteroidRadius = asteroidRadius;
        SpaceSpinOutConstants.iniAsteroidRPM = asteroidRPM;
        SpaceSpinOutConstants.iniAsteroidVelocityY = asteroidVelocity;

        Director director = Director.getDirector();
        director.sendEvent(SpaceSpinOutEvents.EVENT_ADD_ASTEROID, null);
    }
}
