package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

/**
 * Created by kenbo on 12/29/2016.
 */

public class LevelCompleteScreen extends GameOverScreen implements Screen  {
    //same thing as game over screen but the retry button says complete and is unpressable
    public LevelCompleteScreen(Main mainGame){
        super(mainGame);
        retryButton.setText("COMPLETE");

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                camera.unproject(touchVector.set(screenX,screenY,0));
                menuButton.touchDown(touchVector.x, touchVector.y);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                camera.unproject(touchVector.set(screenX,screenY,0));

                if(menuButton.touchUp(touchVector.x, touchVector.y)){
                    main.setScreen(new MenuScreen(main));
                }
                return true;
            }



        });}
    public void render(float delta){
        super.render(delta);
    }
    public void resize(int width, int height){
        super.resize(width,height);
    }
    public void dispose(){
        super.dispose();
    }
    public void show(){
        super.show();
    }
    public void pause(){
        super.pause();
    }
    public void resume(){
        super.resume();
    }
    public void hide(){
        super.hide();
    }
}
