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
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;

public class HelpMenuLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";


    private Table table;
    private Skin skin;
    private Label titleLabelA;
    private Label titleLabelB;
    private TextButton backButton;

    private float magnificationScale;

    private TextureCache textureCache;

    /**
     * The one and only director.
     */
    private Director director;

    public HelpMenuLayer(float width, float height) {
        setWidth(width);
        setHeight(height);

        director = Director.getDirector();

        magnificationScale = 1.0f;

        textureCache = TextureCache.getTextureCache();

//		Gdx.input.setCatchBackKey(true);

        loadTextures();

        buildElements();
    }

    private void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    private void buildElements() {
        // Title
        titleLabelA = new Label("Space SpinOut!", skin, URL_LABEL_FONT, Color.YELLOW);

        titleLabelA.setFontScale(magnificationScale * 1.0f);

        // ---------------------------------------------------------------
        // Buttons.
        // ---------------------------------------------------------------
        backButton = new TextButton("Back", skin);

        backButton.getLabel().setFontScale(magnificationScale * 0.9f);


//		startButton.setScale(magnificationScale);
//		standingsButton.setScale(magnificationScale);

        table = new Table();

        table.setSize(getWidth(), getHeight());
        String[] instructions = {"Your ship's isn't reliable.", "One engine randomly shutsdown and", "your nav system overcompensates.",
                "All this happens while you are", "clearing out asteroids", " ", "Tap the screen to fire."};

        table.row();
        table.add(titleLabelA).padTop(getHeight() / 12.0f).padBottom(getHeight() / 12.0f);
        table.row();
//		table.add(startButton).expandX().expandY();
        for (int i = 0; i < instructions.length; i++) {
            Label l = new Label(instructions[i], skin, URL_LABEL_FONT, Color.WHITE);
            l.setFontScale(magnificationScale * 0.8f);
            table.add(l);
            table.row();
        }
        table.add((Actor) null).expand().fill();
        table.row();
//		table.add(standingsButton).expandX().top();
        table.add(backButton).bottom().padBottom(40);
        table.row();
        table.top();

        table.setFillParent(true);

//    table.setDebug(true);
        table.pack();


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_MAIN_MENU_SCENE,
                        event.getRelatedActor());
            }

        });

        addActor(table);

    }
}

