package com.kenboo.boat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;

/**
 * Created by kenbo on 12/22/2016.
 */

public class Throttle {
    PolygonSprite backgroundSprite;
    PolygonRegion region;
    Pixmap pix;
    Texture pixTexture;
    float backgroundVertices[];
    short backgroundTriangles[];
    //vertices for the innerhandle that indicates the amount of throttle
    float handleVertices[];
    short handleTriangles[];
    PolygonSprite handleSprite;

    boolean touched;
    //id of the finger that touched the throttle
    Integer touchId;
    float throttlePercentage;

    public Throttle(float cameraWidth, float cameraHeight){
        //set vertices and triangles

        backgroundVertices = new float[]{
                0,0,
                5,0,
                5,80,
                0,80
        };
        backgroundTriangles  = new short[]{
                0,1,2,
                2,3,0
        };


        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0xf44842ff);
        pix.fill();

        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),backgroundVertices,backgroundTriangles);
        backgroundSprite = new PolygonSprite(region);

        handleVertices = new float[]{
                0, 0,
                10, 0,
                10, 10,
                0, 10

        };
        handleTriangles = backgroundTriangles;

        pix.setColor(Color.WHITE);
        pix.fill();
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),handleVertices,handleTriangles);
        handleSprite = new PolygonSprite(region);

        //set position so it is at the middle
        handleSprite.setPosition(-cameraWidth/2,-backgroundSprite.getBoundingRectangle().getHeight()/2);
        backgroundSprite.setPosition(-cameraWidth/2,-backgroundSprite.getBoundingRectangle().getHeight()/2);


    }
    public void draw(PolygonSpriteBatch batch){
        backgroundSprite.draw(batch);
        handleSprite.draw(batch);

    }
    public void setTouched(float x,float y , Integer touchId){
        //check if user touched
        if (handleSprite.getBoundingRectangle().contains(x, y)) {
            touched = true;
            this.touchId = touchId;
            //if there is a touch outside the box
        }else if(!handleSprite.getBoundingRectangle().contains(x,y)){
            //only set touched to false
            if(this.touchId == touchId){
                touched = false;
            }

        }
    }
    public void drag(float y, int touchId){
        if(touched && touchId == this.touchId){
            //use rectangle of the sprite. The PolySprite width and height are the size of the textures and are useless.

            handleSprite.setY(y);
            if(handleSprite.getY()<backgroundSprite.getY()){
                handleSprite.setY(backgroundSprite.getY());
            }
            if(handleSprite.getY() > backgroundSprite.getY()+backgroundSprite.getBoundingRectangle().getHeight()){
                handleSprite.setY(backgroundSprite.getY()+backgroundSprite.getBoundingRectangle().getHeight()) ;
            }
            setThrottlePercentage();

        }
    }
    public void setThrottlePercentage(){
        //set the throttle percentage according to the position of the throttle
        throttlePercentage = (handleSprite.getY()-backgroundSprite.getY())/backgroundSprite.getBoundingRectangle().getHeight();

    }
    public void unTouched(Integer touchId){
        if(touchId == this.touchId) {
            this.touchId = null;
            touched = false;

        }
    }
    public void dispose(){
        pix.dispose();
        pixTexture.dispose();
    }

}
