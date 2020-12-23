package Game;

//NATIVE
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;

//CUSTOM
import Classes.GScreen;
import Extras.GSupport;
import Extras.GVector;
import Classes.GGame;
import Classes.GLabyrinth;

public class Main {
    private static GScreen GameScreen;
    private static final int FPS = 30;

    public static void main(String[] args) {
        GGame.GAME_STATE = "ONGOING_EASY";
        prepareGame();
        createGame();
        startTimer();
    }

    private static void prepareGame() {
        GGame.Rotation = 0;
        GGame.position = new GVector(1, 1);
        GGame.distances = new float[GGame.RAY_COUNT];
        GGame.collisions = new GVector[GGame.RAY_COUNT];

        GGame.labyrinth = new GLabyrinth(3);
        GGame.calculateDistances();
    }

    private static void createGame() {
        JFrame Screen = new JFrame("You're the AI");
        Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.setSize(GScreen.WIDTH + 16, GScreen.HEIGHT + 39);
        Screen.setResizable(false);

        GameScreen = new GScreen();
        Screen.add(GameScreen);
        Screen.setVisible(true);
        Screen.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(GGame.GAME_STATE == "MAIN_MENU"){
                    Point mouseLoc = new Point(e.getX(), e.getY() - 39);
                    GameScreen.checkClick(mouseLoc);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Screen.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                //If the game is at a "LOSE" state, you cannot do anything (yet)
                char key = e.getKeyChar();
                switch(GGame.GAME_STATE){
                    case "ONGOING_EASY":
                    case "ONGOING_HARD":
                        GSupport.movePlayer(key);
                        break;
                    case "LOSE_EASY":
                    case "LOSE_HARD":
                        if(key == 'm'){
                            GGame.GAME_STATE = "MAIN_MENU";
                            prepareGame();
                        }
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
       });
    }

    private static void startTimer(){
        int delay = 1 / FPS;
        ActionListener evt = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                GameScreen.repaint();
                if(GGame.GAME_STATE == "ONGOING_EASY" || GGame.GAME_STATE == "ONGOING_HARD"){
                    GGame.calculateDistances();
                }
			}
        };
        new Timer(delay, evt).start();
    }
}