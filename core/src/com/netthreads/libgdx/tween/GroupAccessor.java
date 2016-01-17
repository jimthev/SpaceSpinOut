/*
 * -----------------------------------------------------------------------
 * Copyright 2012 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.netthreads.libgdx.tween;

import com.badlogic.gdx.scenes.scene2d.Group;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Lidgx Group tween accessor.
 */
public class GroupAccessor implements TweenAccessor<Group> {
    public static final int POSITION_XY = 1;
    public static final int SCALE_XY = 2;
    public static final int ROTATION = 3;

    @Override
    public int getValues(Group target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;

            case SCALE_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;

            case ROTATION:
                returnValues[0] = target.getRotation();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Group target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case SCALE_XY:
                target.setScaleX(newValues[0]);
                target.setScaleY(newValues[1]);
                break;
            case ROTATION:
                target.setRotation(newValues[0]);
                break;
            default:
                assert false;
        }
    }
}