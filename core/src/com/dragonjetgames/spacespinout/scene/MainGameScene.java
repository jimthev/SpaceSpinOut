package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.MainGameLayer;
import com.netthreads.libgdx.scene.Layer;

public class MainGameScene extends com.netthreads.libgdx.scene.Scene {

    private MainGameLayer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public MainGameScene() {
        menuLayer = new MainGameLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void act(float delta) {
        super.act(delta);
        setUIValues();
    }

    public void setUIValues() {
        menuLayer.setUIValues();
    }

    public void iniGame() {
//    System.out.println("initialize the game");
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        sso.iniGame();


    }


}
