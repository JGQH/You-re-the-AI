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
    private static GScreen GameScreen;
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
        Screen.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyChar()){
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
                GGame.calculateDistances();
			}
        };
        new Timer(delay, evt).start();
    }
}