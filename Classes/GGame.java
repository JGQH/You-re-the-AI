package Classes;

import java.util.Collections;
import java.util.Vector;
import Extras.GBoundary;
import Extras.GVector;
public class GGame {
    public static final int RAY_COUNT = 8;
    public static GVector position;
    public static float Rotation;
    public static float[] distances;
    public static GVector[] collisions;
    public static GLabyrinth labyrinth;
    public static Vector<GBoundary> boundaries;

    public static void calculateDistances(){
        //Sends 8 rays around the position to "see" the walls
        for(int i = 0; i < RAY_COUNT; i++){
            int dir = i * 360 / RAY_COUNT; 
            GVector ray = new GVector(dir + Rotation);
            GVector pt = getCollision(ray);
            distances[i] = pt.magnitude();
            collisions[i] = pt;
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
