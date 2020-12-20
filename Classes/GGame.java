package Classes;

import java.util.Collections;
import java.util.Vector;
import Extras.GBoundary;
import Extras.GVector;
public class GGame {
    public static final int RAY_COUNT = 32;
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
            int dir = i * 360 / RAY_COUNT; 
            GVector ray = new GVector(dir + Rotation);
            GVector mpt = getCollision(ray);
            GVector pt = GVector.diff(mpt, GGame.position);
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
