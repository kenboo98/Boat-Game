package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import java.awt.Font;

/**
 * Created by kenbo on 1/5/2017.
 */

public class StartScreen implements Screen {
    OrthographicCamera camera;
    float cwidth;
    float cheight;
    WideButton start;

    PolygonSpriteBatch batch;
    BitmapFont font;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    Vector3 touchVector;
    final Main main;
    public StartScreen(Main mainGame){
        this.main = mainGame;
        cwidth = 100;
        cheight = cwidth * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
        camera = new OrthographicCamera(cwidth,cheight);
        camera.position.set(cwidth/2,cheight/2,0);
        camera.update();

        batch = new PolygonSpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(Gdx.graphics.getHeight()/cheight*18);
        font = main.fontGenerator.generateFont(parameter);
        font.getData().setScale(cheight/Gdx.graphics.getHeight());
        start = new WideButton(0,30, 100,20,font, "START");

        touchVector = new Vector3();

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button){
                camera.unproject(touchVector.set(screenX,screenY,0));
                start.touchDown(touchVector.x, touchVector.y);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                camera.unproject(touchVector.set(screenX,screenY,0));

                if(start.touchUp(touchVector.x, touchVector.y)){
                    main.setScreen(new MenuScreen(main));
                }
                return true;
            }
        });
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.486f, 0.839f, 0.910f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        start.draw(batch);
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

    }
}
