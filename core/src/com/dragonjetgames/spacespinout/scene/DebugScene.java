package com.dragonjetgames.spacespinout.scene;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.dragonjetgames.spacespinout.layer.DebugMenuLayer;
import com.netthreads.libgdx.scene.Layer;

public class DebugScene extends com.netthreads.libgdx.scene.Scene {

    private DebugMenuLayer menuLayer;
    private Layer backgroundLayer;

    public DebugScene() {
        menuLayer = new DebugMenuLayer(getWidth(), getHeight());
        addLayer(menuLayer);
    }


    public void iniGame() {
        menuLayer.iniGame();
    }


}
