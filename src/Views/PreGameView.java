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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

public class PreGameView extends JPanel implements ActionListener, ItemListener, ChangeListener {
    PreGameModel preGameModel;
    //Add a new JPanel
        JPanel optionen;
        JPanel buttons;
    //
    MyButton gameSettings,start,back;
    //Amount of human / ai player
        int ai_amount = 8;
        int human_amount = 5; //ACHTUNG! In der Var Klassen ist der seleceted_humanCheckbox_speicher hardgecodet auf !5!
    //
    int buttonWidth = MyWindow.WIDTH/8;
    int buttonHeigth = buttonWidth*2/5;
    int gamemode  = 0;
    JLabel tankName,gameOptions,map_loocking,art,icon,enemy,team;
    //Slider mit Difficulty
        JSlider[] difficulty_slider = new JSlider[ai_amount];
        int[] difficulty_value_Ai = new int[ai_amount];
        int[] team_from_ai = new int[ai_amount];
        int[] team_from_human = new int[human_amount];
    //
    //Jeckboxes for more Players
        JCheckBox[] aiCheckBox = new JCheckBox[ai_amount];
        JCheckBox[] humanCheckBox = new JCheckBox[human_amount];

        boolean[] selected_aiCheckbox = new boolean[ai_amount];
        boolean[] selected_humanCheckbox = new boolean[human_amount];
    //Profile die Eingeloggt werden vom Benutzer
        Profil[] profils = new Profil[human_amount];

    JComboBox[] ai_teamSelecter = new JComboBox[ai_amount];
    JComboBox[] human_teamSelecter = new JComboBox[human_amount];

    JComboBox mapSelectter,modeSelecter;

    String [] gamemodes = {"Gamemodes","Free for all","Team matches", "Instant Fight"};
    String [] team_String = {"Select Team", "Team 1", "Team 2", "Team 3"};
    String[] maps = {"Map1","Map2","Map3","Map4","Map5","Map6"};



    ImageIcon gameOptionsImg;
    Image mapImage;
    GridBagConstraints g;
    BorderLayout b;

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;
        this.setBackground(MyWindow.backgroundColor);
        optionen = new JPanel();
        buttons = new JPanel();
        optionen.setBackground(MyWindow.backgroundColor);
        buttons.setBackground(MyWindow.backgroundColor);
        g = new GridBagConstraints();
        b = new BorderLayout();

        //Bollean
        for(int i =0;i<human_amount;i++)
        {
            selected_humanCheckbox[i] = Var.selected_humanCheckbox_speicher[i];
        }
    }

//TODO: GridBagLAyout in den BorderLayout

    public void erzeugenOverlaySinglePlayer()
    {
        this.setLayout(b);
        optionen.setLayout(new GridBagLayout());
        //Altes Layout mit den Buttons
            //Initialisierung/Erzeugen des Inhalts
            start = new MyButton("KnopfSpielStartenMetallic1.png","START");
            start.addActionListener(this);
            /*//Einstellen von der Anordnung
            g.gridx = 10;
            g.gridy = 10;*/
            //Einfügen der Inhalte in Abhängigkeit der GridBag
            buttons.add(start);

            //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK");
            back.addActionListener(this);
            back.setSize(buttonWidth,buttonHeigth);
            /*//Einstellen von der Anordnung
            g.anchor = GridBagConstraints.SOUTH;
            g.gridx = 9;
            g.gridy = 10;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid*/

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            buttons.add(back);
        //
        //Der neue Scheiß
        art = new JLabel("<html><font color = 'red'><font size = +1>Enemy Image</font></html>");
        art.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 1;
        optionen.add(art,g);

        icon = new JLabel("<html><font color = 'red'><font size = +1>Difficulty</font></html>");
        icon.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        optionen.add(icon,g);

        enemy = new JLabel("<html><font color = 'red'><font size = +1>Select Enemies</font></html>");
        enemy.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        enemy.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(enemy,g);

        team = new JLabel("<html><font color = 'red'><font size = +1>Select Teams</font></html>");
        team.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        g.gridwidth =1;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(team,g);

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
        optionen.add(gameOptions,g);

        //
            map_loocking = new JLabel();
            map_loocking.setBackground(Color.WHITE);
            mapImage = preGameModel.mapSelectingAction(1,Color.GREEN,Color.BLUE).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
            map_loocking.setIcon(new ImageIcon(mapImage));
            map_loocking.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.fill = GridBagConstraints.BOTH;
            g.gridwidth = 1;
            g.gridheight = 1;
        optionen.add(map_loocking,g);

            //MapSelecter
            mapSelectter = new JComboBox();
            mapSelectter.setBackground(MyWindow.backgroundColor);
            mapSelectter.setBorder(BorderFactory.createRaisedBevelBorder());
            mapSelectter.addActionListener(this);
            mapSelectter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : maps)
            {
                mapSelectter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
        optionen.add(mapSelectter,g);

            //Gamemode-Selecter
            modeSelecter = new JComboBox();
            modeSelecter.setBackground(MyWindow.backgroundColor);
            modeSelecter.setBorder(BorderFactory.createRaisedBevelBorder());
            modeSelecter.addActionListener(this);
            modeSelecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : gamemodes)
            {
                modeSelecter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
        optionen.add(modeSelecter,g);

        //Adding the Enemy Amount which can be selected
        for(int i = 0;i<ai_amount;i++)
        {
            tankName = new JLabel("<html><font color = 'blue'>Test</font></html>");
            //Create Images for Visual Shit
                ImageIcon imageIcon = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png");
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
            tankName.setIcon(imageIcon);
            //
            tankName.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            g.gridx = 0;
            g.gridy = 2+i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10,0,0,0);
            optionen.add(tankName,g);

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
            optionen.add(difficulty_slider[i],g);
            //

            aiCheckBox[i] = new JCheckBox("<html><font color = 'blue'><font size = +1>Select the Ai</font></html>",selected_aiCheckbox[i]);
            aiCheckBox[i].addItemListener(this);
            aiCheckBox[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            aiCheckBox[i].setBackground(MyWindow.backgroundColor);
                g.gridx = 4;
                g.gridy = 2+i;
            optionen.add(aiCheckBox[i],g);

            //Team Selecter
            ai_teamSelecter[i] = new JComboBox();
            ai_teamSelecter[i].setBackground(MyWindow.backgroundColor);
            ai_teamSelecter[i].setBorder(BorderFactory.createRaisedBevelBorder());
            ai_teamSelecter[i].addActionListener(this);
            ai_teamSelecter[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : team_String)
            {
                ai_teamSelecter[i].addItem(s);
            }
                g.gridx = 5;
                g.gridy = 2+i;
            optionen.add(ai_teamSelecter[i],g);

        }
        this.add(optionen,BorderLayout.CENTER);
        this.add(buttons,BorderLayout.PAGE_END);
    }


        //Ende des Tests
        //Multiplayer PreGameView

    public void erzeugenOverlayMultiplayer()
    {

        //TODO: Also den Ganzen Scheiß umwerfen und Umkräppeln nach Niels Design!!!

        this.setLayout(b);
        optionen.setLayout(new GridBagLayout());

        //Altes Layout mit den Buttons
        //Initialisierung/Erzeugen des Inhalts
        start = new MyButton("KnopfSpielStartenMetallic1.png","START");
        start.addActionListener(this);
        buttons.add(start,g);

        //Initialisierung/Erzeugen des Inhalts
        back = new MyButton ("KnopfZurückMetallic1.png","BACK");
        back.addActionListener(this);
        buttons.add(back);


        //Der neue Scheiß
        art = new JLabel("<html><font color = 'red'><font size = +1>Enemy Image</font></html>");
        art.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 1;
        optionen.add(art,g);

        icon = new JLabel("<html><font color = 'red'><font size = +1>Difficulty</font></html>");
        icon.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        optionen.add(icon,g);

        enemy = new JLabel("<html><font color = 'red'><font size = +1>Select Enemies</font></html>");
        enemy.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        enemy.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(enemy,g);

        team = new JLabel("<html><font color = 'red'><font size = +1>Select Teams</font></html>");
        team.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 1;
        g.gridwidth =1;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(team,g);

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
        optionen.add(gameOptions,g);


    //Map-Anzeige wird eingefügt
        map_loocking = new JLabel();
        map_loocking.setBackground(Color.WHITE);
        mapImage = preGameModel.mapSelectingAction(1,Color.GREEN,Color.BLUE).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
        map_loocking.setIcon(new ImageIcon(mapImage));
        map_loocking.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        g.gridx = 6;
        g.gridy = GridBagConstraints.RELATIVE;
        g.fill = GridBagConstraints.BOTH;
        g.gridwidth = 1;
        g.gridheight = 1;
        optionen.add(map_loocking,g);

        //MapSelector
            mapSelectter = new JComboBox();
            mapSelectter.setBackground(MyWindow.backgroundColor);
            mapSelectter.setBorder(BorderFactory.createRaisedBevelBorder());
            mapSelectter.addActionListener(this);
            mapSelectter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : maps)
            {
                mapSelectter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
        optionen.add(mapSelectter,g);
        //ModeSelect0r
            modeSelecter = new JComboBox();
            modeSelecter.setBackground(MyWindow.backgroundColor);
            modeSelecter.setBorder(BorderFactory.createRaisedBevelBorder());
            modeSelecter.addActionListener(this);
            modeSelecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : gamemodes)
            {
                modeSelecter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
        optionen.add(modeSelecter,g);

        //Test for 2 Elements in oen Grid
        JPanel[] test = new JPanel[ai_amount];
            for(int i = 0;i<ai_amount;i++)
            {
                test[i] = new JPanel();
                test[i].setBackground(MyWindow.backgroundColor);
                test[i].setLayout(new BorderLayout());
                test[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                aiCheckBox[i] = new JCheckBox("<html><font color = 'blue'><font size = +1>Select as Ai</font></html>", selected_aiCheckbox[i]);
                aiCheckBox[i].addItemListener(this);
                aiCheckBox[i].setBackground(MyWindow.backgroundColor);
                test[i].add(aiCheckBox[i],BorderLayout.NORTH);
            }
            for(int i = 0;i<human_amount;i++)
            {
                humanCheckBox[i] = new JCheckBox("<html><font color = 'red'><font size = +1>Select as Human</font></html>", selected_humanCheckbox[i]);
                humanCheckBox[i].addItemListener(this);
                humanCheckBox[i].setBackground(MyWindow.backgroundColor);
                test[i].add(humanCheckBox[i],BorderLayout.CENTER);
            }

        //Adding the Enemy Amount which can be selected
        for(int i = 0;i<ai_amount;i++) {
            tankName = new JLabel();
            //Create Images for Visual Shit
            ImageIcon imageIcon = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2, buttonHeigth + 2, Image.SCALE_SMOOTH));
            tankName.setIcon(imageIcon);
            //
            tankName.setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
            g.gridx = 0;
            g.gridy = 2 + i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10, 0, 0, 0);
            optionen.add(tankName, g);

            //Difficulty Slider
            difficulty_slider[i] = new JSlider(SwingConstants.HORIZONTAL, 0, 100, Var.difficulty_sliderValue_preGameView[i]);
            difficulty_slider[i].addChangeListener(this);
            difficulty_slider[i].setMajorTickSpacing(1);

            difficulty_slider[i].setPaintLabels(true);
            difficulty_slider[i].setSnapToTicks(true);
            Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
            labelTable.put(0, new JLabel("Hard"));
            labelTable.put(100, new JLabel("Peanuts"));
            //Beschriftung eingeführt
            difficulty_slider[i].setLabelTable(labelTable);
            difficulty_slider[i].setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
            difficulty_slider[i].setBackground(MyWindow.backgroundColor);

            g.gridx = 1;
            g.gridy = 2 + i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10, 0, 0, 0);
            optionen.add(difficulty_slider[i], g);
            //
            //Team Selecter
                ai_teamSelecter[i] = new JComboBox();
                ai_teamSelecter[i].setBackground(MyWindow.backgroundColor);
                ai_teamSelecter[i].setBorder(BorderFactory.createRaisedBevelBorder());
                ai_teamSelecter[i].addActionListener(this);
                ai_teamSelecter[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                for(String s : team_String)
                {
                    ai_teamSelecter[i].addItem(s);
                }
                g.gridx = 5;
                g.gridy = 2+i;
            optionen.add(ai_teamSelecter[i],g);

            g.gridx = 4;
            g.gridy = 2 + i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.insets = new Insets(10, 0, 0, 0);
            optionen.add(test[i], g);
        }

        this.add(optionen,BorderLayout.CENTER);
        this.add(buttons, BorderLayout.PAGE_END);
    }

    //Setzen des Profils nach dem Login
    public void setProfils(Profil profil)
    {
        for(int i = 0;i<profils.length;i++)
        {
            if(Var.login_profils[i] == null)
            {
                Var.login_profils[i] = profil;
                System.out.println(Var.login_profils[i].getName() + "Das ist der Name des Profils in PreGameView synkronosiert");
                i=Var.login_profils.length;
            } else
            {
                System.out.println(i+ " "+ "Platz war besetzt!!");
            }
        }
    }


    //Aufruf/Schnittstelle mit Manuel-Methoden/Was bei knopfdruck ausgeführt werden soll
    @Override
    public void actionPerformed(ActionEvent e)
    {
        int buttonWidth = MyWindow.WIDTH/8;
        int buttonHeigth = buttonWidth*2/5;

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
            //TODO : Übergeben von Teams, Gamemode, map
            if(gamemode == 0) gamemode = 2;
            preGameModel.startAction(amount_selected_Players,difficulty_value_Ai,Var.login_profils,mapSelectter.getSelectedIndex()+1,gamemode);
        }

        if(e.getSource() == mapSelectter)
        {
            System.out.println("Die wird ausgelöst");
            mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,Color.GREEN,Color.BLUE).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
            map_loocking.setIcon(new ImageIcon(mapImage));
        }

        //Gamemodes
        if(e.getSource() == modeSelecter)
        {
            if("Free for all".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 1");
                gamemode = 1;
            }
            if("Team matches".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 2");
                gamemode = 2;
            }
            if("Instant Fight".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 3");
                gamemode = 3;
            }
        }

        if (e.getSource() == back) {
            preGameModel.backAction();
        }
        //Teams
        for(int i = 0; i<ai_amount;i++)
        {
            if(e.getSource() == ai_teamSelecter)
            {
                if("Team 1".equals(ai_teamSelecter[i].getSelectedItem()))
                {
                    team_from_ai[i] = 1;
                }
                if("Team 2".equals(ai_teamSelecter[i].getSelectedItem()))
                {
                    team_from_ai[i] = 2;
                }
                if("Team 3".equals(ai_teamSelecter[i].getSelectedItem()))
                {
                    team_from_ai[i] = 3;
                }
            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

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
                if(e.getStateChange() == ItemEvent.DESELECTED)
                {
                    System.out.println("AiCheckBos State Changed from" + i);
                    selected_aiCheckbox[i] = false;
                    System.out.println(selected_aiCheckbox[i]);
                }
            }
        }

        for(int i = 0;i<humanCheckBox.length;i++)
        {
            if(e.getSource() == humanCheckBox[i])
            {
                if(selected_humanCheckbox[i]) {
                    humanCheckBox[i].setSelected(false);
                    for(int a = i;a<Var.login_profils.length-1;a++)
                    {
                        Var.login_profils[a] = Var.login_profils[i+1];
                    }
                    Var.login_profils[Var.login_profils.length-1] = null;
                } else {
                    Var.selected_humanCheckbox_speicher[i] = true;
                    new LogInModel(true);
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
