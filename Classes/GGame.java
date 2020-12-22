package Classes;

import java.util.Collections;
import java.util.Vector;
import Extras.GBoundary;
import Extras.GSupport;
import Extras.GVector;
public class GGame {
    public static final int RAY_COUNT = 64;
    public static final int FOV = 60;
    public static String GAME_STATE;
    public static int MAX_DIST;

    public static GVector position;
    public static float Rotation;
    public static float[] distances;
    public static GVector[] collisions;
    public static GLabyrinth labyrinth;
    public static Vector<GBoundary> boundaries;

    public static void calculateDistances(){
        //Sends RAY_COUNT rays around the position to "see" the walls
        for(int i = 0; i < RAY_COUNT; i++){
            float dir = Rotation + GSupport.scale(i, 0, RAY_COUNT - 1, -FOV / 2, FOV / 2);
            GVector ray = new GVector(dir);
            GVector mpt = getCollision(ray);
            collisions[i] = mpt;

            GVector pt = GVector.diff(mpt, GGame.position);
            distances[i] = pt.magnitude();
            if(distances[i] == 0){ //We crashed into a wall
                GAME_STATE = "LOSE_EASY";
            }
        }
    }

    private static GVector getCollision(GVector ray){
        Vector<GVector> pts = new Vector<GVector>();
        for (GBoundary boundary : boundaries) {
            GVector pt = ray.cast(boundary);
            if(pt != null){
                pts.add(pt);
            }
        }

        return Collections.min(pts);
    }
}
