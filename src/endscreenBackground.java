import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class endscreenBackground extends JPanel {

    BufferedImage img;
    final static String imgPath = "tetrisBackground.jpg";
    endscreenBackground() {
        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(img, 0, 0, null);
    }
}
