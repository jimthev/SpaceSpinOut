package com.dragonjetgames.spacespinout.layer;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.netthreads.libgdx.scene.Layer;

public class CopyrightLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";

    private Table table;
    private Skin skin;
    private Label copyrightLabel;

    private float magnificationScale = 1.0f;

    public CopyrightLayer(float width, float height) {
        setWidth(width);
        setHeight(height);

        loadTextures();

        buildElements();
    }

    private void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    private void buildElements() {
        copyrightLabel = new Label("Copyright 2016 by Dragonjet Games", skin, URL_LABEL_FONT_SMALL, Color.WHITE);

        copyrightLabel.setFontScale(magnificationScale * 0.4f);

        table = new Table();

        table.setSize(getWidth(), getHeight());

        table.add(copyrightLabel);

        table.bottom();

        table.setFillParent(true);

        table.pack();

        addActor(table);

    }

}
