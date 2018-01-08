package com.kenboo.boat.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by kenbo on 12/25/2016.
 */

public class MapAssets {
    public Dock dock;
    //all the json classes needed to load the data
    //highest level json value
    JsonValue jsonFile;
    JsonValue.JsonIterator islandsJson;
    JsonValue.JsonIterator wavesJson;
    //all the assets we need to load. The islands are in array format
    Array<Land> islands;
    Array<WaveArea> waves;

    public MapAssets(int level){
        islands = new Array<Land>();
        //get the highest level json value
        jsonFile = new JsonReader().parse(Gdx.files.internal("level" + Integer.toString(level)+".json"));
        //get the json data for the islands as an iterator
        islandsJson = jsonFile.get("islands").iterator();
        //convert all the json file islands to land objects
        for(JsonValue island:islandsJson) {
            float vertices1[] = island.get("vertices").asFloatArray();

            islands.add(new Land(vertices1, island.get("x").asFloat(),island.get("y").asFloat()));
        }
        //get the data for all the waves
        waves = new Array<WaveArea>();
        wavesJson = jsonFile.get("waves").iterator();
        for(JsonValue wave :wavesJson) {
            float vertices1[] = wave.get("vertices").asFloatArray();



            waves.add(new WaveArea(vertices1, wave.get("x").asFloat(),wave.get("y").asFloat(),wave.get("deltaX").asFloat(),
            wave.get("deltaY").asFloat()));
        }

        //get the data for the dock from the json File
        dock = new Dock(jsonFile.get("dock").get("x").asFloat(),jsonFile.get("dock").get("y").asFloat(),jsonFile.get("dock").get("rotation").asFloat());

    }
    public void draw(PolygonSpriteBatch batch){
        dock.draw(batch);
        //draw each island in the islands array

        for(WaveArea wave: waves){
            wave.draw(batch);
        }
        for(Land aLand: islands){
            aLand.draw(batch);
        }

    }
    public boolean landCollision(Boat boat){
        for(Land aLand:islands){
            //if(aLand.landSprite.getBoundingRectangle().overlaps(boat.boatSprite.getBoundingRectangle())){
            if(Intersector.overlapConvexPolygons(aLand.polygon, boat.polygon)){
                return true;
            }


            //}
        }
        return false;
    }
    public void waveCollision(Boat boat,float deltaTime){
        for(WaveArea aWave:waves){
            //if(aLand.landSprite.getBoundingRectangle().overlaps(boat.boatSprite.getBoundingRectangle())){
            if(Intersector.overlapConvexPolygons(aWave.polygon, boat.polygon)){
                    boat.translate(aWave.deltaX*deltaTime, aWave.deltaY*deltaTime);
            }
    }}

    public boolean boatDocked(Boat boat){
        return dock.isBoatDocked(boat.polygon);
    }
    public void dispose(){
        for(Land aLand: islands){
            aLand.dispose();
        }
        for(WaveArea wave: waves){
            wave.dispose();
        }
        dock.dispose();
    }
}
