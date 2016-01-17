package com.dragonjetgames.spacespinout.layer;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.dragonjetgames.spacespinout.script.LevelEvent;
import com.dragonjetgames.spacespinout.script.LevelEventEmpty;
import com.dragonjetgames.spacespinout.script.LevelScript;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

import java.util.ArrayList;

public class DebugMenuLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";

    private Table table;
    private Skin skin;
    private Label copyrightLabel;

    private TextButton toggleBox2D;
    private TextButton shipRPMPlus;
    private TextButton shipRPMMinus;
    private Label shipRPM;
    private TextButton viewRPMPlus;
    private TextButton viewRPMMinus;
    private Label viewRPM;
    private TextButton asteroidRPMPlus;
    private TextButton asteroidRPMMinus;
    private Label asteroidRPM;
    private TextButton levelNumPlus;
    private TextButton levelNumMinus;
    private Label levelNum;
    private TextButton pageNumPlus;
    private TextButton pageNumMinus;
    private Label pageNum;

    private TextButton togglePause;
    private TextButton mainMenu;

    int levelLoaded = 1;
    int pageLoaded = 1;

    public static final int NUM_EVENTS_PER_PAGE = 15;

    private float magnificationScale = 1.0f;

    private Director director;

    LevelScript levelScript;

    ArrayList<LevelEventRow> levelEventRows;

    public static int NUM_EVENT_COLUMNS = 6;

    class LevelEventRow {
        public float eventTimeDelta = 0.5f;
        public float eventParamDelta = 1.0f;
        public int rowIndex;
        //    Table table;
        TextButton eventRowPlus;
        TextButton eventRowMinus;

        Table indexLabelTable;
        Label indexLabel;
        Label eventTypeLabel;
        Table eventTypeTable;
        Label eventParam1Label;
        Table eventParam1Table;
        Label eventParam2Label;
        Table eventParam2Table;
        Label eventParam3Label;
        Table eventParam3Table;
        Label eventTimeLabel;
        Table eventTimeTable;
        Label l;

        TextButton eventTimePlus;
        TextButton eventTimeMinus;

        TextButton eventTypePlus;
        TextButton eventTypeMinus;

        TextButton eventParam1Plus;
        TextButton eventParam1Minus;
        TextButton eventParam2Plus;
        TextButton eventParam2Minus;
        TextButton eventParam3Plus;
        TextButton eventParam3Minus;

        public LevelEventRow(int rowIndex) {
            this.rowIndex = rowIndex;
//      table = new Table();
            createRow();
        }

        public void saveLevelScript() {
            levelScript.saveScript(levelLoaded);
        }

        public Actor getEventColumn(int column) {
            switch (column) {
                case 0:
                    return indexLabelTable;
                case 1:
                    return eventTimeTable;
                case 2:
                    return eventTypeTable;
                case 3:
                    return eventParam1Table;
                case 4:
                    return eventParam2Table;
                case 5:
                    return eventParam3Table;
                case 6:
                    return l;
                default:
                    return new Label("X", skin, URL_LABEL_FONT_SMALL, Color.RED);
            }
        }

        public void fillRowData() {
            if (rowIndex >= levelScript.getNumEvents()) {
                levelScript.addEvent(rowIndex, new LevelEventEmpty());
            }
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            indexLabel.setText("" + (rowIndex + 1) + ":");
            String typeText = le.getEventTypeName();

            eventTypeLabel.setText(typeText);

            String param1 = le.getParam1();
            if (param1 == null) {
                param1 = "X";
            }
            eventParam1Label.setText(param1);

            String param2 = le.getParam2();
            if (param2 == null) {
                param2 = "X";
            }
            eventParam2Label.setText(param2);

            String param3 = le.getParam3();
            if (param3 == null) {
                param3 = "X";
            }
            eventParam3Label.setText(param3);

            String eventTime = "" + le.getEventTime();
            eventTimeLabel.setText(eventTime);
        }

        public void incrementEventParam1(int deltaInt) {
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            String paramStr = le.getParam1();
            if (paramStr == null) {
                return;
            }
            float param = Float.parseFloat(le.getParam1());
            param += eventParamDelta * deltaInt;
            le.setEventParam1(param);
            fillRowData();
            saveLevelScript();
        }

        public void incrementEventParam2(int deltaInt) {
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            String paramStr = le.getParam2();
            if (paramStr == null) {
                return;
            }
            float param = Float.parseFloat(le.getParam2());
            param += eventParamDelta * deltaInt;
            le.setEventParam2(param);
            fillRowData();
            saveLevelScript();
        }

        public void incrementEventRow(int deltaInt) {
            if (deltaInt > 0) {
                levelScript.insertRow(rowIndex);
            } else {
                levelScript.removeRow(rowIndex);
            }
            loadPage(pageLoaded);
            //     fillRowData();
            saveLevelScript();
        }

        public void incrementEventParam3(int deltaInt) {
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            String paramStr = le.getParam3();
            if (paramStr == null) {
                return;
            }
            float param = Float.parseFloat(le.getParam3());
            param += eventParamDelta * deltaInt;
            le.setEventParam3(param);
            fillRowData();
            saveLevelScript();
        }

        public void incrementEventType(int deltaInt) {
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            int eventType = le.getEventType();
            eventType += deltaInt;
            if (eventType < 0) {
                return;
            }
            if (eventType > LevelEvent.LEVEL_EVENT_END_INDEX) {
                return;
            }
            levelScript.setLevelType(rowIndex, eventType);
            fillRowData();
            saveLevelScript();
        }

        public void incrementEventTime(int deltaInt) {
            LevelEvent le = levelScript.getLevelEvent(rowIndex);
            float eventTime = le.getEventTime();
            eventTime += eventTimeDelta * deltaInt;
            le.setEventTime(eventTime);
            fillRowData();
            saveLevelScript();
        }

        public void createRow() {
//      table = new Table();
            indexLabelTable = new Table();
            indexLabel = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

            l = new Label("l", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

            eventRowPlus = new TextButton("+", skin);
            eventRowMinus = new TextButton("-", skin);
            indexLabelTable.add(eventRowPlus);
            indexLabelTable.add(eventRowMinus);
            indexLabelTable.add(indexLabel);

            eventTypeTable = new Table();
            eventTypeLabel = new Label("Asteroid", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

            eventTypePlus = new TextButton("+", skin);
            eventTypePlus.getLabel().setFontScale(.3f);
            eventTypeMinus = new TextButton("-", skin);
            eventTypeMinus.getLabel().setFontScale(.3f);
            Table eventTypePMTable = new Table();
            eventTypePMTable.add(eventTypePlus).left();
            eventTypePMTable.row();
            eventTypePMTable.add(eventTypeMinus);
            eventTypeTable.add(eventTypePMTable).left();

            eventTypeTable.add(eventTypeLabel).left();

            eventParam1Plus = new TextButton("+", skin);
            eventParam1Plus.getLabel().setFontScale(.3f);
            eventParam1Minus = new TextButton("-", skin);
            eventParam1Minus.getLabel().setFontScale(.3f);
            eventParam2Plus = new TextButton("+", skin);
            eventParam2Plus.getLabel().setFontScale(.3f);
            eventParam2Minus = new TextButton("-", skin);
            eventParam2Minus.getLabel().setFontScale(.3f);
            eventParam3Plus = new TextButton("+", skin);
            eventParam3Plus.getLabel().setFontScale(.3f);
            eventParam3Minus = new TextButton("-", skin);
            eventParam3Minus.getLabel().setFontScale(.3f);

            eventParam1Table = new Table();
            eventParam1Label = new Label("1", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
            Table eventParam1PMTable = new Table();
            eventParam1PMTable.add(eventParam1Plus).left();
            eventParam1PMTable.row();
            eventParam1PMTable.add(eventParam1Minus);
            eventParam1Table.add(eventParam1PMTable).left();
            eventParam1Table.add(eventParam1Label).left();

            eventParam2Table = new Table();
            eventParam2Label = new Label("2", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
            Table eventParam2PMTable = new Table();
            eventParam2PMTable.add(eventParam2Plus).left();
            eventParam2PMTable.row();
            eventParam2PMTable.add(eventParam2Minus);
            eventParam2Table.add(eventParam2PMTable).left();
            eventParam2Table.add(eventParam2Label).left();

            eventParam3Table = new Table();
            eventParam3Label = new Label("3", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
            Table eventParam3PMTable = new Table();
            eventParam3PMTable.add(eventParam3Plus).left();
            eventParam3PMTable.row();
            eventParam3PMTable.add(eventParam3Minus);
            eventParam3Table.add(eventParam3PMTable).left();
            eventParam3Table.add(eventParam3Label).left();

            eventTimeTable = new Table();
            eventTimeLabel = new Label("T", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

            eventTimePlus = new TextButton("+", skin);
            eventTimePlus.getLabel().setFontScale(.3f);
            eventTimeMinus = new TextButton("-", skin);
            eventTimeMinus.getLabel().setFontScale(.3f);

            Table eventTimePMTable = new Table();
            eventTimePMTable.add(eventTimePlus).left();
            eventTimePMTable.row();
            eventTimePMTable.add(eventTimeMinus);
            eventTimeTable.add(eventTimePMTable).left();
            eventTimeTable.add(eventTimeLabel).left();

            eventTimePlus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventTime(1);
                }
            });

            eventTimeMinus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventTime(-1);
                }
            });

            eventTypePlus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventType(1);
                }
            });

            eventTypeMinus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventType(-1);
                }
            });

            eventParam1Plus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam1(1);
                }
            });

            eventParam1Minus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam1(-1);
                }
            });

            eventParam2Plus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam2(1);
                }
            });

            eventParam2Minus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam2(-1);
                }
            });

            eventParam3Plus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam3(1);
                }
            });

            eventParam3Minus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventParam3(-1);
                }
            });

            eventRowPlus.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventRow(1);
                }
            });

            eventRowMinus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    incrementEventRow(-1);
                }
            });

            fillRowData();
        }

        public Table getTable() {
            return table;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
            loadRow();
        }

        public void loadRow() {
//      LevelEvent le = levelScript.getLevelEvent(rowIndex);
//      if (le == null) {
//        clear();
//        return;
//      }
            fillRowData();
        }

        public void clear() {

        }

    }

    public DebugMenuLayer(float width, float height) {
        director = Director.getDirector();

        setWidth(width);
        setHeight(height);

        loadTextures();

        levelScript = new LevelScript();

        levelEventRows = new ArrayList<LevelEventRow>();
        for (int i = 0; i < NUM_EVENTS_PER_PAGE; i++) {
            levelEventRows.add(new LevelEventRow(i));
        }

        buildElements();
    }

    public void iniGame() {
        loadLevel(SpaceSpinOut.spaceSpinOut.currentLevel);
    }

    public void loadLevel(int levelToLoad) {
        if (levelToLoad < 1) {
            return;
        }
        if (!levelScript.levelExists(levelToLoad)) {
            levelScript.clearEvents();
            levelLoaded = levelToLoad;
            loadPage(1);
            return;
        }

        levelScript.loadLevel(levelToLoad);
        levelLoaded = levelToLoad;

        loadPage(1);
    }

    public void loadPage(int pageToLoad) {
        if (pageToLoad < 1) {
            pageToLoad = 1;
        }
        int numEvents = levelScript.getNumNonEmptyEvents();
        if (pageToLoad > numEvents / NUM_EVENTS_PER_PAGE + 1) {
            return;
        }

        pageLoaded = pageToLoad;

        clearOnScreenEvents();

        for (int i = 0; i < NUM_EVENTS_PER_PAGE; i++) {
            int levelEventIndex = i + (pageLoaded - 1) * NUM_EVENTS_PER_PAGE;
            loadRow(i, levelEventIndex);
        }

        setUIValues();
    }

    public void loadRow(int rowIndex, int eventIndex) {
        LevelEventRow levelEventRow = levelEventRows.get(rowIndex);
        levelEventRow.setRowIndex(eventIndex);
        levelEventRow.loadRow();
    }

    public void clearOnScreenEvents() {
        LevelEventRow levelEventRow;
        for (int i = 0; i < NUM_EVENTS_PER_PAGE; i++) {
            levelEventRow = levelEventRows.get(i);
            levelEventRow.clear();
        }
    }

    private void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    public void setUIValues() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        shipRPM.setText("" + sso.getShipRPM());
        viewRPM.setText("" + sso.getViewRPM());
        asteroidRPM.setText("" + sso.getAsteroidRPM());
        levelNum.setText("" + levelLoaded);
        pageNum.setText("" + pageLoaded);
    }

    private void buildElements() {
        copyrightLabel = new Label("Copyright 2016 by Dragonjet Games", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        toggleBox2D = new TextButton("Box2D", skin);
        shipRPM = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        Label preShip = new Label("Ship:", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        Label preView = new Label("View:", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        Label preAsteroid = new Label("Ast:", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        Label preLevelNum = new Label("Level:", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        Label prePageNum = new Label("Page:", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        togglePause = new TextButton("Pause", skin);
        mainMenu = new TextButton("Main", skin);

        shipRPMPlus = new TextButton("+", skin);
        shipRPMPlus.getLabel().setFontScale(.3f);

        shipRPMMinus = new TextButton("-", skin);
        shipRPMMinus.getLabel().setFontScale(.3f);

        viewRPM = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        viewRPMPlus = new TextButton("+", skin);
        viewRPMPlus.getLabel().setFontScale(.3f);

        viewRPMMinus = new TextButton("-", skin);
        viewRPMMinus.getLabel().setFontScale(.3f);

        asteroidRPM = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        asteroidRPMPlus = new TextButton("+", skin);
        asteroidRPMPlus.getLabel().setFontScale(.3f);

        asteroidRPMMinus = new TextButton("-", skin);
        asteroidRPMMinus.getLabel().setFontScale(.3f);

        levelNum = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        levelNumPlus = new TextButton("+", skin);
        levelNumPlus.getLabel().setFontScale(.3f);

        levelNumMinus = new TextButton("-", skin);
        levelNumMinus.getLabel().setFontScale(.3f);

        pageNum = new Label("", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        pageNumPlus = new TextButton("+", skin);
        pageNumPlus.getLabel().setFontScale(.3f);

        pageNumMinus = new TextButton("-", skin);
        pageNumMinus.getLabel().setFontScale(.3f);

        copyrightLabel.setFontScale(magnificationScale * 0.5f);

        table = new Table();

        Table innerTable = new Table();
//    Table shipRPMPMTable = new Table();
//    Table viewRPMPMTable = new Table();
//    Table asteroidRPMPMTable = new Table();
        Table levelNumPMTable = new Table();
        Table pageNumPMTable = new Table();

        table.setSize(getWidth(), getHeight());

//    table.add(toggleBox2D).top().padTop(getHeight() / 16);
        innerTable.add(toggleBox2D).left().padLeft(10);


        innerTable.add(preLevelNum).left().padLeft(10);
        levelNumPMTable.add(levelNumPlus).left();
        levelNumPMTable.row();
        levelNumPMTable.add(levelNumMinus);
        innerTable.add(levelNumPMTable).left();
        innerTable.add(levelNum).left();

        innerTable.add(prePageNum).left().padLeft(10);
        pageNumPMTable.add(pageNumPlus);
        pageNumPMTable.row();
        pageNumPMTable.add(pageNumMinus);
        innerTable.add(pageNumPMTable).left();
        innerTable.add(pageNum).left();

//    innerTable.add(preView).left().padLeft(10);
//    viewRPMPMTable.add(viewRPMPlus);
//    viewRPMPMTable.row();
//    viewRPMPMTable.add(viewRPMMinus);
//    innerTable.add(viewRPMPMTable).left();
//    innerTable.add(viewRPM).left();

//    innerTable.add(preAsteroid).left().padLeft(10);
//    asteroidRPMPMTable.add(asteroidRPMPlus);
//    asteroidRPMPMTable.row();
//    asteroidRPMPMTable.add(asteroidRPMMinus);
//    innerTable.add(asteroidRPMPMTable).left();
//    innerTable.add(asteroidRPM).left();

        innerTable.add((Actor) null).expandX();
        innerTable.add(togglePause).right().padRight(5);
        innerTable.add(mainMenu).right();

        table.add(innerTable).fillX().padBottom(30);

        Table eventTable = new Table();

        table.row();
        for (int i = 0; i < NUM_EVENTS_PER_PAGE; i++) {
            for (int j = 0; j < NUM_EVENT_COLUMNS; j++) {
                eventTable.add(levelEventRows.get(i).getEventColumn(j)).left().padLeft(10);
            }
            eventTable.add((Actor) null).expandX();
            eventTable.row();
        }

        table.add(eventTable).expandX().fillX();
        table.row();
        table.add((Actor) null).expand();
        table.row();

//    table.add(copyrightLabel).bottom();
//    table.add(copyrightLabel);

        table.setFillParent(true);

//    table.pack();

//    table.setDebug(true);


        setUIValues();

        addActor(table);


        toggleBox2D.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TOGGLE_BOX2D, event.getRelatedActor());
            }
        });

        levelNumPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                loadLevel(levelLoaded + 1);
                setUIValues();
            }
        });

        levelNumMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadLevel(levelLoaded - 1);
                setUIValues();
            }
        });

        pageNumPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadPage(pageLoaded + 1);
                setUIValues();
            }

        });

        pageNumMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadPage(pageLoaded - 1);
                setUIValues();
            }
        });

        shipRPMPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_INCREASE_SHIP_RPM, event.getRelatedActor());
                setUIValues();
            }

        });

        shipRPMMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_DECREASE_SHIP_RPM, event.getRelatedActor());
                setUIValues();
            }
        });

        viewRPMPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_INCREASE_VIEW_RPM, event.getRelatedActor());
                setUIValues();
            }

        });

        viewRPMMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_DECREASE_VIEW_RPM, event.getRelatedActor());
                setUIValues();
            }
        });

        asteroidRPMPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_INCREASE_ASTEROID_RPM, event.getRelatedActor());
                setUIValues();
            }

        });

        asteroidRPMMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_DECREASE_ASTEROID_RPM, event.getRelatedActor());
                setUIValues();
            }
        });

        togglePause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TOGGLE_PAUSE, event.getRelatedActor());
                setUIValues();
            }
        });

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SpaceSpinOut.spaceSpinOut.currentLevel = levelLoaded;
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_MAIN_MENU_SCENE, event.getRelatedActor());
                setUIValues();
            }
        });

    }

}
