package com.dragonjetgames.spacespinout.spaceobjects;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutBox2D;
import com.dragonjetgames.spacespinout.SpaceSpinOutConstants;

public class Asteroid extends SpaceObject {
    public float shipRPM = 10;
    float rotationRadiansPerSecond;
    static Texture img;

    public Asteroid(float size, float rpm) {
        super(size);
        shipRPM = rpm;
        rotationRadiansPerSecond = ((float) Math.PI / 180.0f) * (360.0f * shipRPM) * (1.0f / 60.0f);
    }

    public void act(float delta) {
        angle += rotationRadiansPerSecond * delta;
        if (angle > Math.PI * 2.0) {
            angle -= Math.PI * 2.0;
        }
        if (angle < 0) {
            angle += Math.PI * 2.0;
        }
        body.setTransform(body.getPosition().x, body.getPosition().y, angle);
        super.act(delta);
    }

    public void makeSprite() {
        if (img == null) {
            img = new Texture(SpaceSpinOut.TEXTURE_DIR + SpaceSpinOut.TEXTURE_ASTEROID);
        }
        sprite = new Sprite(img);
        sprite.setSize(size * 2, size * 2);
    }

    public void makeBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, SpaceSpinOut.gameCameraViewPortSize / 2);
        body = SpaceSpinOut.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(SpaceSpinOutConstants.iniAsteroidRadius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.8f;
        body.setGravityScale(0);
//    System.out.println("SpaceSpinOutConstants.iniAsteroidVelocityY:"+SpaceSpinOutConstants.iniAsteroidVelocityY);
        body.setLinearVelocity(0, -SpaceSpinOutConstants.iniAsteroidVelocityY);
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.categoryBits = SpaceSpinOutBox2D.ENTITY_BALL;
        filter.maskBits = SpaceSpinOutBox2D.ENTITY_GROUND | SpaceSpinOutBox2D.ENTITY_BULLET | SpaceSpinOutBox2D.ENTITY_PLAYER_SHIP;
        fixture.setFilterData(filter);
        circle.dispose();
        body.setUserData(this);
    }
}
