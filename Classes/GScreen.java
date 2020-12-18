package Classes;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
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
        Image tesuto = new ImageIcon("Resources/Sonar.png").getImage();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawRect(50, 50, 100, 100);
        g2d.drawImage(tesuto, 0, 0, 576, 576, this);
    }
}
