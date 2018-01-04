package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.io.FileNotFoundException;

/**
 * Created by kenbo on 1/4/2017.
 */

public class MenuTouchListener implements InputProcessor {
    MenuScreen menuScreen;
    public MenuTouchListener(MenuScreen menuScreen){
        this.menuScreen = menuScreen;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        menuScreen.camera.unproject(menuScreen.touchVect.set(screenX,screenY,0));
        for (LevelButton lButton : menuScreen.buttons) {
            if (lButton.buttonSprite.getBoundingRectangle().contains(menuScreen.touchVect.x, menuScreen.touchVect.y)) {
                lButton.buttonSprite.setScale(0.9f);
            }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        menuScreen.camera.unproject(menuScreen.touchVect.set(screenX,screenY,0));
        for (LevelButton lButton:menuScreen.buttons){
            lButton.buttonSprite.setScale(1f);
            if (lButton.buttonSprite.getBoundingRectangle().contains(menuScreen.touchVect.x,menuScreen.touchVect.y)) {
                menuScreen.main.lastLevel=lButton.levelNumber;
                try {
                    menuScreen.main.setScreen(new GameScreen(menuScreen.main, lButton.levelNumber));
                }catch(Exception e){

                }
                //press break so the loop ends
                break;
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
