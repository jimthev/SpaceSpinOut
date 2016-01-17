package com.dragonjetgames.spacespinout.layer;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.netthreads.libgdx.scene.Layer;

public class MainGameLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";

    private Table table;
    private Skin skin;
    private Label titleLabel;
    private Label levelLabel;
    private Label scoreLabel;
    private Label livesLabel;

    private float magnificationScale = 1.0f;

    public MainGameLayer(float width, float height) {
        setWidth(width);
        setHeight(height);

        loadTextures();

        buildElements();
    }

    private void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    public void setUIValues() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        livesLabel.setText("Lives: " + sso.livesLeft);
        scoreLabel.setText("Score: " + sso.currentScore);
        levelLabel.setText("Level " + sso.currentLevel);
    }

    private void buildElements() {
        titleLabel = new Label("Space SpinOut!", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        titleLabel.setFontScale(magnificationScale * 1.2f);
        levelLabel = new Label("Level 1", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        levelLabel.setFontScale(magnificationScale * 0.8f);

        scoreLabel = new Label("Score: 270", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        scoreLabel.setFontScale(magnificationScale * 0.8f);

        livesLabel = new Label("Lives: 1", skin, URL_LABEL_FONT_SMALL, Color.WHITE);
        livesLabel.setFontScale(magnificationScale * 0.8f);

        table = new Table();

        table.setSize(getWidth(), getHeight());

        table.add(titleLabel).left().padLeft(20).padTop(10);
        table.add(levelLabel).right().padRight(50);
        table.row();
        table.add((Actor) null).expand().fill();
        table.add((Actor) null).expand().fill();
        table.row();
        table.add(scoreLabel).left().padLeft(50).padBottom(20);
        table.add(livesLabel).right().padRight(50).padBottom(20);

        table.setFillParent(true);

//    table.debug();

        setUIValues();

        table.pack();

        addActor(table);

    }

}

