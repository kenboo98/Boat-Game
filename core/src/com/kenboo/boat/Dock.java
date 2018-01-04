package com.kenboo.boat;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by kenbo on 12/24/2016.
 */

public class Dock {
    //A square where the ship has to dock to complete level
    PolygonSprite dockSprite;
    PolygonRegion region;
    //texture for the color
    Pixmap pix;
    Texture pixTexture;
    float vertices[];
    short triangles[];
    float WIDTH = 25;
    float HEIGHT = 45;
    //throttle sets the amount of power the boat has

    Polygon polygon;

    public Dock(float x, float y, float rotation){
        //vertices of the boat
        vertices = new float[]{
                0,0,
                WIDTH,0,
                WIDTH, HEIGHT,
                0,HEIGHT
        };
        triangles = new short[]{
                0,1,2,
                2,3,0
        };

        //pixel for region
        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0xfffd85ff);
        pix.fill();
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),vertices, triangles);
        dockSprite = new PolygonSprite(region);

        dockSprite.setOrigin(WIDTH/2,HEIGHT/2);
        dockSprite.setPosition(x,y);
        dockSprite.setRotation(rotation);

        polygon = new Polygon(vertices);
        polygon.setPosition(dockSprite.getX(),dockSprite.getY());
        polygon.setOrigin(WIDTH/2,HEIGHT/2);
        polygon.setRotation(rotation);

    }
    public void draw(PolygonSpriteBatch batch){
        dockSprite.draw(batch);


    }
    public boolean isBoatDocked(Polygon boatPolygon){

        float boatVertices[] = boatPolygon.getTransformedVertices();
        for(int i=0; i<boatVertices.length; i += 2){
            if (!dockSprite.getBoundingRectangle().contains(boatVertices[i],boatVertices[i+1])){
                return false;
            }

        }
        return true;

    }
    public void dispose(){
        pix.dispose();
        pixTexture.dispose();
    }
}
