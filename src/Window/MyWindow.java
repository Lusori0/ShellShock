package Window;

import Model.MainMenuModel;
import Model.Profil;

import Views.Sound;

import Views.CreateProfil;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;


public class MyWindow{


    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH;
    public static int HEIGHT;
    public final static Color backgroundColor = new Color(30,30,30);

    private static Sound music;


    static JFrame window;

    // Image als Hintergrund  C:\Users\Santo\Desktop\Schule\Informatik\ShellShock\res\buttons\KnopfSingleplayerMetallic1.png

    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {new MyWindow();}



    public MyWindow() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window = new JFrame("SchoolShock");

        window.setUndecorated(true);

        WIDTH = (int)getWidth(screensize);
        HEIGHT = (int) getHeight(screensize);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);


        new MainMenuModel();
        music = new Sound();
        makeNewProfile();



    }




    //Ermittlund der Screensize
    public double getWidth(Dimension d)
    {
        return screensize.getWidth();
    }

    public double getHeight(Dimension d)
    {
        return screensize.getHeight();
    }

    public static void stopMusic(){
        music.muteMusic();
    }

    public static void startMusic() {music.startMusic();}




    public static void setContent(JPanel panel){

        window.setContentPane(panel);
        window.setVisible(true);

    }


    public static void makeNewProfile(){

        File file = new File("data/profil.bin");
        if(!file.exists()){

            setContent(new CreateProfil());

        }



    }
}
