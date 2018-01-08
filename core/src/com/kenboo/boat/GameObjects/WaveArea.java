package com.kenboo.boat.GameObjects;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by kenbo on 12/28/2016.
 */

public class WaveArea{
    PolygonSprite waveSprite;
    PolygonRegion region;
    //texture for the color
    Pixmap pix;
    Texture pixTexture;
    Polygon polygon;
    float deltaX;
    float deltaY;
        //identical class to the land but with deltaX and deltaY to move the ship
    public WaveArea(float vertices[], float x, float y,float deltaX,float deltaY){

        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0x66ccffff);
        pix.fill();
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),vertices, getShort(vertices.length/2));
        waveSprite = new PolygonSprite(region);
        waveSprite.setX(x);
        waveSprite.setY(y);
        polygon = new Polygon(vertices);
        polygon.setPosition(x,y);

        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    public void draw(PolygonSpriteBatch batch){
        waveSprite.draw(batch);

    }
    public void dispose(){
        pix.dispose();
        pixTexture.dispose();


    }
    private short[] getShort(int nVertices){
        //generate a short array

        short triangle[] = new short[(nVertices-2)*3];
        int counter = 0;
        for(short i = 2;i < nVertices;i++){

            triangle[counter] = (short)(i-1);
            counter++;
            triangle[counter] = i;
            counter++;
            triangle[counter] = 0;
            counter++;

        }
        return triangle;

    }
}
