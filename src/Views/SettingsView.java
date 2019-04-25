package Views;

import Model.SettingsModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

public class SettingsView extends JPanel implements ActionListener, ChangeListener {

    MyButton back,muteMusik;
    JSlider musicbar;

    ImageIcon backImg,muteMusikImg;

    SettingsModel settingsModel;

    public SettingsView ( SettingsModel settingsModel)
    {
        this.settingsModel = settingsModel;
        this.setBackground(MyWindow.backgroundColor);

        erzeugenOverlay();

    }

    private void erzeugenOverlay()
    {

        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Slidebar test Anfang
            musicbar = new JSlider(SwingConstants.HORIZONTAL,0,100,100);
            musicbar.addChangeListener(this);
            musicbar.setMajorTickSpacing(10);
            musicbar.setPaintTicks(true);
            musicbar.setSnapToTicks(true);
            //Test den Slider zu beschriften
            Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
            labelTable.put(0, new JLabel("0%"));
            labelTable.put(10, new JLabel("10%"));
            labelTable.put(20, new JLabel("20%"));
            labelTable.put(30, new JLabel("30%"));
            labelTable.put(40, new JLabel("40%"));
            labelTable.put(50, new JLabel("50%"));
            labelTable.put(60, new JLabel("60%"));
            labelTable.put(70, new JLabel("70%"));
            labelTable.put(80, new JLabel("80%"));
            labelTable.put(90, new JLabel("90%"));
            labelTable.put(100, new JLabel("100%"));
            musicbar.setLabelTable(labelTable);

            //Einstellen von der Anordnung
                g.weightx = 1;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag

        this.add(musicbar,g);

        // Slidebar test Ende

        //Initialisierung/Erzeugen des Inhalts
            muteMusik = new MyButton("KnopfSoundMetallic1.png","muteMusik",muteMusikImg);
            muteMusik.addActionListener(this);
            //Einstellen von der Anordnung
                g.weightx = 0.5;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(muteMusik,g);

        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
            back.addActionListener(this);

            //Einstellen von der Anordnung
                g.weightx = 0.5;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 2;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(back,g);

    }


    //Aufruf/Schnittstelle mit Manuel-Methoden/Was bei knopfdruck ausgeführt werden soll
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

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == musicbar)
        {
           System.out.println ((float) musicbar.getValue()/100);
           settingsModel.changeMusicVolume((float) musicbar.getValue()/100);
        }
    }
}
