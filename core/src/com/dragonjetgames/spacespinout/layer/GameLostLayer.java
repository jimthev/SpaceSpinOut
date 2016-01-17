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

public class GameLostLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";

    private Table table;
    private Skin skin;
    private Label titleLabelA;
    private Label titleLabelB;
    private Label titleLabelC;
    private TextButton startButton;

    private float magnificationScale;

    private Director director;

    public GameLostLayer(float width, float height) {
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
    }

    public void setUIValues() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        titleLabelC.setText("Score:" + sso.currentScore);
    }

    public void act(float delta) {
        super.act(delta);
        setUIValues();
    }

    private void buildElements() {
        // Title
        titleLabelA = new Label("You have failed to", skin, URL_LABEL_FONT, Color.YELLOW);
        titleLabelA.setFontScale(magnificationScale);
        titleLabelB = new Label("SpinOut!", skin, URL_LABEL_FONT, Color.YELLOW);
        titleLabelB.setFontScale(magnificationScale);

        titleLabelC = new Label("Score:" + SpaceSpinOut.spaceSpinOut.currentScore, skin, URL_LABEL_FONT, Color.YELLOW);
        titleLabelC.setFontScale(magnificationScale);

        startButton = new TextButton("Try Again", skin);
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
        table.add(titleLabelB).expandX();
        table.row();
        table.add(titleLabelC).expandX().padTop(getHeight() / 10);
        table.row();
        table.add(startButton).expandY().expandX();
        table.row();

        table.setFillParent(true);

        table.pack();

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SpaceSpinOut.spaceSpinOut.currentLevel = 1;
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_MAIN_MENU_SCENE, event.getRelatedActor());
            }
        });

        addActor(table);
    }
}

