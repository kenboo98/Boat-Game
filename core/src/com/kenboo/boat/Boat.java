package com.kenboo.boat;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by kenbo on 12/20/2016.
 */

public class Boat {
    PolygonSprite boatSprite;
    PolygonRegion region;
    //texture for the color
    Pixmap pix;
    Texture pixTexture;
    float vertices[];
    short triangles[];
    //throttle sets the amount of power the boat has

    float THROTTLE_MAX = 5;
    float throttle;

    //this target speed will be calculated from the throttle
    //the speed will change depending on the target
    float rotateSpeed;
    float targetSpeed;
    float acceleration;
    float speed;
    float drag;

    float deltaX;
    float deltaY;
    //polygon to use for collision
    Polygon polygon;

    //arrow for pointing to the dock
    Arrow arrow;
    float width;
    float height;
    public Boat(float width, float height){
        //vertices of the boat
        this.height = height;
        this.width = width;
        vertices = new float[]{
                0,0,
                width,0,
                width, height*0.75f,
                width/2, height,
                0,height*0.75f
        };
        triangles = new short[]{
                0,1,2,
                2,3,4,
                2,4,0
        };

        //pixel for region
        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0xCC6600FF);
        pix.fill();
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),vertices, triangles);
        boatSprite = new PolygonSprite(region);

        boatSprite.setOrigin(width/2,height/2);
        throttle = 0f;
        drag = 0.005f;
        speed = 0;
        polygon = new Polygon(vertices);
        polygon.setPosition(boatSprite.getX(),boatSprite.getY());
        polygon.setOrigin(width/2,height/2);
        arrow = new Arrow();
    }
    public void draw(PolygonSpriteBatch batch){
        boatSprite.draw(batch);
        arrow.draw(batch);


    }
    public void update(float deltaTime){
        targetSpeed = (float)Math.sqrt(throttle)*10;
        acceleration = 0.2f;
        if(speed < targetSpeed){
            speed += acceleration;
        }else{
            speed -= speed*drag;
        }
        //rotate proportionally ro speed
        boatSprite.setRotation(boatSprite.getRotation()+ rotateSpeed*speed*deltaTime);
        //Some math to get the delta x y from the rotation. Add 90 degs because the sprite is pointing up and 0deg is right
        deltaX = MathUtils.cosDeg(boatSprite.getRotation()+90)*speed*deltaTime;
        deltaY = MathUtils.sinDeg(boatSprite.getRotation()+90)*speed*deltaTime;
        boatSprite.setY(boatSprite.getY()+deltaY);
        boatSprite.setX(boatSprite.getX()+deltaX);
        polygon.setPosition(boatSprite.getX(),boatSprite.getY());
        polygon.setRotation(boatSprite.getRotation());

        arrow.setPos(boatSprite.getX()+ width/2, boatSprite.getY() + height/2);

    }

    public void setThrottle(float throttlePercentage){

    //function to set the throttle percentage from the throttle UI
        throttle = throttlePercentage*THROTTLE_MAX;
    }

    public void setSteering(float steeringPercentage){
        rotateSpeed = steeringPercentage * -1;
    }

    public void dispose(){
        pix.dispose();
        pixTexture.dispose();


    }
    public void reset(){
        //resets the boat to the start point
        boatSprite.setX(-10);
        boatSprite.setY(0);
        boatSprite.setRotation(0);

        polygon.setPosition(0,-10);
        polygon.setRotation(0);
    }
    public void translate(float x,float y){
        boatSprite.translate(x,y);
        polygon.translate(x,y);

    }

}
