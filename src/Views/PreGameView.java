package Views;

import Model.PreGameModel;
import Model.SinglePlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class PreGameView extends JPanel implements ActionListener, ItemListener {
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    JButton add,remove;

    JLabel tankName,gameOptions;

    JCheckBox infiniteAmmo,infiniteGas,infiniteWeapons;
    //Jeckboxes for more Players
        JCheckBox[] aiCheckBox = new JCheckBox[8];

        boolean[] selected_aiCheckbox = new boolean[8];

    JComboBox gameSettingsTest;

    String [] optionen = {"SpielOptionen","UltraGG","SALZKÖNIGREICH", "Unendlich Munition","Unendlich Sprit"};

    ImageIcon gameSettingsImg,startImg,backImg,gameOptionsImg;

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;
        this.setBackground(MyWindow.backgroundColor);

        erzeugenOverlayTest();
    }

    private void erzeugenOverlay()
    {
        //TODO: Also den Ganzen Scheiß umwerfen und Umkräppeln nach Niels Design!!!



        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Initialisierung/Erzeugen des Inhalts
            start = new MyButton("KnopfSpielStartenMetallic1.png","START",startImg);
            start.addActionListener(this);
            //Einstellen von der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(start,g);

        //Initialisierung/Erzeugen des Inhalts
            gameSettings = new MyButton("KnopfSpielEinstellungenMetallic1.png","Spieleinstellungen",gameSettingsImg);
            gameSettings.addActionListener(this);
            //Einstellen von der Anordnung


                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(gameSettings,g);

        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK", backImg);
            back.addActionListener(this);
            //Einstellen von der Anordnung

                g.gridy = 2;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(back,g);
        //JComboBox test
        gameSettingsTest = new JComboBox();

        for(String s : optionen)
        {
            gameSettingsTest.addItem(s);
        }
        //Einstellen von der Anordnung


            g.gridy = 3;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBag
        gameSettingsTest.addActionListener(this);
        gameSettingsTest.setPreferredSize(new Dimension(250,100));
        this.add(gameSettingsTest,g);

    }

    private void erzeugenOverlayTest()
    {
        //TODO: Also den Ganzen Scheiß umwerfen und Umkräppeln nach Niels Design!!!

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Altes Layout mit den Buttons
            //Initialisierung/Erzeugen des Inhalts
            start = new MyButton("KnopfSpielStartenMetallic1.png","START",startImg);
            start.addActionListener(this);
            //Einstellen von der Anordnung
            g.gridx = 25;
            g.gridy = 0;
            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(start,g);

            //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK", backImg);
            back.addActionListener(this);
            //Einstellen von der Anordnung
            g.gridx = 24;
            g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(back,g);
        //Der neue Scheiß
        JLabel art = new JLabel("<html><font color = 'red'>ART</font></html>");
        art.setPreferredSize(new Dimension(250,100));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 1;
        this.add(art,g);

        JLabel Icon = new JLabel("<html><font color = 'red'>Icon</font></html>");
        Icon.setPreferredSize(new Dimension(250,100));
        Icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        this.add(Icon,g);

        JLabel Team = new JLabel("<html><font color = 'red'>Team</font></html>");
        Team.setPreferredSize(new Dimension(250,100));
        Team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        this.add(Team,g);

        gameOptionsImg = new ImageIcon("res/buttons/KnopfEinstellungenMetallic1.png");
        gameOptions = new JLabel();
        gameOptions.setIcon(gameOptionsImg);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy =1;
        g.gridwidth = 3;
        g.gridheight = 1;
        g.insets = new Insets(0,250,0,0);
        g.fill = GridBagConstraints.HORIZONTAL;
        this.add(gameOptions,g);

        //CheckBoxes Added to the JPanel
            infiniteAmmo = new JCheckBox("Infinite Ammo",false);
            infiniteAmmo.setBackground(Color.black);
            infiniteAmmo.addItemListener(this);
            g.gridx = 6;
            g.gridy = 2;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.insets = new Insets(10,250,0,0);
            this.add(infiniteAmmo,g);

            infiniteGas = new JCheckBox("Infinite Ammo",false);
            infiniteGas.setBackground(Color.black);
            infiniteGas.addItemListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.insets = new Insets(10,250,0,0);
            this.add(infiniteGas,g);

            infiniteWeapons = new JCheckBox("Infinite Ammo",false);
            infiniteWeapons.setBackground(Color.black);
            infiniteWeapons.addItemListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.insets = new Insets(10,250,0,0);
            this.add(infiniteWeapons,g);

        //Option with Buttons sound to complicated xD
        /*add = new JButton("ADD Tank");
        add.addActionListener(e -> System.out.println("Ich ahbe funkitonier!!"));
        add.setPreferredSize(new Dimension(250,100));
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        //g.fill = GridBagConstraints.BOTH;
        this.add(add,g);

        remove = new JButton("Reomove Tank");
        remove.addActionListener(e -> System.out.println("Ich habe funktioniert"));
        remove.setPreferredSize(new Dimension(250,100));
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        //g.fill = GridBagConstraints.BOTH;
        this.add(remove,g);*/

        //Adding the Enemy Amount which can be selected
        for(int i = 0;i<8;i++)
        {
            JButton test = new JButton("<html><font color = 'blue'>Test</font></html>"+i);
            test.setPreferredSize(new Dimension(250,100));
            g.gridx = 0;
            g.gridy = 2+i;
            g.gridwidth = 3;
            g.gridheight = 1;
            g.weightx = 0;
            g.fill = GridBagConstraints.HORIZONTAL;
            g.insets = new Insets(10,0,0,0);
            this.add(test,g);

            aiCheckBox[i] = new JCheckBox("Select the Ai",selected_aiCheckbox[i]);
            aiCheckBox[i].addItemListener(this);
            aiCheckBox[i].setPreferredSize(new Dimension(250,100));
            aiCheckBox[i].setBackground(Color.RED);
            g.gridx = 4;
            g.gridy = 2+i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.insets = new Insets(10,0,0,0);
            this.add(aiCheckBox[i],g);
        }

    }


        //Ende des Tests


    //Aufruf/Schnittstelle mit Manuel-Methoden/Was bei knopfdruck ausgeführt werden soll
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gameSettings) {

         if("UltraGG".equals(gameSettingsTest.getSelectedItem() ) )
         {
            System.out.println("Hat geklappt du Opfer!!!");
         }

        }
        if (e.getSource() == start) {
            preGameModel.startAction();
        }

        if (e.getSource() == back) {
            preGameModel.backAction();
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getSource() == infiniteAmmo)
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("InfiniteAmmo was selected");
            }
        }

        if(e.getSource() == infiniteGas)
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("InfiniteGas was selected");
            }
        }

        if(e.getSource() == infiniteWeapons)
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("InfiniteWaepons was selected");
            }
        }


        for(int i = 0;i<aiCheckBox.length;i++)
        {
            if(e.getSource() == aiCheckBox[i])
            {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    System.out.println("AiCheckBos State Changed from" + i);
                    selected_aiCheckbox[i] = true;
                    System.out.println(selected_aiCheckbox[i]);
                }
            }
        }
    }
}
