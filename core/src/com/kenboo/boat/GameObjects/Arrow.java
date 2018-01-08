package com.kenboo.boat.GameObjects;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by kenbo on 12/31/2016.
 */

public class Arrow {
    final float WIDTH = 5;
    final float HEIGHT = 5;
    //radius, distance from center of the boat to the center of the arrow
    final float RADIUS = 30;
    float[] vertices;
    short[] triangles;
    private PolygonSprite sprite;
    private Pixmap pix;
    private PolygonRegion region;
    private Texture pixTexture;
    public Arrow(){
        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0xfffd85ff);
        pix.fill();
        vertices = new float[]{
                0,0,
                WIDTH, 0,
                WIDTH/2, HEIGHT
        };
        triangles = new short[]{
                0,1,2
        };
        pixTexture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(pixTexture),vertices,triangles);

        sprite = new PolygonSprite(region);
        sprite.setOrigin(WIDTH/2, HEIGHT/2);

    }
    //position of the center of the arrow
    public void setPos(float x, float y){
        sprite.setPosition(x-WIDTH/2+MathUtils.cosDeg(sprite.getRotation()+90)*RADIUS,y-HEIGHT/2 + MathUtils.sinDeg(sprite.getRotation()+90)*RADIUS);
    }
    public void draw(PolygonSpriteBatch batch){
        sprite.draw(batch);

    }
    public void setAngle(float dockX, float dockY){

        float angle = MathUtils.atan2(dockY-sprite.getY(), dockX-sprite.getX())*MathUtils.radiansToDegrees;
        sprite.setRotation(angle-90);

    }
}
