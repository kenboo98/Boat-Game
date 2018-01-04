package com.kenboo.boat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class GameScreen implements Screen {

	OrthographicCamera camera;
    OrthographicCamera uiCamera;
	PolygonSpriteBatch batch;
	int WORLD_WIDTH = 1000;
	int WORLD_HEIGHT = 1000;
	//camera width and height
	float cheight;
	float cwidth;

    MapAssets mapAssets;
	Boat boat;
    Throttle throttle;
    Steering steering;

    Vector3 touchVector;

    //game structure controller
    Main main;


	public GameScreen(Main main, int levelNum) {
		batch = new PolygonSpriteBatch();
		//set up the camera
		cwidth = 100;
		cheight = 100 * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		camera = new OrthographicCamera(cwidth,cheight);

        //set up the boat
		boat = new Boat(20,40);
        //place the center of the boat in the center of the map
        boat.translate(-10,0);
        //mapAssests handles loading of the level and collision with the land
        mapAssets = new MapAssets(levelNum);

        uiCamera = new OrthographicCamera(cwidth,cheight);
        throttle = new Throttle(cwidth,cheight);
        steering = new Steering(cwidth,cheight);
        touchVector = new Vector3();

        this.main = main;
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown (int x, int y, int pointer, int button) {
                uiCamera.unproject(touchVector.set(x,y,0));

                throttle.setTouched(touchVector.x, touchVector.y,pointer);
                steering.setTouched(touchVector.x, touchVector.y,pointer);

                return true;
            }

            public boolean touchUp (int x, int y, int pointer, int button) {
                steering.unTouch(pointer);
                throttle.unTouched(pointer);
                boat.setSteering(steering.steeringPercentage);

                return true;
            }

            public boolean touchDragged (int x, int y, int pointer) {
                uiCamera.unproject(touchVector.set(x,y,0));
                throttle.drag(touchVector.y,pointer);
                steering.drag(touchVector.x,pointer);
                boat.setThrottle(throttle.throttlePercentage);
                boat.setSteering(steering.steeringPercentage);
                return true;
            }

        });


	}

	@Override
	public void render (float delta) {
        Gdx.gl.glClearColor(0.486f, 0.839f, 0.910f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //update all the obejcts
        boat.update(delta);
        boat.arrow.setAngle(mapAssets.dock.dockSprite.getX(), mapAssets.dock.dockSprite.getY());
        mapAssets.waveCollision(boat, delta);

        //move camera to the boat sprite's coordinates camera
        updateCameraPos();
        camera.update();

        //do all the drawing
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        mapAssets.draw(batch);
        boat.draw(batch);
        batch.end();

        //draw UI on top with the uiCamera
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        throttle.draw(batch);
        steering.draw(batch);
        batch.end();

        //if collision with land, go to the game over screen
        if (mapAssets.landCollision(boat)) {
            boat.reset();

            main.setScreen(new GameOverScreen(main));
        }
        //if the boat is docked, exit app
        if (mapAssets.boatDocked(boat)){
           main.setScreen(new LevelCompleteScreen(main));
        }


	}
	
	@Override
	public void dispose () {
        mapAssets.dispose();
        boat.dispose();
        throttle.dispose();
        steering.dispose();
		batch.dispose();

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 100f;
		camera.viewportHeight = 100f * height/width;
        updateCameraPos();
		camera.update();
	}

    public void updateCameraPos(){
        //camera coordinates are at the center of the camera

        camera.position.set(boat.boatSprite.getX(),boat.boatSprite.getY(),0);
    }

    public void show(){

    }
    public void pause(){

    }
    public void resume(){

    }
    public void hide(){

    }
}
