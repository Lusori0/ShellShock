package Window;


import Model.*;
import Views.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;



public class MyWindow{


    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH;
    public static int HEIGHT;
    public final static Color backgroundColor = new Color(30,30,30);


    private static Sound music;

    static JFrame window;

    private static String activeUser;


    // Image als Hintergrund  C:\Users\Santo\Desktop\Schule\Informatik\ShellShock\res\buttons\KnopfSingleplayerMetallic1.png

    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Var();
        new MyWindow();
    }



    public MyWindow() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window = new JFrame("SchoolShock");
        window.addKeyListener(new MyKeys());



        window.setUndecorated(true);
        //Festlegung der Variablen für spätere Verwendung
        WIDTH = (int)getWidth();
        HEIGHT = (int) getHeight();

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);


        music = new Sound();
        new LogInModel();
        //Niels Dawumms
        // new GameModel();



    }




    //Ermittlund der Screensize
    public double getWidth()
    {
        return screensize.getWidth();
    }

    public double getHeight()
    {
        return screensize.getHeight();
    }

    public static void muteMusic(){
        music.muteMusic();
    }

    //getter und setter

    public static String getActiveUser(){
        return activeUser;
    }

    public static void setActiveUser(String u){
        activeUser = u;
    }




    public static void setContent(JPanel panel){

        window.setContentPane(panel);


        window.setVisible(true);

    }


}
