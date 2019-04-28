package Views;

import Window.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUiView extends JPanel implements ActionListener {
    ImageIcon fireImg;
    MyButton fire;

    public  GameUiView()
    {

        erzeugenOverlay();
    }


    private void erzeugenOverlay()
    {
        fire = new MyButton("","Fire Button",fireImg);
        fire.addActionListener(this);
        this.add(fire);
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == fire))
        {

        }
    }
}
