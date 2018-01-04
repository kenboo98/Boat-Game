package com.kenboo.boat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by kenbo on 12/25/2016.
 */


public class Main extends Game {
    FreeTypeFontGenerator fontGenerator;
    //store the last level so when resetting, we know what level to go back to
    int lastLevel;
    public void create(){

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("franzo.ttf"));
        setScreen(new StartScreen(this));
    }
    public void render(){
        super.render();
    }
    public void dispose(){

    }
    //over ride parent screen class to dispose textures
    @Override
    public void setScreen(Screen screen){
        if(getScreen() != null) {
            getScreen().dispose();
        }
        super.setScreen(screen);
    }
}
