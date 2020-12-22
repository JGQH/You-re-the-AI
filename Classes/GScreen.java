package Classes;

//NATIVE
import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Extras.GBoundary;
import Extras.GSupport;
import Extras.GVector;

public class GScreen extends JPanel{
    private static final long serialVersionUID = 1L;
    // CONSTANTS
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 576; //For 9:16 ratio

    private Font customFont;
    public GScreen(){
        this.setDoubleBuffered(true);
        this.loadFont();
    }
    private void loadFont(){
        try {
            //Font downloaded from: https://www.dafont.com/es/pixelated.font
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/pixelated.ttf"));
        } catch (Exception e) {
            customFont = new Font("Consolas", Font.BOLD, 20);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        switch (GGame.GAME_STATE) {
            case "ONGOING_EASY":
                this.drawEasy(g2d);
                break;
            case "LOSE_EASY":
            case "LOSE_HARD":
                this.drawLose(g2d);
                break;
        }
    }

    private void drawEasy(Graphics2D g2d){
        //===============================================//
        //==================== SONAR ====================//
        //===============================================//
        Image sonar = new ImageIcon("Resources/Sonar.png").getImage();
        g2d.drawImage(sonar, 224, 0, 576, 576, this);

        //(36, 288) -> (540, 288)
        int lastX = 36 + 224; int lastY = 288;
        for (int i = 0; i < GGame.RAY_COUNT; i++) {
            float dis = GGame.distances[i];
            int x = 36 +224 + (int)(504 * (i + 1) / (GGame.RAY_COUNT + 1));
            int y = (int)GSupport.scale(dis, 0, GGame.MAX_DIST, 414, 162); //(576 / 2) +- (540 - 36) / 4

            g2d.drawLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        }
        g2d.drawLine(lastX, lastY, 540 + 224, 288);

        //==================================================//
        //==================== ROTATION ====================//
        //==================================================//
        g2d.drawLine(512, 288, 512 + (int)(252 * Math.cos(GGame.Rotation * Math.PI / 180)), 288 + (int)(252 * Math.sin(GGame.Rotation * Math.PI / 180)));

        //TESTING PURPOSES
        for (GBoundary boundary: GGame.boundaries){
            g2d.drawLine(boundary.x1, boundary.y1, boundary.x2, boundary.y2);
        }
        for(GVector pos: GGame.collisions){
            g2d.drawOval((int)pos.x - 2, (int)pos.y - 2, 4, 4);
        }
        g2d.drawOval((int)GGame.position.x - 2, (int)GGame.position.y - 2, 4, 4);
    }

    private void drawLose(Graphics2D g2d) {
        g2d.setFont(this.customFont.deriveFont(250f));
        g2d.drawString("YOU LOSE", 45, 250);
        g2d.setFont(this.customFont.deriveFont(40f));
        g2d.drawString("PRESS 'M' TO GO BACK TO MENU", 260, 450);
    }
}
