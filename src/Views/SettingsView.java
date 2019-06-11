package Views;

import Model.SettingsModel;
import Window.MyButton;
import Window.MyWindow;
import Window.Var;

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

    int musicbarValue;

    public SettingsView ( SettingsModel settingsModel)
    {
        this.setLayout(new GridBagLayout());


        this.settingsModel = settingsModel;
        this.setBackground(MyWindow.backgroundColor);

        erzeugenOverlay();

    }

    private void erzeugenOverlay()
    {

        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        GridBagConstraints g = new GridBagConstraints();

        //Slidebar test Anfang
        //if(Var.music.getVolume() == 0){Var.music.setVolume(0.5f);}

            musicbarValue = Var.soundBarVolume;
            System.out.println(musicbarValue +"Der Anfang der Musicbar");
            musicbar = new JSlider(SwingConstants.HORIZONTAL,0,100,musicbarValue );
            musicbar.addChangeListener(this);
            musicbar.setMajorTickSpacing(10);

            musicbar.setPaintTicks(true);
            musicbar.setPaintLabels(true);
            musicbar.setSnapToTicks(true);

            // Beschriftung des Sliders
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
            //
            musicbar.setLabelTable(labelTable);
            musicbar.setPreferredSize(new Dimension(500,100));
            musicbar.setBackground(MyWindow.backgroundColor);

            //Einstellen von der Anordnung
                //g.weightx = 1f;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag

        this.add(musicbar,g);

        // Slidebar test Ende

        //Initialisierung/Erzeugen des Inhalts
            muteMusik = new MyButton("KnopfSoundMetallic1.png", "Press or use Slidebar to mute", muteMusikImg);
        if(Var.music.isMuted()){
            ImageIcon mute = new ImageIcon("res/buttons/Mute1.png");
            mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
            muteMusik.setIcon(mute);

            muteMusik = new MyButton("Mute1.png", "Press or use Slidebar to mute", muteMusikImg);
            muteMusik.addActionListener(this);
            //Einstellen von der Anordnung

            g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

            g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

            g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

            g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(muteMusik,g);
        } else
        {
            muteMusik = new MyButton("KnopfSoundMetallic1.png", "Press or use Slidebar to mute", muteMusikImg);
            //muteMusik = new MyButton("KnopfSoundMetallic1.png","muteMusik",muteMusikImg);
            muteMusik.addActionListener(this);
            //Einstellen von der Anordnung

            g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(muteMusik,g);
        }



        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
            back.addActionListener(this);

            //Einstellen von der Anordnung

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

        if (e.getSource() == muteMusik) {
            settingsModel.muteMusikAction();
            //Abfrage ob die Music gemuted ist und dann Image ändern
            if (Var.music.isMuted()) {
                ImageIcon mute = new ImageIcon("res/buttons/Mute1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);

                musicbarValue = musicbar.getValue();


            } else {
                ImageIcon mute = new ImageIcon("res/buttons/KnopfSoundMetallic1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);
                musicbar.setValue(musicbarValue);
            }
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == musicbar)
        {
           //System.out.println ((float) musicbar.getValue()/100);
            //Abfrage von Muted Music
            if(musicbar.getValue() == 0) {
                ImageIcon mute = new ImageIcon("res/buttons/Mute1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);
            } else
            {
                ImageIcon mute = new ImageIcon("res/buttons/KnopfSoundMetallic1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);
            }
            //
           settingsModel.changeMusicVolume((float) musicbar.getValue()/100);
            musicbarValue = musicbar.getValue();
        }


    }
}
