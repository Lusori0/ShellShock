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

    public static int WIDTH = 1200;
    public static int HEIGHT = 800;

    static JFrame window;

    public static void main(String args[]){
        new MyWindow();
    }

    public MyWindow() {
        window = new JFrame("SchoolShock");

        window.setSize(WIDTH,HEIGHT);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setVisible(true);

        new MainMenuModel();

        makeNewProfile();

    }


    /*//Test von Layouts --> SCHWIERIGE Nummer ;)
    public static  void layoutTry(JPanel jpanel)
    {
        jpanel.setLayout(new GridLayout(0,1));
    }*/


    public static void setContent(JPanel panel){
        window.setContentPane(panel);
        //layoutTry(panel);
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
