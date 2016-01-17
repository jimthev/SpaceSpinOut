package com.dragonjetgames.spacespinout.spaceobjects;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.dragonjetgames.spacespinout.SpaceSpinOut;
import com.dragonjetgames.spacespinout.SpaceSpinOutBox2D;

public class Bullet extends SpaceObject {
    Texture spriteSheet;
    TextureRegion[] spriteSheetFrames;
    static Animation spriteAnimation;
    float animationTime = 0;
    int lastFrame = 96;
    int loopFrame = 26;
    float frameDuration = 0.015f;
    float startingAngle = 0.0f;
    static Texture img;

    public Bullet(float angle, float size, float height, float width) {
        super(size);
        startingAngle = angle;
        makeBody(angle, height, size, width);
        makeSprite(width);
    }

    public void act(float delta) {
        super.act(delta);

        animationTime += delta;

        if (lastFrame > 0) {
            if (animationTime > frameDuration * lastFrame) {
                animationTime -= frameDuration * lastFrame;
                animationTime += frameDuration * loopFrame;
            }
        }

    }

    public void draw(Batch batch) {
        if (sprite == null) {
            return;
        }
        // sprite.draw(batch);

        TextureRegion currentFrame = spriteAnimation.getKeyFrame(animationTime, true);

//    for (int i = 0; i < spriteSheetFrames.length; i++) {
//      if (currentFrame == spriteSheetFrames[i]) {
//        System.out.println("frame:"+i);
//      }
//    }

//    batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
//    System.out.println("startingAngle:"+startingAngle);
        batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1.0f, 1.0f, (float) Math.toDegrees(startingAngle));

    }

    public void makeSprite(float width) {
        int sheetRows = 8;
        int sheetColumns = 16;

        if (spriteAnimation == null) {
            spriteSheet = new Texture(SpaceSpinOut.TEXTURE_DIR + SpaceSpinOut.TEXTURE_FIREBALL_SHEET);
            TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / sheetColumns, spriteSheet.getHeight() / sheetRows);              // #10
            spriteSheetFrames = new TextureRegion[lastFrame];
            int index = 0;
            for (int j = 0; j < sheetColumns; j++) {
                for (int i = 0; i < sheetRows; i++) {
                    if (index >= lastFrame) {
                        break;
                    }
                    spriteSheetFrames[index++] = tmp[i][j];
                }
            }
            spriteAnimation = new Animation(frameDuration, spriteSheetFrames);
        }

        animationTime = 0f;

        if (img == null) {
            img = new Texture(SpaceSpinOut.TEXTURE_DIR + SpaceSpinOut.TEXTURE_FIREBALL);
        }
        sprite = new Sprite(img);
        sprite.setSize(width, size);

    }

    public void makeBody(float angle, float height, float size, float width) {
        float dx = -(float) Math.sin(angle);
        float dy = (float) Math.cos(angle);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(height * dx, height * dy);
        body = SpaceSpinOut.world.createBody(bodyDef);

        float bulletVelocity = 100;

        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-width / 2, size / 2);
        vertices[1] = new Vector2(width / 2, size / 2);

        this.angle = angle;
        vertices[2] = new Vector2(width / 2, -size / 2);
        vertices[3] = new Vector2(-width / 2, -size / 2);
        shape.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.8f;
        fixtureDef.isSensor = true;
        body.setGravityScale(0);
        body.setLinearVelocity(dx * bulletVelocity, dy * bulletVelocity);
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.categoryBits = SpaceSpinOutBox2D.ENTITY_BULLET;
        filter.maskBits = SpaceSpinOutBox2D.ENTITY_BALL;
        fixture.setFilterData(filter);
        shape.dispose();
        body.setUserData(this);
        body.setTransform(0, 0, angle);
    }
}
