package Extras;

import Classes.GGame;

public class GVector implements Comparable<GVector>{
    public float x, y;

    //INITIALIZER METHODS
    public GVector(float _x, float _y){
        this.x = _x;
        this.y = _y;
    }
    public GVector(float rotation){
        double radian = Math.PI * rotation / 180;
        this.x = (float)Math.cos(radian);
        this.y = (float)Math.sin(radian);
    }

    //METHODS
    public float magnitude(){
        return (float)Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public GVector cast(GBoundary wall){
        //https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
        //SETTING VARIABLES
        int x1 = wall.x1; int y1 = wall.y1; int x2 = wall.x2; int y2 = wall.y2;
        float x3 = GGame.position.x; float y3 = GGame.position.y; float x4 = x3 + this.x; float y4 = y3 + this.y;

        //CALCULATING DENOMINATOR
        final float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if(denominator == 0) return null;

        //CALCULATING t AND u
        final float t =  ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4))/denominator;

        final float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3))/denominator;
        if((0 <= t) && (t <= 1) && (0 <= u)){
            float _x = x1 + t * (x2 - x1);
            float _y = y1 + t * (y2 - y1);
            return new GVector(_x, _y);
        }
        return null;
    }

    public int compareTo(GVector vec){
        GVector vec1 = diff(this, GGame.position);
        GVector vec2 = diff(vec, GGame.position);
        return (int)(vec1.magnitude() - vec2.magnitude());
    }

    //OPERATORS
    public static GVector diff(GVector vec1, GVector vec2){
        return new GVector(vec1.x - vec2.x, vec1.y - vec2.y);
    }
}
