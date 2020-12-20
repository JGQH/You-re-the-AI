package Game;

//NATIVE
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//CUSTOM
import Classes.GScreen;
import Extras.GVector;
import Classes.GGame;
import Classes.GLabyrinth;

public class Main {
    private static GScreen GameScreen = new GScreen();
    private static final int FPS = 30;

    public static void main(String[] args) {
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
    }

    private static void createGame() {
        JFrame Screen = new JFrame("You're the AI");
        Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.setSize(GScreen.WIDTH, GScreen.HEIGHT);
        Screen.setResizable(false);
        Screen.add(GameScreen);
        Screen.setVisible(true);
        Screen.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyChar()){
                    case 'w':
                        if(GGame.position.y > 0){
                            GGame.position.y -= 1;
                        }
                        break;
                    case 'a':
                        if(GGame.position.x > 0){
                            GGame.position.x -= 1;
                        }
                        break;
                    case 's':
                        if(GGame.position.y < GGame.labyrinth.SIZE * GLabyrinth.SCALER){
                            GGame.position.y += 1;
                        }
                        break;
                    case 'd':
                        if(GGame.position.x < GGame.labyrinth.SIZE * GLabyrinth.SCALER){
                            GGame.position.x += 1;
                        }
                        break;
                    case 'q':
                        GGame.Rotation = (GGame.Rotation == 315) ? 0 : (GGame.Rotation + 45);
                        break;
                    case 'e':
                        GGame.Rotation = (GGame.Rotation == 0) ? 315 : (GGame.Rotation - 45);
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
                GGame.calculateDistances();
                GameScreen.repaint();
			}
        };
        new Timer(delay, evt).start();
    }
}