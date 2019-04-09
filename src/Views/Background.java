package Views;

import Window.MyWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Background {

    BufferedImage background;

    public  Background(File file)
    {
        backgroundScaling(file);
    }

    public BufferedImage backgroundScaling (File file)
    {
        try {
            background = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Fehler entstanden bim laden der File!!"+ e);
        }
        int imageWidth = background.getWidth();
        int imageHeight = background.getHeight();
        int cropX = (imageWidth - MyWindow.WIDTH) / 2;
        int cropY = (imageHeight - MyWindow.HEIGHT) / 2;

                if(imageWidth < MyWindow.WIDTH || imageHeight < MyWindow.HEIGHT)
                {
                    return background;
                } else
                {

                    return background = background.getSubimage(cropX, cropY, MyWindow.WIDTH, MyWindow.HEIGHT);
                }

    }

}
