import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestClass {
    public static void main(String[] args) throws IOException, UnsupportedFlavorException, AWTException {
        JFrame frame = new JFrame();
        Panel panel = new Panel();
        frame.add(panel);
        panel.setSize(350,100);
        JTextField field = new JTextField();
        field.setText("123456789");
        field.setSize(350,100);
        panel.add(field);
        frame.setSize(350,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
