package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.ceil;

/**
 * Created by kenbo on 12/26/2016.
 */



public class MenuScreen implements Screen{
    //camera for scaling the menu screen
    OrthographicCamera camera;
    //screen width is 100
    float cwidth;
    float cheight;
    //number of levels for the loop
    final int N_LEVELS = 100;
    //temporary button
    Array<LevelButton> buttons;

    PolygonSpriteBatch batch;

    Vector3 touchVect;
    //get the font generator from the main class
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    Main main;
    float fontUnitSize;
    //this is needed due to needing a gesturelistener and an ordinary touch listener for this class
    InputMultiplexer inputMultiplexer;
    public MenuScreen(Main mainScreen) {
        //set camera width
        cwidth = 100;
        cheight = 100 * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
        camera = new OrthographicCamera(cwidth,cheight);
        camera.position.set(cwidth/2, cheight/2,0);
        camera.update();
        //set up the font

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        //parameter.size sets the font size according to the font unit size
        //font setScale makes sure the unit size is set according to the camera and screen height
        fontUnitSize = 8;
        parameter.size = (int)(Gdx.graphics.getHeight()/cheight*fontUnitSize);
        font = mainScreen.fontGenerator.generateFont(parameter);
        font.getData().setScale(cheight/Gdx.graphics.getHeight());


        buttons = new Array<LevelButton>();
        //have 5 buttons per row. Each button would be a 20x20 square
        //place each button in the appropriate position and place it in the array
        for(int i = 0; i < N_LEVELS;i++){
            buttons.add(new LevelButton((i % 5)*20,cheight-((ceil(i/5)+1)*20),20,20, i+1,font));
        }



        batch = new PolygonSpriteBatch();
        main = mainScreen;
        touchVect = new Vector3();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(new PanListener(this)));
        inputMultiplexer.addProcessor(new MenuTouchListener(this));

        Gdx.input.setInputProcessor(inputMultiplexer);


    }
    public void scrollCamera(float deltaY){
        camera.position.add(0,deltaY*cheight/Gdx.graphics.getHeight(),0);
        if(camera.position.y > cheight/2){
            camera.position.set(cwidth/2, cheight/2,0);
        }
        if(camera.position.y < cheight-(ceil(N_LEVELS/5)+1)*20 + cheight/2){
            camera.position.set(cwidth/2, cheight-(ceil(N_LEVELS/5)+1)*20 + cheight/2,0);
        }
        camera.update();
    }
    @Override
    public void show() {

    }

    public void render(float delta){
        Gdx.gl.glClearColor(0.486f, 0.839f, 0.910f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for(LevelButton button:buttons){
            button.draw(batch);
        }
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

    public void dispose(){
        //use for loop due to nested for loops not working
        for(int i = 0; i<N_LEVELS;i++){
            buttons.get(i).dispose();
        }
        font.dispose();
        batch.dispose();
    }
}
