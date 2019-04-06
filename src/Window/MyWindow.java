package Window;

import Model.MainMenuModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class MyWindow{


    //private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH = 1200;
    public static int HEIGHT = 800;
    //rivate Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

    static JFrame window;

    public static void main(String args[]){
        new MyWindow();
    }

    public MyWindow() {
        window = new JFrame("SchoolShock");

        window.setSize(WIDTH,HEIGHT);
        //WIDTH = (int)getWidth(screensize);
        //HEIGHT = (int) getHeighz(screensize);


        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setVisible(true);
        new MainMenuModel();
    }

    public static void setContent(JPanel panel){
        window.setContentPane(panel);
        //panel.setLayout(new FlowLayout());
        window.setVisible(true);
    }

    /*
    public double getHeighz(Dimension d)
    {
        return screensize.getHeight();
    }

    public double getWidth(Dimension d) {
        return screensize.getWidth();
    }*/

}
