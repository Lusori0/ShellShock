package Window;

import Model.MainMenuModel;

import javax.swing.*;

public class MyWindow{

    public static int WIDTH = 1000;
    public static int HEIGHT = 1000;

    static JFrame window;

    public static void main(String args[]){
        new MyWindow();
    }

    public MyWindow() {
        window = new JFrame("SchoolShock");

        window.setSize(WIDTH,HEIGHT);

        window.setVisible(true);

        new MainMenuModel();
    }

    public static void setContent(JPanel panel){
        window.setContentPane(panel);
        window.setVisible(true);
    }


}
