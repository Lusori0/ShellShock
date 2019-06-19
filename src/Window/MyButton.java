package Window;

import javax.swing.*;
import java.awt.*;


public class MyButton extends JButton {


    public MyButton(String dateiname,String text) {

        // Ausrechnung der Dimersionen
        int buttonWidth = MyWindow.WIDTH/8;
        int buttonHeigth = buttonWidth*2/5;



        this.setBackground(MyWindow.backgroundColor);
        this.setBorder(BorderFactory.createRaisedBevelBorder());


        //Hinweist-Text wir eingefügt
        this.setToolTipText(text);


        ImageIcon imageIcon = new ImageIcon("res/buttons/" + dateiname);


        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
}
    public MyButton(String dateiname,String text,int width,int heigth) {

        // Ausrechnung der Dimersionen
        int buttonWidth = width;
        int buttonHeigth = heigth;



        this.setBackground(MyWindow.backgroundColor);
        this.setBorder(BorderFactory.createRaisedBevelBorder());


        //Hinweist-Text wir eingefügt
        this.setToolTipText(text);


        ImageIcon imageIcon = new ImageIcon("res/buttons/" + dateiname);


        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
    }
}
