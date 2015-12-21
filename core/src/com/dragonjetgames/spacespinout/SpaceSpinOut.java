package com.dragonjetgames.spacespinout;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceSpinOut extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera cam;
	double screenRatio;
	int screenAreaX = 0;
	int screenAreaY = 0;
	int screenAreaW = 0;
	int screenAreaH = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("DragonJetMascotGreyOutline.png");
		cam = new OrthographicCamera();
		setupScreenArea();
	}

	public void setupScreenArea() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		double scaledWidth = 1000;
		double screenRatio = ((double) width) / ((double) height);
    if (screenRatio > 1.0) {
      height = 1000;
      width = (int) (screenRatio * 1000);
    } else {
      height = (int)(1000 / screenRatio);
      width = 1000;
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

	@Override
	public void resize(int width, int height) {
		setupScreenArea();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(img, screenAreaX, screenAreaY, screenAreaW, screenAreaH);
		batch.end();
	}
}
