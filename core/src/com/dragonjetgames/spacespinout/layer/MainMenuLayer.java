package com.dragonjetgames.spacespinout.layer;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

public class MainMenuLayer extends Layer {
    private static final String UI_FILE = "data/uiskin60.json";
    private static final String URL_LABEL_FONT = "large-font";
    private static final String URL_LABEL_FONT_SMALL = "default-font";


    private Table table;
    private Table tableMascot;
    private Image mascotLabel;
    private Skin skin;
    private Label titleLabelA;
    private Label titleLabelB;
    private TextButton startButton;
    private TextButton standingsButton;
    private TextButton aboutButton;

    private float magnificationScale;

    private TextureCache textureCache;

    /**
     * The one and only director.
     */
    private Director director;

    public MainMenuLayer(float width, float height) {
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
        titleLabelA = new Label("Space", skin, URL_LABEL_FONT, Color.YELLOW);
        titleLabelB = new Label("SpinOut!", skin, URL_LABEL_FONT, Color.YELLOW);

        titleLabelA.setFontScale(magnificationScale * 1.2f);
        titleLabelB.setFontScale(magnificationScale * 1.2f);

        // ---------------------------------------------------------------
        // Buttons.
        // ---------------------------------------------------------------
        startButton = new TextButton("Play", skin);
//    standingsButton = new TextButton("Standings", skin);
        startButton.pad(8 * magnificationScale);
        aboutButton = new TextButton("Help", skin);

        startButton.getLabel().setFontScale(magnificationScale * 1.8f);
//    standingsButton.getLabel().setFontScale(magnificationScale);
        aboutButton.getLabel().setFontScale(magnificationScale * 0.9f);

        TextureDefinition definition = textureCache.getDefinition(SpaceSpinOut.TEXTURE_MASCOT);
        TextureRegion textureRegion = textureCache.getTexture(definition);

        mascotLabel = new Image(new TextureRegionDrawable(textureRegion), Scaling.fillX, Align.bottom);

//		startButton.setScale(magnificationScale);
//		standingsButton.setScale(magnificationScale);

        table = new Table();

        table.setSize(getWidth(), getHeight());

        table.row();
        table.add(titleLabelA).padTop(getHeight() / 12.0f).padBottom(getHeight() / 125.0f);
        table.row();
        table.add(titleLabelB).padBottom(getHeight() / 15.0f);
        table.row();
//		table.add(startButton).expandX().expandY();
        table.add(startButton).padBottom(getHeight() / 18.0f);
        table.row();
        table.add((Actor) null).expand().fill();
        table.row();
//		table.add(standingsButton).expandX().top();
        table.add(aboutButton).bottom().padBottom(40);
        table.row();
//		table.add(aboutButton).expandY().expandX();
        table.top();

        table.setFillParent(true);

//    table.setDebug(true);
        table.pack();

        tableMascot = new Table();

        tableMascot.setSize(getWidth(), getHeight());

        tableMascot.row();
        tableMascot.add(mascotLabel).expandX().bottom().padBottom(getHeight() / 10.0f);
        tableMascot.bottom();

        tableMascot.setFillParent(true);

        tableMascot.pack();


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_GAME_SCENE,
                        event.getRelatedActor());
            }

        });

        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TRANSITION_TO_HELP_SCENE,
                        event.getRelatedActor());
            }

        });

//    standingsButton.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        director.sendEvent(SpaceSpinOutEvents.EVENT_SHOW_STANDINGS,
//                event.getRelatedActor());
//      }
//    });

        addActor(tableMascot);
        addActor(table);

    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}