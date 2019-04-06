package Window;

import Model.MainMenuModel;

import javax.swing.*;
import java.awt.*;
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


}
