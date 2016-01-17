package com.dragonjetgames.spacespinout.layer;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

public class LevelCompleteLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";

    private Table table;
    private Skin skin;
    private Label titleLabelA;
    private TextButton startButton;

    private float magnificationScale;

    private Director director;

    public LevelCompleteLayer(float width, float height) {
        setWidth(width);
        setHeight(height);

        director = Director.getDirector();

        magnificationScale = 1.0f;

        loadTextures();

        buildElements();
    }

    private void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    public void iniGame() {
        titleLabelA.setText("Level " + SpaceSpinOut.spaceSpinOut.currentLevel + " Complete");
    }

    private void buildElements() {
        // Title
        titleLabelA = new Label("Level Complete", skin, URL_LABEL_FONT, Color.YELLOW);
        titleLabelA.setFontScale(magnificationScale);

        startButton = new TextButton("Next Level", skin);
        startButton.pad(8 * magnificationScale);

        startButton.getLabel().setFontScale(magnificationScale * 1.2f);

        // ---------------------------------------------------------------
        // Table
        // ---------------------------------------------------------------
        table = new Table();

        table.setSize(getWidth(), getHeight());

        table.row();
        table.add(titleLabelA).expandY().expandX();
        table.row();
        table.add(startButton).expandY().expandX();
        table.row();

        table.setFillParent(true);

        table.pack();

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SpaceSpinOut.spaceSpinOut.currentLevel++;
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_GAME_SCENE, event.getRelatedActor());
            }
        });

        addActor(table);
    }

}