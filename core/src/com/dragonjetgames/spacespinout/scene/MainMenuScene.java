package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.MainMenuLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

public class MainMenuScene extends Scene {

    private Layer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public MainMenuScene() {
        menuLayer = new MainMenuLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void iniGame() {
//    System.out.println("Setup background and get ready for level 1");
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        sso.screenRPM = 10;
        sso.livesLeft = 3;
        sso.currentScore = 0;
        sso.stopGame();

    }

}