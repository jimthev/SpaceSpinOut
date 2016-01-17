package com.dragonjetgames.spacespinout.spaceobjects;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dragonjetgames.spacespinout.SpaceSpinOut;

public class StarBackground extends com.dragonjetgames.spacespinout.spaceobjects.SpaceObject {
    float currentV;
    Texture img;

    public StarBackground(float size) {
        super(size);
    }

    public void makeSprite() {
        img = new Texture(SpaceSpinOut.TEXTURE_DIR + SpaceSpinOut.TEXTURE_STAR_BACKGROUND);
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        sprite = new Sprite(img);
        sprite.setSize(size * 2, size * 2);
    }

    public void act(float delta) {
        if (sprite == null) {
            return;
        }
        currentV -= delta * .1;
        if (currentV < -1) {
            currentV += 1;
        }
        sprite.setV(currentV);
        sprite.setV2(currentV + 1);
        sprite.setCenter(0, 0);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees((float) angle));
    }

}
