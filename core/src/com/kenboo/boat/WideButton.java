package com.kenboo.boat;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by kenbo on 1/3/2017.
 */

public class WideButton {
    String text;
    BitmapFont font;
    float x;
    float y;
    float width;
    float height;

    Pixmap pix;
    Texture texture;

    float vertices[];
    short triangles[];
    PolygonRegion region;
    PolygonSprite sprite;
    //required to calculate text position
    GlyphLayout layout;
    float fontX;
    float fontY;
    public WideButton( float x, float y, float width, float height, BitmapFont font,String text){
        this.text = text;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.font = font;

        vertices = new float[]{
                0,0,
                width,0,
                width, height,
                0,height
        };
        triangles = new short[]{
                0,1,2,
                2,3,0
        };

        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0x33CC33FF);
        pix.fill();
        texture = new Texture(pix);
        region = new PolygonRegion(new TextureRegion(texture),vertices,triangles);
        sprite = new PolygonSprite(region);
        sprite.setOrigin(width/2, height/2);
        sprite.setPosition(x,y);




        layout = new GlyphLayout();
        layout.setText(font,text);
        fontX = sprite.getX()+ width/2 - layout.width/2;
        fontY = sprite.getY()+ height/2 + layout.height/2;




    }
    public void draw(PolygonSpriteBatch batch){
        sprite.draw(batch);
        font.draw(batch,layout, fontX, fontY);
    }
    public boolean touchDown(float x, float y){
        if(sprite.getBoundingRectangle().contains(x,y)){
            sprite.setScale(0.9f);
            return true;
        }
        return false;
    }
    public boolean touchUp(float x,float y){
        sprite.setScale(1f);
        if(sprite.getBoundingRectangle().contains(x,y)){
            return true;
        }
        return false;
    }
    public void setText(String text){
        this.text = text;
        layout = new GlyphLayout();
        layout.setText(font,text);
        fontX = sprite.getX()+ width/2 - layout.width/2;
        fontY = sprite.getY()+ height/2 + layout.height/2;
    }
    public void dispose(){
        font.dispose();
        pix.dispose();
        texture.dispose();

    }
}
