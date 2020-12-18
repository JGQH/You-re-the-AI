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

public class Main{
    private static GScreen GameScreen = new GScreen();
    private static final int FPS = 30;

    public static void main(String[] args) {
        createGame();
        startTimer();
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
                System.out.println(e.getKeyChar());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
       });
    }

    private static void startTimer(){
        int delay = 1 / FPS;
        ActionListener evt = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                GameScreen.repaint();
			}
        };
        new Timer(delay, evt).start();
    }
}