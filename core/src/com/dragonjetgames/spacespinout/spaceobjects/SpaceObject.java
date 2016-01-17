package com.dragonjetgames.spacespinout.spaceobjects;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class SpaceObject {
    public boolean toDelete = false;
    Body body;
    public float angle = 0;
    public float size = 10;

    Sprite sprite;

    public SpaceObject(float size) {
        this.size = size;
        makeBody();
        makeSprite();
    }

    public void act(float delta) {
        if (sprite == null) {
            return;
        }
        sprite.setCenter(body.getPosition().x, body.getPosition().y);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees((float) angle));
    }

    public void draw(Batch batch) {
        if (sprite == null) {
            return;
        }
        sprite.draw(batch);
    }

    public void makeBody() {
    }

    public void makeSprite() {
    }
}
