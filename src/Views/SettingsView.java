package Views;

import Model.SettingsModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel implements ActionListener {

    MyButton back,muteMusik;

    SettingsModel settingsModel;

    public SettingsView ( SettingsModel settingsModel)
    {
        this.settingsModel = settingsModel;

        back = new MyButton("Back");
        muteMusik = new MyButton("muteMusik");

        back.addActionListener(this);
        muteMusik.addActionListener(this);

        this.add(back);
        this.add(muteMusik);
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
