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
        this.setBackground(MyWindow.backgroundColor);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
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
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
    }
}
