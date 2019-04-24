package Views;

import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUiView extends JPanel implements ActionListener {
    ImageIcon fireImg;
    MyButton fire;

    public  GameUiView()
    {

    }


    private void erzeugenOverlay()
    {
        fire = new MyButton("","",fireImg);
        fire.addActionListener(this);
    }









    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == fire))
        {

        }
    }
}
