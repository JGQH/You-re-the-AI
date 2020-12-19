package Classes;

//NATIVE
import java.awt.*;
import javax.swing.JPanel;

import Extras.GBoundary;
import Extras.GVector;

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
        
        for (GBoundary boundary: GGame.boundaries){
            g2d.drawLine(boundary.x1, boundary.y1, boundary.x2, boundary.y2);
        }
        for (int i = 0; i < GGame.RAY_COUNT; i++) {
            GVector collision = GGame.collisions[i];
            g2d.drawString(Float.toString(collision.x), 70, 70 + 20 * i);
            g2d.drawString(Float.toString(collision.y), 150, 70 + 20 * i);
            g2d.drawOval((int)collision.x - 2, (int)collision.y - 2, 4, 4);
        }
    }
}
