package Views;

import Model.*;
import Window.MyButton;
import Window.MyWindow;
import Window.Var;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

public class PreGameView extends JPanel implements ActionListener, ItemListener, ChangeListener {
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    JButton add,remove;

    JLabel tankName,gameOptions;
    //Slider mit Difficulty
        JSlider[] difficulty_slider = new JSlider[8];
        int[] difficulty_value_Ai = new int[8];
    //
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
        //Equal Height and Width for all Buttons
            int buttonWidth = MyWindow.WIDTH/8;
            int buttonHeigth = buttonWidth*2/5;
        //TODO: Also den Ganzen Scheiß umwerfen und Umkräppeln nach Niels Design!!!

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Altes Layout mit den Buttons
            //Initialisierung/Erzeugen des Inhalts
            start = new MyButton("KnopfSpielStartenMetallic1.png","START",startImg);
            start.addActionListener(this);
            //Einstellen von der Anordnung
            g.gridx = 25;
            g.gridy = 25;
            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(start,g);

            //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK", backImg);
            back.addActionListener(this);
            //Einstellen von der Anordnung
            g.anchor = GridBagConstraints.SOUTH;
            g.gridx = 24;
            g.gridy = 25;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(back,g);


        //Der neue Scheiß
        JLabel art = new JLabel("<html><font color = 'red'><font size = +3>Enemys</font></html>");
        art.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 1;
        this.add(art,g);

        JLabel Icon = new JLabel("<html><font color = 'red'><font size = +3>Difficulty</font></html>");
        Icon.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        Icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        this.add(Icon,g);

        JLabel Team = new JLabel("<html><font color = 'red'><font size = +3>Select Extra Enemy's</font></html>");
        Team.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        Team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
       // g.weightx = 0.1;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        this.add(Team,g);

        gameOptions = new JLabel();
        //Create Images for Visual Shit
            gameOptionsImg = new ImageIcon("res/buttons/KnopfEinstellungenMetallic1.png");
            gameOptionsImg.setImage(gameOptionsImg.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
            gameOptions.setIcon(gameOptionsImg);
        //
        gameOptions.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy =1;
        g.weightx = 0;
        g.gridwidth = 3;
        g.gridheight = 1;
        g.insets = new Insets(0,buttonWidth,0,0);
        g.fill = GridBagConstraints.HORIZONTAL;
        this.add(gameOptions,g);

        //CheckBoxes Added to the JPanel
            infiniteAmmo = new JCheckBox("Infinite Ammo",false);
            infiniteAmmo.setBackground(Color.black);
            infiniteAmmo.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            infiniteAmmo.addItemListener(this);
            g.gridx = 6;
            g.gridy = 2;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.insets = new Insets(10,buttonWidth,0,0);
            this.add(infiniteAmmo,g);

            infiniteGas = new JCheckBox("Infinite Ammo",false);
            infiniteGas.setBackground(Color.black);
        infiniteGas.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            infiniteGas.addItemListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            //g.insets = new Insets(10,buttonWidth,0,0);
            this.add(infiniteGas,g);

            infiniteWeapons = new JCheckBox("Infinite Ammo",false);
            infiniteWeapons.setBackground(Color.black);
            infiniteWeapons.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            infiniteWeapons.addItemListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 2;
            g.gridheight = 1;
            //g.insets = new Insets(10,buttonWidth,0,0);
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
            JLabel test = new JLabel("<html><font color = 'blue'>Test</font></html>");
            //Create Images for Visual Shit
                ImageIcon imageIcon = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png");
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
                test.setIcon(imageIcon);
            //
            test.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            g.gridx = 0;
            g.gridy = 2+i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10,0,0,0);
            this.add(test,g);
            //Difficulty Slider
                difficulty_slider[i] = new JSlider(SwingConstants.HORIZONTAL,0,100,Var.difficulty_sliderValue_preGameView[i]);
                difficulty_slider[i].addChangeListener(this);
                difficulty_slider[i].setMajorTickSpacing(1);

                difficulty_slider[i].setPaintLabels(true);
                difficulty_slider[i].setSnapToTicks(true);
                Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
                    labelTable.put(0, new JLabel("Hard"));
                    labelTable.put(100, new JLabel("Peanuts"));
                //Beschriftung eingeführt
                difficulty_slider[i].setLabelTable(labelTable);
                difficulty_slider[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                difficulty_slider[i].setBackground(MyWindow.backgroundColor);

                g.gridx = 1;
                g.gridy = 2+i;
                g.gridwidth = 1;
                g.gridheight = 1;
                g.weightx = 0;
                g.insets = new Insets(10,0,0,0);
                this.add(difficulty_slider[i],g);
            //

            aiCheckBox[i] = new JCheckBox("<html><font color = 'blue'><font size = +3>Select the Ai</font></html>",selected_aiCheckbox[i]);
            aiCheckBox[i].addItemListener(this);
            aiCheckBox[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            aiCheckBox[i].setBackground(MyWindow.backgroundColor);
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
            //Einfach Linked List übergeben xD
            int amount_selected_Players = 0;
            for (int i =0;i<selected_aiCheckbox.length;i++)
            {
                if(selected_aiCheckbox[i])
                {
                    amount_selected_Players ++;
                }
            }

            preGameModel.startAction(amount_selected_Players,difficulty_value_Ai);
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

    @Override
    public void stateChanged(ChangeEvent e) {
        for(int i= 0; i<difficulty_slider.length;i++)
        {
            Var.difficulty_sliderValue_preGameView[i] = difficulty_slider[i].getValue();
            if(selected_aiCheckbox[i])
            {
                difficulty_value_Ai[i] = difficulty_slider[i].getValue();
                System.out.println(difficulty_value_Ai[i]+ "This is its Index"+i);
            }
        }

    }
}
