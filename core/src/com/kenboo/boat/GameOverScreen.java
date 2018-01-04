package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * Created by kenbo on 1/2/2017.
 */

public class GameOverScreen implements Screen {
    //screenshot from the past game screen

    Sprite screenshotSprite;
    PolygonSpriteBatch batch;

    WideButton retryButton;
    WideButton menuButton;

    OrthographicCamera camera;

    Vector3 touchVector;
    BitmapFont font;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    final Main main;
    float retryButttonFontSize = 16;
    float menuButtonFontSize = 8;

    public GameOverScreen(Main mainGame){
        this.main = mainGame;
        //set up camra
        float cwidth = 100;
        float cheight = 100 * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
        camera = new OrthographicCamera(cwidth,cheight);
        //set the position so the camera is at 0,0
        camera.position.set(cwidth/2, cheight/2,0);
        camera.update();
        //screenshot sprite
        screenshotSprite = new Sprite(ScreenUtils.getFrameBufferTexture());
        screenshotSprite.setSize(cwidth,cheight);
        screenshotSprite.setColor(Color.LIGHT_GRAY);
        //set up the batch
        batch = new PolygonSpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        //set up font parameters. Linear texture filter for smooth filter
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        //scaled again inside the level button class for some reason.

        parameter.size = (int)(Gdx.graphics.getHeight()/cheight*retryButttonFontSize);
        font = mainGame.fontGenerator.generateFont(parameter);
        font.getData().setScale(cheight/Gdx.graphics.getHeight());

        retryButton = new WideButton(0,70,100,20,font,"RETRY");
        //button that takes you back to the menu
        parameter.size = (int)(Gdx.graphics.getHeight()/cheight*menuButtonFontSize);
        font = mainGame.fontGenerator.generateFont(parameter);
        font.getData().setScale(cheight/Gdx.graphics.getHeight());
        menuButton = new WideButton(0,cheight-10,100,10,font,"MENU");

        touchVector = new Vector3();
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                camera.unproject(touchVector.set(screenX,screenY,0));
                retryButton.touchDown(touchVector.x,touchVector.y);
                menuButton.touchDown(touchVector.x, touchVector.y);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                camera.unproject(touchVector.set(screenX,screenY,0));
                if(retryButton.touchUp(touchVector.x,touchVector.y)){
                    main.setScreen(new GameScreen(main,main.lastLevel));
                }
                if(menuButton.touchUp(touchVector.x, touchVector.y)){
                    main.setScreen(new MenuScreen(main));
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }


        });
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();
        screenshotSprite.draw(batch);
        retryButton.draw(batch);
        menuButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        menuButton.dispose();
        retryButton.dispose();

    }
}
