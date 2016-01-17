package com.dragonjetgames.spacespinout.spaceobjects;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutBox2D;

public class PlayerShip extends SpaceObject {
    public float shipRPM = 10;
    float rotationRadiansPerSecond;
    Texture img;

    public Bullet getBullet() {
        return new Bullet(angle, size / 2, SpaceSpinOut.BULLET_HEIGHT, SpaceSpinOut.BULLET_WIDTH);
    }

    public void makeSprite() {
        img = new Texture(SpaceSpinOut.TEXTURE_DIR + SpaceSpinOut.TEXTURE_SPACE_SHIP);
        sprite = new Sprite(img);
        sprite.setSize(size, size);
    }

    public void setRPM(float rpm) {
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
        body.setTransform(0, 0, angle);
        super.act(delta);
    }

    public PlayerShip(float size) {
        super(size);
    }

    public void makeBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = SpaceSpinOut.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0, size / 2);
        vertices[1] = new Vector2(size / 2, -size / 2);
        vertices[2] = new Vector2(-size / 2, -size / 2);
        shape.set(vertices);
//    shape.setAsBox(4, SHIP_GUN_HEIGHT * 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;
        body.setGravityScale(0);
        body.setLinearVelocity(0, 0);
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.categoryBits = SpaceSpinOutBox2D.ENTITY_PLAYER_SHIP;
        filter.maskBits = SpaceSpinOutBox2D.ENTITY_BALL;
        fixture.setFilterData(filter);
        shape.dispose();
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }
}
