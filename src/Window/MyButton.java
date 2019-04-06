package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButton extends JButton {



    String text;

    public MyButton(String text) {

        this.text = text;

        this.setText(text);

        this.setPreferredSize(new Dimension(MyWindow.WIDTH/4,MyWindow.HEIGHT/10));
    }
}
