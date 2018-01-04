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
 * Created by kenbo on 12/26/2016.
 */

public class LevelButton {
    PolygonSprite buttonSprite;
    Pixmap pix;
    Texture texture;
    PolygonRegion region;

    float vertices[];
    short triangles[];

    int levelNumber;
    final int PADDING = 2;

    BitmapFont font;

    float fontX;
    float fontY;


    //use glyph to calculate center of the text
    GlyphLayout layout;

    public LevelButton(float x, float y, float width, float height, int levelNumber, BitmapFont font){

        vertices = new float[]{
                0+PADDING, 0+PADDING,
                width-PADDING, 0+PADDING,
                width-PADDING, height-PADDING,
                0+PADDING,height-PADDING
        };
        triangles = new short[]{
                0,1,2,
                2,3,0
        };

        pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(0x33CC33FF);
        pix.fill();
        texture = new Texture(pix);

        region = new PolygonRegion( new TextureRegion(texture),vertices,triangles);
        buttonSprite = new PolygonSprite(region);
        buttonSprite.setOrigin(width/2, height/2);
        buttonSprite.setPosition(x,y);

        this.levelNumber = levelNumber;
        this.font = font;

        layout = new GlyphLayout();

        layout.setText(font,Integer.toString(levelNumber));
        fontX = buttonSprite.getX()+ width/2 - layout.width/2;
        fontY = buttonSprite.getY()+ height/2 + layout.height/2;


    }
    public void draw(PolygonSpriteBatch batch){
        buttonSprite.draw(batch);
        font.draw(batch,layout,fontX,fontY);
    }
    public void dispose(){

        pix.dispose();
        texture.dispose();
    }

}
