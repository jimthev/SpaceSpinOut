package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.GameCompleteLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

public class GameCompleteScene extends Scene {
    private GameCompleteLayer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public GameCompleteScene() {
        menuLayer = new GameCompleteLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void iniGame() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        sso.setViewUp();
        sso.screenRPM = 20;
        sso.stopGame();
//    menuLayer.iniGame();
    }

}
