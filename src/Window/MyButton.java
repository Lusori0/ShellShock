package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButton extends JButton implements ActionListener {

    ButtonAction buttonAction;

    String text;

    public MyButton(ButtonAction buttonAction,String text) {
        this.buttonAction = buttonAction;

        this.text = text;

        this.setText(text);

        this.setPreferredSize(new Dimension(MyWindow.WIDTH/4,MyWindow.HEIGHT/10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this){
            buttonAction.execute();
        }
    }
}
