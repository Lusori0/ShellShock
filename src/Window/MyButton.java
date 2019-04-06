package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButton extends JButton {



    String text;
    String dateiname;
    ImageIcon imageIcon;

    public MyButton(String dateiname,String text,ImageIcon imageIcon) {

        this.text = text;
        this.setToolTipText(text);
        this.dateiname = dateiname;
        this.imageIcon = imageIcon;
        imageIcon = new ImageIcon("res\\buttons\\"+ dateiname);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(MyWindow.WIDTH/4,MyWindow.HEIGHT/10,Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(MyWindow.WIDTH/4,MyWindow.HEIGHT/10));
    }
}
