package com.kenboo.boat;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by kenbo on 12/20/2016.
 */

public class Land{
    PolygonSprite landSprite;
    PolygonRegion region;
    //texture for the color
    Pixmap pix;
    Texture pixTexture;
    Polygon polygon;

    public Land(float vertices[], float x, float y){

        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0x33CC33FF);
        pix.fill();
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),vertices, getShort(vertices.length/2));
        landSprite = new PolygonSprite(region);
        landSprite.setX(x);
        landSprite.setY(y);
        polygon = new Polygon(vertices);
        polygon.setPosition(x,y);
    }
    public void draw(PolygonSpriteBatch batch){
        landSprite.draw(batch);

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
