package Window;

import Model.MainMenuModel;
import Model.Profil;
import Views.CreateProfil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;


public class MyWindow{


    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH;
    public static int HEIGHT;
    public final static Color backgroundColor = new Color(30,30,30);

    static JFrame window;

    public static void main(String args[]){new MyWindow();}



    public MyWindow() {
        window = new JFrame("SchoolShock");

        window.setUndecorated(true);

        WIDTH = (int)getWidth(screensize);
        HEIGHT = (int) getHeight(screensize);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);


        new MainMenuModel();
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
