package Views;

import Model.SettingsModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel implements ActionListener {

    MyButton back,muteMusik;

    ImageIcon backImg,muteMusikImg;

    SettingsModel settingsModel;

    public SettingsView ( SettingsModel settingsModel)
    {
        this.settingsModel = settingsModel;

        // Ersellen des Loadouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();



        this.setBackground(MyWindow.backgroundColor);

        muteMusik = new MyButton("","muteMusik",muteMusikImg);
        muteMusik.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(muteMusik,g);




        back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
        back.addActionListener(this);

        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 2;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(back,g);





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
