package Views;

import Model.SettingsModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel implements ActionListener {

    MyButton back,muteMusik;

    ImageIcon backImg,muteMusikImg;

    SettingsModel settingsModel;

    public SettingsView ( SettingsModel settingsModel)
    {
        this.settingsModel = settingsModel;

        muteMusik = new MyButton("","muteMusik",muteMusikImg);
        muteMusik.addActionListener(this);
        //  muteMusikImg = new ImageIcon("");
        // muteMusik.setIcon(muteMusikImg);
        this.add(muteMusik);


        back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
        back.addActionListener(this);
        //   backImg = new ImageIcon("");
        //   back.setIcon(backImg);
        this.add(back);




    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()== back)
        {
            settingsModel.backAction();
        }

        if (e.getSource() == muteMusik)
        {
            settingsModel.muteMusikAction();
        }
    }
}
