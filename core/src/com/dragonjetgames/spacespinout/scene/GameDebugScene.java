package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.dragonjetgames.spacespinout.SpaceSpinOutEvents;
import com.netthreads.libgdx.director.Director;

import java.util.Iterator;

public class GameDebugScene extends Stage {
    private static final String UI_FILE = "data/uiskin60.json";

    private Table table;
    private Skin skin;
    private TextButton nextButton;

    private Director director;
    InputMultiplexer inputMultiplexer;


    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public GameDebugScene() {
        inputMultiplexer = new InputMultiplexer(this);
        director = Director.getDirector();

        loadTextures();

        buildElements();
    }

    public void addInputProcessors(InputMultiplexer inputMultiplexer) {
        Array<InputProcessor> is = getInputMultiplexer().getProcessors();
        Iterator it = is.iterator();
        while (it.hasNext()) {
            InputProcessor ip = (InputProcessor) it.next();
            inputMultiplexer.addProcessor(ip);
        }
    }

    public void loadTextures() {
        skin = new Skin(Gdx.files.internal(UI_FILE));
    }

    public void buildElements() {
        nextButton = new TextButton("Debug", skin);

//    nextButton.getLabel().setFontScale(1.5f * magnificationScale);

        table = new Table();

        table.setSize(getWidth(), getHeight());

        table.row();
        table.add(nextButton).top().right().padTop(getHeight() / 11.0f).padRight(getHeight() / 16.0f);
        table.top().right();

        table.setFillParent(true);

        table.pack();


        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                director.sendEvent(SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_DEBUG_SCENE, event.getRelatedActor());

            }
        });

        // Add table to view
        addActor(table);

    }
}
