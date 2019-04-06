package Window;

import Model.MainMenuModel;
import Model.Profil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Flow;

public class MyWindow{


    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH ;
    public static int HEIGHT  ;


   static   JFrame window;

    ImageIcon img;

    JLabel bImg;

    public static void main(String args[]){
        new MyWindow();
    }


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
/*
    private void background() {
        //
        img = new ImageIcon("res/buttons/KnopfMultiplayerMetallic1.png");
        bImg.setIcon(img);
        bImg.setSize(WIDTH,HEIGHT);
        window.add(bImg);
        //
    }*/



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
            try{

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/profil.bin"));

                String name = JOptionPane.showInputDialog("Gib deinen Namen ein");

                Profil profil = new Profil(name, 0);
                objectOutputStream.writeObject(profil);
                objectOutputStream.close();

            }

            catch(IOException e){
                System.out.println("Write Fehler: " + e);
            }
        }

    }
}
