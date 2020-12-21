package Extras;

public class GSupport {
    public static float scale(float val, float min1, float max1, float min2, float max2){
        float p = (val - min1) / (max1 - min1);
        return min2 + p * (max2 - min2);
    }
}
