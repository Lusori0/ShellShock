package Window;

import javax.swing.*;
import java.awt.*;


public class MyButton extends JButton {



    String text;
    String dateiname;
    ImageIcon imageIcon;
    int buttonWidth,buttonHeigth;

    public MyButton(String dateiname,String text,ImageIcon imageIcon) {
        // Ausrechnung der Dimersionen
        buttonWidth = MyWindow.WIDTH/10;
        buttonHeigth = buttonWidth*2/5;

        this.setBackground(Color.PINK);
        this.text = text;
        //Hinweist-Text wir eingefügt
        this.setToolTipText(text);
        this.dateiname = dateiname;
        this.imageIcon = imageIcon;
        //Zugriff auf die Images für den Button(If-Abfrage für verschiedeme Betriebssysteme)
        if(System.getProperty("os.name").contains("Win")) {
            imageIcon = new ImageIcon("res\\buttons\\" + dateiname);
        }
        else{
            imageIcon = new ImageIcon("res/buttons/" + dateiname);
        }
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
    }
}
