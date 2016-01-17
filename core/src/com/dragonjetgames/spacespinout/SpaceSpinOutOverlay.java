package com.dragonjetgames.spacespinout;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceSpinOutOverlay {
    SpriteBatch batch;
    OrthographicCamera cam;

    int screenAreaX = 0;
    int screenAreaY = 0;
    int screenAreaW = 0;
    int screenAreaH = 0;

    Texture img;
    Texture imgLeft;
    Texture imgRight;
    Texture imgTop;
    Texture imgBottom;


    public void create() {
        Game g;
        batch = new SpriteBatch();
//		img = new Texture("DragonJetMascotGreyOutline.png");
        img = new Texture("game_area.png");
        imgLeft = new Texture("game_area_left.png");
        imgRight = new Texture("game_area_right.png");
        imgTop = new Texture("game_area_top.png");
        imgBottom = new Texture("game_area_bottom.png");
        cam = new OrthographicCamera();
        setupScreenArea();
    }

    public void setupScreenArea() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int width = screenWidth;
        int height = screenHeight;

        double scaledWidth = 1000;
        double screenRatio = ((double) width) / ((double) height);
        if (screenRatio > 1.0) {
            height = (int) scaledWidth;
            width = (int) (screenRatio * scaledWidth);
        } else {
            height = (int) (scaledWidth / screenRatio);
            width = (int) scaledWidth;
        }

        if (width == height) {
            screenAreaX = 0;
            screenAreaY = 0;
            screenAreaW = width;
            screenAreaH = width;
        } else if (width > height) {
            int screenWHDelta = width - height;
            screenAreaX = screenWHDelta / 2;
            screenAreaY = 0;
            screenAreaW = height;
            screenAreaH = height;
        } else {
            int screenWHDelta = height - width;
            screenAreaX = 0;
            screenAreaY = screenWHDelta / 2;
            screenAreaW = width;
            screenAreaH = width;
        }

        cam.setToOrtho(false, width, height);
    }

    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(imgLeft, screenAreaX - screenAreaW, screenAreaY, screenAreaW, screenAreaH);
        batch.draw(imgTop, screenAreaX, screenAreaY - screenAreaH, screenAreaW, screenAreaH);
        batch.draw(imgBottom, screenAreaX, screenAreaY + screenAreaH, screenAreaW, screenAreaH);
        batch.draw(imgRight, screenAreaX + screenAreaW, screenAreaY, screenAreaW, screenAreaH);
        batch.draw(img, screenAreaX, screenAreaY, screenAreaW, screenAreaH);
        batch.end();
    }
}
