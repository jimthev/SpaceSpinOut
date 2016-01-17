package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.layer.CopyrightLayer;
import com.dragonjetgames.spacespinout.layer.HelpMenuLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

public class HelpScene extends Scene {

    private HelpMenuLayer menuLayer;
    private Layer backgroundLayer;
    private Layer copyrightLayer;

    public HelpScene() {
        menuLayer = new HelpMenuLayer(getWidth(), getHeight());
        addLayer(menuLayer);

        copyrightLayer = new CopyrightLayer(getWidth(), getHeight());
        addLayer(copyrightLayer);
    }

    public void iniGame() { // only access to this page is from Main Menu
//        SpaceSpinOut sso = SpaceSpinOut.spaceSpinOut;
//        sso.pauseGame();
    }
}