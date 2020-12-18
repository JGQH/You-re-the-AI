package Game;

//NATIVE
import javax.swing.JFrame;

//CUSTOM
import Classes.GScreen;
public class Main{
    public static void main(String[] args) {
        JFrame Screen = new JFrame("You're the AI");
        Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.setSize(GScreen.WIDTH, GScreen.HEIGHT);
        Screen.setVisible(true);
    }
}