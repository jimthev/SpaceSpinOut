package com.dragonjetgames.spacespinout.script;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.util.SpaceSpinOutUtil;
import com.netthreads.libgdx.director.Director;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class LevelScript {
    ArrayList<LevelEvent> events;

    LevelEvent nextEvent;

    boolean handleEvents = false;

    float time;
    float previousEventTime;
    private Director director;

    public LevelScript() {
        events = new ArrayList<LevelEvent>();
//    fillTest();
//    setupNextEvent();
    }

    public void insertRow(int index) {
        LevelEvent le = getLevelEvent(index);
        LevelEvent newEvent = getNewEvent(le.eventType, le.eventTime);
        newEvent.setEventParam1(le.getParam1Float());
        newEvent.setEventParam2(le.getParam2Float());
        newEvent.setEventParam3(le.getParam2Float());
        events.add(index, newEvent);
    }

    public void removeRow(int index) {
        events.remove(index);
    }

    public void saveScript(int levelNum) {
        OutputStream os = SpaceSpinOutUtil.getFileOutputStream("levels/level_" + levelNum + ".txt");
        PrintStream ps = new PrintStream(os);

        for (int i = 0; i < getNumNonEmptyEvents(); i++) {
            LevelEvent le = getLevelEvent(i);
            String line = "";
            line += le.getEventType() + ", ";
            line += le.getEventTime();
            line += le.getParamText();
//      System.out.println("line:"+i+":"+line);
            ps.println(line);
        }

        ps.flush();
    }

    public LevelEvent getNewEvent(int eventType, float time) {
        LevelEvent newEvent = null;
        String[] lineParts = {"0", "0", "10", "-10", "25"};
        switch (eventType) {
            case LevelEvent.LEVEL_EVENT_EMPTY:
                newEvent = addEmptyEvent(time, false);
                break;
            case LevelEvent.LEVEL_EVENT_ADD_ASTEROID:
                newEvent = addAddAsteroidEvent(lineParts, time, false);
                break;
            case LevelEvent.LEVEL_EVENT_SCREEN_RPM:
                newEvent = addScreenRPMEvent(lineParts, time, false);
                break;
            case LevelEvent.LEVEL_EVENT_SHIP_RPM:
                newEvent = addShipRPMEvent(lineParts, time, false);
                break;
            case LevelEvent.LEVEL_EVENT_LEVEL_COMPLETE:
                newEvent = addLevelCompleteEvent(lineParts, time, false);
                break;
            case LevelEvent.LEVEL_EVENT_GAME_COMPLETE:
                newEvent = addGameCompleteEvent(lineParts, time, false);
                break;
            default:
                newEvent = null;
                break;
        }
        return newEvent;
    }

    public void setLevelType(int index, int eventType) {
        LevelEvent le = getLevelEvent(index);
        float time = le.eventTime;
        LevelEvent newEvent = getNewEvent(eventType, time);
        if (newEvent != null) {
            events.remove(index);
            events.add(index, newEvent);
        }


    }

    public int getNumEvents() {
        return events.size();
    }

    public int getNumNonEmptyEvents() {
        int index = 0;
        for (int i = 0; i < events.size(); i++) {
            LevelEvent le = events.get(i);
            if (!(le instanceof LevelEventEmpty)) {
                index = i + 1;
            }
        }
        return index;
    }

    public LevelEvent getLevelEvent(int index) {
        return events.get(index);
    }

    public void clearEvents() {
        events = new ArrayList<LevelEvent>();
    }

    public boolean levelExists(int levelNum) {
        return SpaceSpinOutUtil.fileExists("levels/level_" + levelNum + ".txt");
    }

    public void addEvent(int index, LevelEvent le) {
        events.add(index, le);
    }

    public void setEvent(int index, LevelEvent le) {
        events.remove(index);
        events.add(index, le);
    }

    public void loadLevel(int level) {
        events = new ArrayList<LevelEvent>();

        String[] lines = SpaceSpinOutUtil.getFileAsStringArray("levels/level_" + level + ".txt");
        float cumulativeTime = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
//      System.out.println("line:"+i+":"+line);
            String[] lineParts = SpaceSpinOutUtil.breakStringIntoStrings(line, ",");

            int eventType = Integer.parseInt(lineParts[0]);
            float time = Float.parseFloat(lineParts[1]);

            switch (eventType) {
                case LevelEvent.LEVEL_EVENT_EMPTY:
                    addEmptyEvent(time, true);
                    break;
                case LevelEvent.LEVEL_EVENT_ADD_ASTEROID:
                    addAddAsteroidEvent(lineParts, time, true);
                    break;
                case LevelEvent.LEVEL_EVENT_SCREEN_RPM:
                    addScreenRPMEvent(lineParts, time, true);
                    break;
                case LevelEvent.LEVEL_EVENT_SHIP_RPM:
                    addShipRPMEvent(lineParts, time, true);
                    break;
                case LevelEvent.LEVEL_EVENT_LEVEL_COMPLETE:
                    addLevelCompleteEvent(lineParts, time, true);
                    break;
                case LevelEvent.LEVEL_EVENT_GAME_COMPLETE:
                    addGameCompleteEvent(lineParts, time, true);
                    break;
                default:
                    System.out.println("unknown type:" + eventType + " in line:" + line);
            }
        }

        handleEvents = false;
    }

    public LevelEvent addShipRPMEvent(String[] params, float time, boolean addEvent) {
        float screenRPM = Float.parseFloat(params[2]);

        LevelEvent event = new LevelEventShipRPM(screenRPM);
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public LevelEvent addScreenRPMEvent(String[] params, float time, boolean addEvent) {
        float screenRPM = Float.parseFloat(params[2]);

        LevelEvent event = new LevelEventScreenRPM(screenRPM);
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public LevelEvent addGameCompleteEvent(String[] params, float time, boolean addEvent) {
        LevelEvent event = new LevelEventGameComplete();
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public LevelEvent addLevelCompleteEvent(String[] params, float time, boolean addEvent) {
        LevelEvent event = new LevelEventLevelComplete();
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public LevelEvent addEmptyEvent(float time, boolean addEvent) {
        LevelEvent event = new LevelEventEmpty();
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public LevelEvent addAddAsteroidEvent(String[] params, float time, boolean addEvent) {
        float asteroidRadius = Float.parseFloat(params[2]);
        float asteroidRPM = Float.parseFloat(params[3]);
        float asteroidVelocity = Float.parseFloat(params[4]);

        LevelEvent event = new LevelEventAddAsteroid(asteroidRadius, asteroidRPM, asteroidVelocity);
        event.eventTime = time;
        if (addEvent) {
            events.add(event);
        }
        return event;
    }

    public void stopGame() {
        handleEvents = false;
    }

    public void startGame() {
        time = 0.0f;
        previousEventTime = 0.0f;
        handleEvents = true;
    }

    public void setupNextEvent() {
        if (events.isEmpty()) {
            nextEvent = new LevelEvent(); // error event
            nextEvent.eventTime = 0;
            return;
        }
        nextEvent = events.get(0);
        events.remove(0);

        if (!handleEvents) {
            return;
        }
        if (time >= nextEvent.eventTime + previousEventTime) {
            doNextEvent();
        }
    }

    public void fillTest() {
        for (int i = 0; i < 10; i++) {
            LevelEvent event = new LevelEventAddAsteroid(6 + i / 2, -15 + i * 2, 25);
            event.eventTime = i * 3;
            events.add(event);
        }
    }

    public void act(float delta) {
        if (!handleEvents) {
            return;
        }
        time += delta;

        if (time >= nextEvent.eventTime + previousEventTime) {
            doNextEvent();
        }
    }

    public void doNextEvent() {
        previousEventTime += nextEvent.eventTime;
        nextEvent.doEvent();
        setupNextEvent();
    }
}
