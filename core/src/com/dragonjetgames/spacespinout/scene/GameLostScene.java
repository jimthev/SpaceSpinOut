package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.GameLostLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

public class GameLostScene extends Scene {
    private GameLostLayer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public GameLostScene() {
        menuLayer = new GameLostLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void iniGame() {
        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
        sso.setViewUp();
        sso.screenRPM = 20;
        sso.stopGame();
    }

}
