package Classes;

//NATIVE
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Extras.GBoundary;

public class GScreen extends JPanel{
    private static final long serialVersionUID = 1L;
    // CONSTANTS
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 576; //For 9:16 ratio

    public GScreen(){
        this.setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        Image sonar = new ImageIcon("Resources/Sonar.png").getImage();
        g2d.drawImage(sonar, 224, 0, 576, 576, this);

        //(36, 288) -> (540, 288)
        int lastX = 36 + 224; int lastY = 288;
        for (int i = 0; i < GGame.RAY_COUNT; i++) {
            float dis = GGame.distances[i];
            int x = 36 +224 + (int)(504 * (i + 1) / (GGame.RAY_COUNT + 1));
            int y = (int)scale(dis, 0, GGame.MAX_DIST, 280, 120);

            g2d.drawLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        }
        g2d.drawLine(lastX, lastY, 540 + 224, 288);

        //TESTING PURPOSES
        for (GBoundary boundary: GGame.boundaries){
            g2d.drawLine(boundary.x1, boundary.y1, boundary.x2, boundary.y2);
        }
        g2d.drawOval((int)GGame.position.x - 2, (int)GGame.position.y - 2, 4, 4);
    }

    private float scale(float val, float min1, float max1, float min2, float max2){
        float p = (val - min1) / (max1 - min1);
        return min2 + p * (max2 - min2);
    }
}
