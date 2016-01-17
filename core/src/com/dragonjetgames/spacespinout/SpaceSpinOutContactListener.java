package com.dragonjetgames.spacespinout;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dragonjetgames.spacespinout.spaceobjects.Asteroid;

public class SpaceSpinOutContactListener implements ContactListener {
    SpaceSpinOut spaceSpinOut;

    public SpaceSpinOutContactListener(SpaceSpinOut sso) {
        spaceSpinOut = sso;
    }

    public void bulletAsteroidContact(com.dragonjetgames.spacespinout.spaceobjects.Bullet bullet, com.dragonjetgames.spacespinout.spaceobjects.Asteroid asteroid) {
        asteroid.toDelete = true;
        bullet.toDelete = true;
        spaceSpinOut.currentScore += (20 - (int) asteroid.size);
        spaceSpinOut.shotAsteroid();
//    spaceSpinOut.addAnAsteroid = true;
    }

    public void shipAsteroidContact(com.dragonjetgames.spacespinout.spaceobjects.Asteroid asteroid) {
        asteroid.toDelete = true;
        spaceSpinOut.lostLife();
//    spaceSpinOut.addAnAsteroid = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Filter filterA = fixtureA.getFilterData();
        Filter filterB = fixtureB.getFilterData();
        if (filterA.categoryBits == SpaceSpinOutBox2D.ENTITY_GROUND || filterB.categoryBits == SpaceSpinOutBox2D.ENTITY_GROUND) {
            return;
        }
        if (filterA.categoryBits == SpaceSpinOutBox2D.ENTITY_BULLET || filterB.categoryBits == SpaceSpinOutBox2D.ENTITY_BULLET) {
            Object a = fixtureA.getBody().getUserData();
            Object b = fixtureB.getBody().getUserData();

            com.dragonjetgames.spacespinout.spaceobjects.Asteroid asteroid;
            com.dragonjetgames.spacespinout.spaceobjects.Bullet bullet;

            if (a instanceof com.dragonjetgames.spacespinout.spaceobjects.Asteroid) {
                bulletAsteroidContact((com.dragonjetgames.spacespinout.spaceobjects.Bullet) b, (com.dragonjetgames.spacespinout.spaceobjects.Asteroid) a);
            } else {
                bulletAsteroidContact((com.dragonjetgames.spacespinout.spaceobjects.Bullet) a, (com.dragonjetgames.spacespinout.spaceobjects.Asteroid) b);
            }
            return;

        } else if (filterA.categoryBits == SpaceSpinOutBox2D.ENTITY_PLAYER_SHIP || filterB.categoryBits == SpaceSpinOutBox2D.ENTITY_PLAYER_SHIP) {
            Object a = fixtureA.getBody().getUserData();
            if (a instanceof com.dragonjetgames.spacespinout.spaceobjects.Asteroid) {
                shipAsteroidContact((com.dragonjetgames.spacespinout.spaceobjects.Asteroid) a);
            } else {
                shipAsteroidContact((Asteroid) fixtureB.getBody().getUserData());
            }
            return;
        }
//				System.out.println("apply impulse");
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
