package com.kenboo.boat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by kenbo on 12/23/2016.
 */

public class Steering {
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
    //use Intger class so it can be null
    Integer touchId;
    float steeringPercentage;


    public Steering(float cameraWidth, float cameraHeight){
        backgroundVertices = new float[]{
                0,0,
                60,0,
                60,5,
                0,5
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

        //set position so it is at the middle bottom of the creen
        handleSprite.setPosition( - handleSprite.getBoundingRectangle().getWidth()/2,
                -cameraHeight/2);
        backgroundSprite.setPosition(-backgroundSprite.getBoundingRectangle().getWidth()/2,
                -cameraHeight/2);

    }
    public void draw(PolygonSpriteBatch batch){
        backgroundSprite.draw(batch);
        handleSprite.draw(batch);

    }
    //called when a finger touches down
    public void setTouched(float x,float y, Integer touchId){
        //bit complicated for multitouch
        //if the touch is inside the bounds, set touched to true and store the touch finger
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
    public void drag(float x,int touchId){
        if(touched && touchId == this.touchId){
            //use rectangle of the sprite. The PolySprite width and height are the size of the textures and are useless.
            //create bounds for the handle
            handleSprite.setX(x);
            if(handleSprite.getX()<backgroundSprite.getX()){
                handleSprite.setX(backgroundSprite.getX());
            }
            if(handleSprite.getX() +handleSprite.getBoundingRectangle().getWidth()> backgroundSprite.getX()+backgroundSprite.getBoundingRectangle().getWidth()){
                handleSprite.setX(backgroundSprite.getX()+backgroundSprite.getBoundingRectangle().getWidth()-handleSprite.getBoundingRectangle().getWidth()) ;
            }
            setSteeringPercentage();

        }
    }
    public void setSteeringPercentage(){
        steeringPercentage = (handleSprite.getX()+handleSprite.getBoundingRectangle().getWidth()/2)/(backgroundSprite.getBoundingRectangle().getWidth()/2-handleSprite.getBoundingRectangle().getWidth()/2);

    }
    public void setCenter(){
        //returns the steering to the center
        handleSprite.setX( - handleSprite.getBoundingRectangle().getWidth()/2);
        setSteeringPercentage();
    }
    public void unTouch(Integer touchId){
        if(touchId == this.touchId) {
            this.touchId = null;
            touched = false;
            setCenter();
        }
    }
    public void dispose(){
        pix.dispose();
        pixTexture.dispose();
    }
}
