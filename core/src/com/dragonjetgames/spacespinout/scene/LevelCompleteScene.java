package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.LevelCompleteLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

public class LevelCompleteScene extends Scene {

    private LevelCompleteLayer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public LevelCompleteScene() {
        menuLayer = new LevelCompleteLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void iniGame() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        sso.screenRPM = 0;
        sso.setViewUp();
        sso.stopGame();
        menuLayer.iniGame();
    }

}
