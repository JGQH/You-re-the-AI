package Game;

//NATIVE
import javax.swing.JFrame;

//CUSTOM
import Classes.GScreen;
public class Main{
    private static GScreen GameScreen = new GScreen();
    public static void main(String[] args) {
        JFrame Screen = new JFrame("You're the AI");
        Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.setSize(GScreen.WIDTH, GScreen.HEIGHT);
        Screen.setResizable(false);
        Screen.add(GameScreen);
        Screen.setVisible(true);
    }
}