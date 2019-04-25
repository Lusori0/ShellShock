package Window;

import javax.swing.*;
import java.awt.*;


public class MyButton extends JButton {


    public MyButton(String dateiname,String text,ImageIcon imageIcon) {

        // Ausrechnung der Dimersionen
        int buttonWidth = MyWindow.WIDTH/8;
        int buttonHeigth = buttonWidth*2/5;



        this.setBackground(MyWindow.backgroundColor);
        this.setBorder(BorderFactory.createRaisedBevelBorder());


        //Hinweist-Text wir eingefügt
        this.setToolTipText(text);

        //Zugriff auf die Images für den Button(If-Abfrage für verschiedeme Betriebssysteme)

            imageIcon = new ImageIcon("res/buttons/" + dateiname);


        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
    }
}
