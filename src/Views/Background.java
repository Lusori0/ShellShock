package Views;

import Window.MyWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Background {

    BufferedImage background;

    public  Background(File file)
    {
        backgroundScaling(file);
    }

    public BufferedImage backgroundScaling (File file) {


        //Versuch den Background Image zu laden
        if (MyWindow.WIDTH <= 1920 || MyWindow.HEIGHT <= 1080) {
            try {
                background = ImageIO.read(file);
            } catch (IOException e) {
                System.out.println("Fehler entstanden bim laden der File!!" + e);
            }
        } else
        {
            try {
                    background = ImageIO.read(new File("res/gameimages/Menübildschirm_big.png"));
            } catch (IOException e) {
                System.out.println("Fehler entstanden bim laden der File!!" + e);
            }
        }

        int imageWidth = background.getWidth();
        int imageHeight = background.getHeight();
        //möglichkeit den Image auf verschiedenen Bildschirmauflösungen korrekt anzuzeigen
        int cropX = (imageWidth - MyWindow.WIDTH) / 2;
        int cropY = (imageHeight - MyWindow.HEIGHT) / 2;

                if(imageWidth < MyWindow.WIDTH || imageHeight < MyWindow.HEIGHT)
                {
                    //Displaygröße größer als Full HD
                    return background = background.getSubimage(cropX,cropY,MyWindow.WIDTH,MyWindow.HEIGHT);

                } else {
                    //Beschneidung von Bildschirmauflösungen von Full HD und kleiner
                    return background = background.getSubimage(cropX, cropY, MyWindow.WIDTH, MyWindow.HEIGHT);
                }

    }

}
