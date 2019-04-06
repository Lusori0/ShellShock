package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButton extends JButton {



    String text;
    String dateiname;
    ImageIcon imageIcon;
    int buttonWidth,buttonHeigth;

    public MyButton(String dateiname,String text,ImageIcon imageIcon) {
        buttonWidth = MyWindow.WIDTH/10;
        buttonHeigth = buttonWidth*2/5;
        this.setBackground(Color.PINK);
        this.text = text;
        this.setToolTipText(text);
        this.dateiname = dateiname;
        this.imageIcon = imageIcon;
        if(System.getProperty("os.name").contains("Win")) {
            imageIcon = new ImageIcon("res\\buttons\\" + dateiname);
        }
        else{
            imageIcon = new ImageIcon("res/buttons/" + dateiname);
        }
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
    }
}
