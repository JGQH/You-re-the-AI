package Extras;

import Classes.GGame;

public class GSupport {
    public static float scale(float val, float min1, float max1, float min2, float max2){
        float p = (val - min1) / (max1 - min1);
        return min2 + p * (max2 - min2);
    }
    public static void movePlayer(char move){
        switch(move){
            case 'w':
                GGame.position.y -= 1;
                break;
            case 'a':
                GGame.position.x -= 1;
                break;
            case 's':
                GGame.position.y += 1;
                break;
            case 'd':
                GGame.position.x += 1;
                break;
            case 'e':
                GGame.Rotation = (GGame.Rotation == 350) ? 0 : (GGame.Rotation + 10);
                break;
            case 'q':
                GGame.Rotation = (GGame.Rotation == 0) ? 350 : (GGame.Rotation - 10);
                break;
        }
    }
}
