package Window;


import Model.*;


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




    static JFrame window;

    private static String activeUser;

    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Var();
        new MyWindow();

    }



    public MyWindow() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window = new JFrame("ShellShock");



        window.setUndecorated(true);
        //Festlegung der Variablen für spätere Verwendung
        WIDTH =  (int) getWidth();
        HEIGHT = (int) getHeight();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);




        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.addKeyListener(new MyKeys());


        new LogInModel(false);


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
        Var.music.muteMusic();
    }

    //getter und setter

    public static void setContent(JPanel panel){

        window.setContentPane(panel);


        window.setVisible(true);

        window.requestFocusInWindow();
    }

    public static JFrame getFrame(){
        return window;
    }


}
