package Classes;

//NATIVE
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Extras.GSupport;
import Extras.GVector;

public class GScreen extends JPanel{
    private static final long serialVersionUID = 1L;
    // CONSTANTS
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 576; //For 9:16 ratio

    private HashMap<String, Image> resources = new HashMap<>();
    private Vector<GButton> buttons = new Vector<>();

    private Font customFont;
    private Graphics2D screen;
    public GScreen(){
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.loadFont();
        this.loadImages();
    }

    private void loadFont(){
        try {
            //Font downloaded from: https://www.dafont.com/es/pixelated.font
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/pixelated.ttf"));
        } catch (Exception e) {
            customFont = new Font("Consolas", Font.BOLD, 20);
        }
    }

    private void loadImages(){
        resources.put("sonar", new ImageIcon("Resources/easy/sonar.png").getImage());
        resources.put("buttons", new ImageIcon("Resources/Tutorial/buttons.png").getImage());

        buttons.add(new GButton("Resources/Menu/easy_mode.png", 10, 10, 310, 110, "ONGOING_EASY"));
        buttons.add(new GButton("Resources/Menu/hard_mode.png", 10, 130, 310, 110, "ONGOING_HARD"));
        buttons.add(new GButton("Resources/Menu/tutorial.png", 10, 250, 310, 110, "TUTORIAL"));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        screen = (Graphics2D)g;
        switch (GGame.GAME_STATE) {
            case "ONGOING_EASY":
                this.drawEasy();
                break;
            case "ONGOING_HARD":
                this.drawHard();
                break;
            case "LOSE_EASY":
            case "LOSE_HARD":
                this.drawLose();
                break;
            case "MAIN_MENU":
                this.drawMenu();
                break;
            case "TUTORIAL":
                this.drawTutorial();
                break;
        }
    }

    private void drawHard(){
        //===============================================//
        //==================== SONAR ====================//
        //===============================================//
        Image sonar = resources.get("sonar");
        screen.drawImage(sonar, 224, 0, 576, 576, this);

        //(36, 288) -> (540, 288)
        screen.setStroke(new BasicStroke(1.5f));
        screen.setColor(Color.BLACK);
        int lastX = 36 + 224; int lastY = 288;
        for (int i = 0; i < GGame.RAY_COUNT; i++) {
            float dis = GGame.distances[i];
            int x = 36 +224 + (int)(504 * (i + 1) / (GGame.RAY_COUNT + 1));
            int y = (int)GSupport.scale(dis, 0, GGame.MAX_DIST, 414, 162); //(576 / 2) +- (540 - 36) / 4

            screen.drawLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        }
        screen.drawLine(lastX, lastY, 540 + 224, 288);

        //==================================================//
        //==================== ROTATION ====================//
        //==================================================//
        screen.drawLine(512, 288, 512 + (int)(252 * Math.cos(GGame.Rotation * Math.PI / 180)), 288 + (int)(252 * Math.sin(GGame.Rotation * Math.PI / 180)));

        //===============================================//
        //==================== LEVEL ====================//
        //===============================================//
        screen.setColor(Color.WHITE);
        screen.setFont(this.customFont.deriveFont(50f));
        screen.drawString("LEVEL", 0, 50);
        screen.setFont(this.customFont.deriveFont(75f));
        screen.drawString("  " + (GGame.labyrinth.SIZE - 2), 0, 110);
    }

    private void drawEasy(){
        //====================================================//
        //==================== RAYCASTING ====================//
        //====================================================//
        screen.setColor(Color.WHITE);
        for(GVector collision: GGame.collisions){
            int x = (int)GSupport.scale(collision.x, 0, GGame.MAX_DIST,  224, 800);
            int y = (int)GSupport.scale(collision.y, 0, GGame.MAX_DIST, 0, HEIGHT);

            screen.fillOval(x - 2, y - 2, 4, 4);
        }

        screen.setColor(Color.RED);
        int x = (int)GSupport.scale(GGame.position.x, 0, GGame.MAX_DIST,  224, 800);
        int y = (int)GSupport.scale(GGame.position.y, 0, GGame.MAX_DIST, 0, HEIGHT);
        screen.fillOval(x - 3, y - 3, 6, 6);
        
        //===============================================//
        //==================== LEVEL ====================//
        //===============================================//
        screen.setColor(Color.WHITE);
        screen.setFont(this.customFont.deriveFont(50f));
        screen.drawString("LEVEL", 0, 50);
        screen.setFont(this.customFont.deriveFont(75f));
        screen.drawString("  " + (GGame.labyrinth.SIZE - 2), 0, 110);
    }

    private void drawLose() {
        screen.setFont(this.customFont.deriveFont(250f));
        screen.drawString("YOU LOSE", 45, 250);
        screen.setFont(this.customFont.deriveFont(40f));
        screen.drawString("PRESS 'M' TO GO BACK TO MENU", 260, 450);
    }

    private void drawMenu(){
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLoc, this);

        for(GButton btn: buttons){
            if(btn.contains(mouseLoc)){
                this.screen.drawImage(btn.img, btn.x + 10, btn.y + 10, 290, 90, this);
            }else{
                this.screen.drawImage(btn.img, btn.x, btn.y, 310, 110, this);
            }
        }
    }

    private void drawTutorial(){
        screen.setColor(Color.WHITE);
        screen.setFont(this.customFont.deriveFont(60f));
        screen.drawString("These are the only buttons you'll need", 25, 125);

        Image btns = resources.get("buttons");
        screen.drawImage(btns, 362, 188, 300, 200, this);

        screen.drawString("Test them playing in the 'Easy Mode'", 30, 450);
        screen.drawString("Click anywhere to return to menu", 40, 550);
    }
    public void checkClick(Point mouseLoc){
        for(GButton btn: buttons){
            if(btn.contains(mouseLoc)){
                GGame.GAME_STATE = btn.gameState;
                break;
            }
        }
    }

    private class GButton{
        Image img;
        int x, y, width, height;
        String gameState;
        public GButton(String name, int x, int y, int width, int height, String gameState){
            this.img = new ImageIcon(name).getImage();
            this.x = x; this. y = y;
            this.width = width; this.height = height;
            this.gameState = gameState;
        }

        public boolean contains(Point mouseLoc){
            return (this.x < mouseLoc.x) && (mouseLoc.x < (this.x + this.width)) &&
                (this.y < mouseLoc.y) && (mouseLoc.y < (this.y + this.height));
        }
    }
}
