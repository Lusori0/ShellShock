package Views;

import Model.*;
import Panzer.Panzer;
import Window.MyButton;
import Window.MyWindow;
import Window.Var;

import javax.sound.midi.SysexMessage;
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
        JPanel[] connecting;
        JPanel rgb_allingment;
    //
    MyButton gameSettings,start,back;
    //Amount of human / ai player
        int ai_amount = 7;
        int human_amount = 5; //ACHTUNG! In der Var Klassen ist der seleceted_humanCheckbox_speicher hardgecodet auf !5!
    //
    int buttonWidth = MyWindow.WIDTH/8;
    int buttonHeigth = buttonWidth*2/5;
    int gamemode  = 0;
    JLabel tankName,gameOptions,map_loocking,art,icon,enemy,team,currentPlayer_tank,firingOptions;
    //Slider mit Difficulty
        JSlider[] difficulty_slider = new JSlider[ai_amount];
        int[] difficulty_value_Ai = new int[ai_amount];
        int[] team_from_ai = new int[ai_amount];
        int[] team_from_human = new int[human_amount];
        int team_current_user;
    //
    //Jeckboxes for more Players
        JCheckBox[] aiCheckBox = new JCheckBox[ai_amount];
        JCheckBox[] humanCheckBox = new JCheckBox[human_amount];
        MyButton[] login_human = new MyButton[human_amount];
        boolean[] selected_aiCheckbox = new boolean[ai_amount];
        boolean[] selected_humanCheckbox = new boolean[human_amount];
        boolean mulitplayer; //Only for ItemStateChanged from AI
    //Profile die Eingeloggt werden vom Benutzer
        Profil[] profils = new Profil[human_amount];
    //TeamSelecter
        JComboBox[] ai_teamSelecter = new JComboBox[ai_amount];
        JComboBox[] human_teamSelecter = new JComboBox[human_amount];
        //JComboBox curren_tank_Selecter = new JComboBox();
    //Map/ModeSelecter
        JComboBox mapSelectter,modeSelecter;
    //RGB Slider + Button
        JButton change_fb; // Button to change between foreground and backgorund
        JSlider red ;
        JSlider green;
        JSlider blue ;
        JSlider red_b ;
        JSlider green_b;
        JSlider blue_B ;
        Color foreground = new Color(Var.redtemp,Var.greentemp,Var.bluetemp);
        Color background = new Color(Var.red_btemp,Var.green_btemp,Var.blue_btemp);

    String [] firingmode = {
            "<html><font color = 'green'><font size = +1>Single Fire</font></html>",
            "<html><font color = 'green'><font size = +1>Team wise</font></html>",
            "<html><font color = 'green'><font size = +1>All at once</font></html>"
    };
    String [] team_String = {
            "<html><font color = 'green'><font size = +1>Team 1</font></html>",
            "<html><font color = 'green'><font size = +1>Team 2</font></html>",
            "<html><font color = 'green'><font size = +1>Team 3</font></html>",
            "<html><font color = 'green'><font size = +1>Team 4</font></html>"
    };
    String[] maps = {
            "<html><font color = 'green'><font size = +1>Map1</font></html>",
            "<html><font color = 'green'><font size = +1>Map2</font></html>",
            "<html><font color = 'green'><font size = +1>Map3</font></html>",
            "<html><font color = 'green'><font size = +1>Map4</font></html>",
            "<html><font color = 'green'><font size = +1>Map5</font></html>",
            "<html><font color = 'green'><font size = +1>Random Map</font></html>"
    };

    //TODO:  Ingamevolume ändern

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

        //Copy the Values in class variable
            for(int i =0;i<human_amount;i++)
            {
                selected_humanCheckbox[i] = Var.selected_humanCheckbox_speicher[i];
            }
            for(int i =0;i<ai_amount;i++)
            {
                selected_aiCheckbox[i] = Var.selected_aiCheckbox_speicher[i];
            }

        for(int i = 0;i<team_from_ai.length;i++)
        {
            team_from_ai[i]= Var.team_from_ai_speicher[i];
        }
        for(int i = 0; i<team_from_human.length;i++)
        {
            team_from_human[i]= Var.team_from_human_speicher[i];
        }
    }

    public void erzeugenOverlaySinglePlayer()
    {

        this.setLayout(b);
        optionen.setLayout(new GridBagLayout());
        //Altes Layout mit den Buttons
            //Initialisierung/Erzeugen des Inhalts
            start = new MyButton("KnopfSpielStartenMetallic1.png","START");
            start.addActionListener(this);
            buttons.add(start);

            //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK");
            back.addActionListener(this);
            back.setSize(buttonWidth,buttonHeigth);
            buttons.add(back);
        //

        //Adding currentProfile in Top Row
            currentPlayer_tank = new JLabel();
                ImageIcon tank = new ImageIcon(Panzer.getPanzerImage(Var.activeUser.getPanzerLevel())); //Falls man mit mehreren Panzer einfach getPanzer bei Var.aktiveUser
                tank.setImage(tank.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
            currentPlayer_tank.setIcon(tank);
            //GridbagLayout
            g.gridx = 0;
            g.gridy = 1;

            optionen.add(currentPlayer_tank,g);
            JLabel description = new JLabel("<html><font color = 'red'><font size = +1>Selected Tank from active user, Your Team: 1</font></html>");

            //GridbagLayout
            g.gridx = 1;
            g.gridy = 1;
            g.gridwidth = 2;
            optionen.add(description,g);
            //TeamSelecter
               /* curren_tank_Selecter = new JComboBox();
                curren_tank_Selecter.setBackground(MyWindow.backgroundColor);
                curren_tank_Selecter.setBorder(BorderFactory.createRaisedBevelBorder());
                curren_tank_Selecter.addActionListener(this);
                curren_tank_Selecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                for(String s : team_String)
                {
                    curren_tank_Selecter.addItem(s);
                }
                g.gridx = 2;
                g.gridy = 1;
            optionen.add(curren_tank_Selecter,g);*/

        //Adding Enemy
        art = new JLabel("<html><font color = 'red'><font size = +1>Enemy Image</font></html>");
        art.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth =1;
        optionen.add(art,g);

        icon = new JLabel("");
        icon.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
        optionen.add(icon,g);

        enemy = new JLabel("<html><font color = 'red'><font size = +1>Select Enemies</font></html>");
        enemy.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        enemy.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(enemy,g);

        team = new JLabel("<html><font color = 'red'><font size = +1>Select Teams</font></html>");
        team.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
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
        g.gridy =2;
        g.weightx = 0;
        g.gridwidth = 3;
        g.gridheight = 1;
        g.insets = new Insets(MyWindow.HEIGHT/100,buttonWidth,0,0);
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(gameOptions,g);

        //RGB Slider
                 rgb_allingment = new JPanel();
                rgb_allingment.setLayout(new BorderLayout());
                //Red Slider
                red = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.redtemp);
                red.addChangeListener(this);
                red.setMajorTickSpacing(1);

                red.setPaintLabels(true);
                red.setSnapToTicks(true);
                Dictionary<Integer, Component> labelTable_red = new Hashtable<Integer, Component>();
                    labelTable_red.put(0, new JLabel("<html><font color = 'red'>0</font></html>"));
                    labelTable_red.put(255, new JLabel("<html><font color = 'red'>255</font></html>"));
                //Beschriftung eingeführt
                red.setLabelTable(labelTable_red);
                red.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                red.setBackground(MyWindow.backgroundColor);
                rgb_allingment.add(red,BorderLayout.NORTH);

                //Green Slider
                green = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.greentemp);
                green.addChangeListener(this);
                green.setMajorTickSpacing(1);
                green.setPaintLabels(true);
                green.setSnapToTicks(true);
                Dictionary<Integer, Component> labelTable_green = new Hashtable<Integer, Component>();
                    labelTable_green.put(0, new JLabel("<html><font color = 'green'>0</font></html>"));
                    labelTable_green.put(255, new JLabel("<html><font color = 'green'>255</font></html>"));
                //Beschriftung eingeführt
                green.setLabelTable(labelTable_green);
                green.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                green.setBackground(MyWindow.backgroundColor);
                rgb_allingment.add(green, BorderLayout.CENTER);

                //Blue Slider
                blue = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.bluetemp);
                blue.addChangeListener(this);
                blue.setMajorTickSpacing(1);

                blue.setPaintLabels(true);
                blue.setSnapToTicks(true);
                Dictionary<Integer, Component> labelTable_blue = new Hashtable<Integer, Component>();
                    labelTable_blue.put(0, new JLabel("<html><font color = 'blue'>0</font></html>"));
                    labelTable_blue.put(255, new JLabel("<html><font color = 'blue'>255</font></html>"));
                //Beschriftung eingeführt
                blue.setLabelTable(labelTable_blue);
                blue.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                blue.setBackground(MyWindow.backgroundColor);
                rgb_allingment.add(blue, BorderLayout.SOUTH);

        //
        //Add Mini-MapImg
            map_loocking = new JLabel();
            map_loocking.setBackground(Color.WHITE);
            mapImage = preGameModel.mapSelectingAction(1,new Color(Var.redtemp,Var.greentemp,Var.bluetemp),new Color(Var.red_btemp,Var.green_btemp,Var.blue_btemp)).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
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
        //Unter der Map die RGB Slider einfügen
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth =2;
            g.gridheight = 2;
        optionen.add(rgb_allingment,g);
        //Button to change Foreground OR Background
            change_fb = new JButton("<html><font color = 'green'><font size = +1>Change Foreground</font></html>");
            change_fb.setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
            change_fb.setBackground(MyWindow.backgroundColor);
            change_fb.addActionListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth = 1;
            g.gridheight =1;
        optionen.add(change_fb,g);

            //Adding Firing Options
            firingOptions = new JLabel("<html><font color = 'blue'><font size = +1>Select FiringOptions</font></html>");
            firingOptions.setBackground(MyWindow.backgroundColor);
                g.gridx = 6;
                g.gridy = GridBagConstraints.RELATIVE;
                g.gridwidth =1;
                g.gridheight = 1;
            optionen.add(firingOptions,g);

            //Gamemode-Selecter
            modeSelecter = new JComboBox();
            modeSelecter.setBackground(MyWindow.backgroundColor);
            modeSelecter.setBorder(BorderFactory.createRaisedBevelBorder());
            modeSelecter.addActionListener(this);
            modeSelecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : firingmode)
            {
                modeSelecter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
        optionen.add(modeSelecter,g);


        //Adding the Enemy Amount which can be selected
        for(int i = 0;i<ai_amount;i++)
        {
            tankName = new JLabel();
            //Create Images for Visual Shit
                ImageIcon imageIcon = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png");
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
            tankName.setIcon(imageIcon);
            //
            tankName.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            g.gridx = 0;
            g.gridy = 3+i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10,0,0,0);
            optionen.add(tankName,g);


            aiCheckBox[i] = new JCheckBox("<html><font color = 'white'><font size = +1>Ai (Unselected)</font></html>",selected_aiCheckbox[i]);
            aiCheckBox[i].addItemListener(this);
            aiCheckBox[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            aiCheckBox[i].setBackground(MyWindow.backgroundColor);
                g.gridx = 2;
                g.gridy = 3+i;
            g.insets = new Insets(10,0,0,0);
            optionen.add(aiCheckBox[i],g);


        }
        this.add(optionen,BorderLayout.CENTER);
        this.add(buttons,BorderLayout.PAGE_END);
    }


        //Ende des Tests
        //Multiplayer PreGameView

    public void erzeugenOverlayMultiplayer()
    {
        mulitplayer = true;

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

        //Adding currentProfile in Top Row
            currentPlayer_tank = new JLabel();
            ImageIcon tank = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png"); //Falls man mit mehreren Panzer einfach getPanzer bei Var.aktiveUser
            tank.setImage(tank.getImage().getScaledInstance(buttonWidth + 2,buttonHeigth + 2,Image.SCALE_SMOOTH));
            currentPlayer_tank.setIcon(tank);
            //GridbagLayout
            g.gridx = 0;
            g.gridy = 1;

            optionen.add(currentPlayer_tank,g);
        JLabel description = new JLabel("<html><font color = 'red'><font size = +1>Selected Tank from active user, Your Team: 1</font></html>");

            //GridbagLayout
            g.gridx = 1;
            g.gridy = 1;
            g.gridwidth = 2;
        optionen.add(description,g);

        //TeamSelecter
            /*curren_tank_Selecter = new JComboBox();
            curren_tank_Selecter.setBackground(MyWindow.backgroundColor);
            curren_tank_Selecter.setBorder(BorderFactory.createRaisedBevelBorder());
            curren_tank_Selecter.addActionListener(this);
            curren_tank_Selecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : team_String)
            {
                curren_tank_Selecter.addItem(s);
            }
            g.gridx = 2;
            g.gridy = 1;
        optionen.add(curren_tank_Selecter,g);*/

        //Der neue Scheiß
        art = new JLabel("<html><font color = 'red'><font size = +1>Enemy Image</font></html>");
        art.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        art.setBackground(Color.PINK);
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth =1;
        optionen.add(art,g);

        icon = new JLabel("<html><font color = 'red'><font size = +1></font></html>");
        icon.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        icon.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
        optionen.add(icon,g);

        enemy = new JLabel("<html><font color = 'red'><font size = +1>Select Enemies</font></html>");
        enemy.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        enemy.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
        g.gridwidth =3;
        g.gridheight = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(enemy,g);

        team = new JLabel("<html><font color = 'red'><font size = +1>Select Teams</font></html>");
        team.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
        team.setBackground(Color.RED);
        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 2;
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
        g.gridy =2;
        g.weightx = 0;
        g.gridwidth = 3;
        g.gridheight = 1;
        g.insets = new Insets(0,buttonWidth,0,0);
        g.fill = GridBagConstraints.HORIZONTAL;
        optionen.add(gameOptions,g);

        //RGB Slider
        rgb_allingment = new JPanel();
        rgb_allingment.setLayout(new BorderLayout());
        //Red Slider
            red = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.redtemp);
            red.addChangeListener(this);
            red.setMajorTickSpacing(1);

            red.setPaintLabels(true);
            red.setSnapToTicks(true);
            Dictionary<Integer, Component> labelTable_red = new Hashtable<Integer, Component>();
            labelTable_red.put(0, new JLabel("<html><font color = 'red'>0</font></html>"));
            labelTable_red.put(255, new JLabel("<html><font color = 'red'>255</font></html>"));
            //Beschriftung eingeführt
            red.setLabelTable(labelTable_red);
            red.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
            red.setBackground(MyWindow.backgroundColor);
            rgb_allingment.add(red,BorderLayout.NORTH);

            //Green Slider
            green = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.greentemp);
            green.addChangeListener(this);
            green.setMajorTickSpacing(1);

            green.setPaintLabels(true);
            green.setSnapToTicks(true);
            Dictionary<Integer, Component> labelTable_green = new Hashtable<Integer, Component>();
            labelTable_green.put(0, new JLabel("<html><font color = 'green'>0</font></html>"));
            labelTable_green.put(255, new JLabel("<html><font color = 'green'>255</font></html>"));
            //Beschriftung eingeführt
            green.setLabelTable(labelTable_green);
            green.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
            green.setBackground(MyWindow.backgroundColor);
            rgb_allingment.add(green, BorderLayout.CENTER);

            //Blue Slider
            blue = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.bluetemp);
            blue.addChangeListener(this);
            blue.setMajorTickSpacing(1);

            blue.setPaintLabels(true);
            blue.setSnapToTicks(true);
            Dictionary<Integer, Component> labelTable_blue = new Hashtable<Integer, Component>();
            labelTable_blue.put(0, new JLabel("<html><font color = 'blue'>0</font></html>"));
            labelTable_blue.put(255, new JLabel("<html><font color = 'blue'>255</font></html>"));
            //Beschriftung eingeführt
            blue.setLabelTable(labelTable_blue);
            blue.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
            blue.setBackground(MyWindow.backgroundColor);
            rgb_allingment.add(blue, BorderLayout.SOUTH);
        //
        //Add Mini-MapImg
            map_loocking = new JLabel();
            map_loocking.setBackground(Color.WHITE);
            mapImage = preGameModel.mapSelectingAction(1,new Color(Var.redtemp,Var.greentemp,Var.bluetemp),new Color(Var.red_btemp,Var.green_btemp,Var.blue_btemp)).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
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
            //Unter der Map die RGB Slider einfügen
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth = 2;
            g.gridheight = 2;
        optionen.add(rgb_allingment,g);
        //Button to change Foreground OR Background
            change_fb = new JButton("<html><font color = 'green'><font size = +1>Change Foreground</font></html>");
            change_fb.setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
            change_fb.setBackground(MyWindow.backgroundColor);
            change_fb.addActionListener(this);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth = 1;
            g.gridheight = 1;
        optionen.add(change_fb,g);

        //Adding Firing Options
            firingOptions = new JLabel("<html><font color = 'blue'><font size = +1>Select FiringOptions</font></html>");
            firingOptions.setBackground(MyWindow.backgroundColor);
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth =1;
            g.gridheight = 1;
            optionen.add(firingOptions,g);

            //Gamemode-Selecter
            modeSelecter = new JComboBox();
            modeSelecter.setBackground(MyWindow.backgroundColor);
            modeSelecter.setBorder(BorderFactory.createRaisedBevelBorder());
            modeSelecter.addActionListener(this);
            modeSelecter.setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
            for(String s : firingmode)
            {
                modeSelecter.addItem(s);
            }
            g.gridx = 6;
            g.gridy = GridBagConstraints.RELATIVE;
            optionen.add(modeSelecter,g);

        // 2 Elements in one Grid
            connecting = new JPanel[ai_amount];
            for(int i = 0;i<ai_amount;i++)
            {
                connecting[i] = new JPanel();
                connecting[i].setBackground(MyWindow.backgroundColor);
                connecting[i].setLayout(new BorderLayout());
                connecting[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                if(selected_aiCheckbox[i])
                {
                    aiCheckBox[i] = new JCheckBox("<html><font color = 'blue'>Ai (Selected)</font></html>", selected_aiCheckbox[i]);
                }else{
                    aiCheckBox[i] = new JCheckBox("<html><font color = 'white'>Ai (Unselected)</font></html>", selected_aiCheckbox[i]);
                }

                aiCheckBox[i].addItemListener(this);
                aiCheckBox[i].setBackground(MyWindow.backgroundColor);
                connecting[i].add(aiCheckBox[i],BorderLayout.NORTH);
            }
            for(int i = 0;i<human_amount;i++)
            {
                if(selected_humanCheckbox[i])
                {
                    humanCheckBox[i] = new JCheckBox("<html><font color = 'blue'>Connected as "+(Var.login_profils[i].getName())+"</font></html>", selected_humanCheckbox[i]);
                    connecting[i].remove(aiCheckBox[i]);
                    connecting[i].revalidate();
                    connecting[i].repaint();
                }else{
                    humanCheckBox[i] = new JCheckBox("<html><font color = 'white'>Humanplayer (Unselected)</font></html>", selected_humanCheckbox[i]);
                }
                humanCheckBox[i].addItemListener(this);
                humanCheckBox[i].setBackground(MyWindow.backgroundColor);
                connecting[i].add(humanCheckBox[i],BorderLayout.CENTER);
            }

        //Adding the Enemy Amount which can be selected
        for(int i = 0;i<ai_amount;i++) {
            tankName = new JLabel();
            //Create Images for Visual Shit
            if(i<5 &&Var.login_profils[i] != null){
                ImageIcon imageIcon = new ImageIcon(Panzer.getPanzerImage(Var.login_profils[i].getPanzerLevel()));
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2, buttonHeigth + 2, Image.SCALE_SMOOTH));
                tankName.setIcon(imageIcon);
            } else{
                ImageIcon imageIcon = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png");
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(buttonWidth + 2, buttonHeigth + 2, Image.SCALE_SMOOTH));
                tankName.setIcon(imageIcon);
            }
            //
            tankName.setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
            g.gridx = 0;
            g.gridy = 3 + i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.weightx = 0;
            g.insets = new Insets(10, 0, 0, 0);
            optionen.add(tankName, g);

            g.gridx = 2;
            g.gridy = 3 + i;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.insets = new Insets(10, 0, 0, 0);
            optionen.add(connecting[i], g);
        }

        this.add(optionen,BorderLayout.CENTER);
        this.add(buttons, BorderLayout.PAGE_END);
    }








    //Setzen des Profils nach dem Login
    public void setProfils(Profil profil)
    {
        for(int i = 0;i<profils.length;i++)
        {
            if(Var.login_profils[i] == null && selected_humanCheckbox[i] )
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
            if(gamemode == 0) gamemode = 1;
            for(int i = 0;i<Var.login_profils.length;i++)
            {
                for(int l = 0;l<Var.selected_humanCheckbox_speicher.length;l++)
                {
                    Var.selected_humanCheckbox_speicher[l] = false;
                }
                //Abbruch von Start wenn keine Feinde ausgewählt wurden
                if(amount_selected_Players != 0 || Var.login_profils[i] != null){
                        for (int a = 0; a < ai_amount; a++)
                        {
                            if(team_from_ai[a]> 1 || team_from_human[i] >1)
                            {
                                System.out.println("Auf jeden Fall sind KIs vorhanden!!");
                                preGameModel.startAction(amount_selected_Players, difficulty_value_Ai, Var.login_profils, mapSelectter.getSelectedIndex() + 1, gamemode,foreground,background,team_from_human,team_from_ai);
                                a = ai_amount;
                            } else
                            {
                                JOptionPane.showMessageDialog(this,"Please select for at lest 1 Enemy a different Team than Team 1");
                                a = ai_amount;
                            }
                        }
                    //preGameModel.startAction(amount_selected_Players, difficulty_value_Ai, Var.login_profils, mapSelectter.getSelectedIndex() + 1, gamemode,foreground,background,team_from_human,team_from_ai);
                    i = Var.login_profils.length;
                } else{
                    JOptionPane.showMessageDialog(this, "Please Select an Enemy");

                    i = Var.login_profils.length;
                }
            }
        }



        if(e.getSource() == change_fb)
        {
            if("<html><font color = 'green'><font size = +1>Change Background</font></html>".equals(change_fb.getText()))
            {
                change_fb.setText("<html><font color = 'green'><font size = +1>Change Foreground</font></html>");
                //Save Slider Value
                    Var.red_btemp = red_b.getValue();
                    Var.green_btemp = green_b.getValue();
                    Var.blue_btemp = blue_B.getValue();
                //Clear JPanel
                    rgb_allingment.remove(red_b);
                    rgb_allingment.remove(green_b);
                    rgb_allingment.remove(blue_B);
                    rgb_allingment.revalidate();
                    rgb_allingment.repaint();
                //Red Slider
                    red = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.redtemp);
                    red.addChangeListener(this);
                    red.setMajorTickSpacing(1);

                    red.setPaintLabels(true);
                    red.setSnapToTicks(true);
                    Dictionary<Integer, Component> labelTable_red = new Hashtable<Integer, Component>();
                    labelTable_red.put(0, new JLabel("<html><font color = 'red'>0</font></html>"));
                    labelTable_red.put(255, new JLabel("<html><font color = 'red'>255</font></html>"));
                    //Beschriftung eingeführt
                    red.setLabelTable(labelTable_red);
                    red.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    red.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(red,BorderLayout.NORTH);

                    //Green Slider
                    green = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.greentemp);
                    green.addChangeListener(this);
                    green.setMajorTickSpacing(1);

                    green.setPaintLabels(true);
                    green.setSnapToTicks(true);
                    Dictionary<Integer, Component> labelTable_green = new Hashtable<Integer, Component>();
                    labelTable_green.put(0, new JLabel("<html><font color = 'green'>0</font></html>"));
                    labelTable_green.put(255, new JLabel("<html><font color = 'green'>255</font></html>"));
                    //Beschriftung eingeführt
                    green.setLabelTable(labelTable_green);
                    green.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    green.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(green, BorderLayout.CENTER);

                    //Blue Slider
                    blue = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.bluetemp);
                    blue.addChangeListener(this);
                    blue.setMajorTickSpacing(1);

                    blue.setPaintLabels(true);
                    blue.setSnapToTicks(true);
                    Dictionary<Integer, Component> labelTable_blue = new Hashtable<Integer, Component>();
                    labelTable_blue.put(0, new JLabel("<html><font color = 'blue'>0</font></html>"));
                    labelTable_blue.put(255, new JLabel("<html><font color = 'blue'>255</font></html>"));
                    //Beschriftung eingeführt
                    blue.setLabelTable(labelTable_blue);
                    blue.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    blue.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(blue, BorderLayout.SOUTH);
                //
            } else
            {
                change_fb.setText("<html><font color = 'green'><font size = +1>Change Background</font></html>");
                //Save Slider Value
                    Var.redtemp = red.getValue();
                    Var.greentemp = green.getValue();
                    Var.bluetemp = blue.getValue();
                //Clear JPanel
                    rgb_allingment.remove(red);
                    rgb_allingment.remove(green);
                    rgb_allingment.remove(blue);
                    rgb_allingment.revalidate();
                    rgb_allingment.repaint();

                //Add new Slider for Background
                    red_b = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.red_btemp);
                    red_b.addChangeListener(this);
                    red_b.setMajorTickSpacing(1);

                    red_b.setPaintLabels(true);
                    red_b.setSnapToTicks(true);
                    Dictionary<Integer, Component> labelTable_red = new Hashtable<Integer, Component>();
                    labelTable_red.put(0, new JLabel("<html><font color = 'red'>0</font></html>"));
                    labelTable_red.put(255, new JLabel("<html><font color = 'red'>255</font></html>"));
                    //Beschriftung eingeführt
                    red_b.setLabelTable(labelTable_red);
                    red_b.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    red_b.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(red_b,BorderLayout.NORTH);

                    //Green Slider
                    green_b = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.green_btemp);
                    green_b.addChangeListener(this);
                    green_b.setMajorTickSpacing(1);

                    green_b.setPaintLabels(true);
                    green_b.setSnapToTicks(true);
                    Dictionary<Integer, Component> labelTable_green = new Hashtable<Integer, Component>();
                    labelTable_green.put(0, new JLabel("<html><font color = 'green'>0</font></html>"));
                    labelTable_green.put(255, new JLabel("<html><font color = 'green'>255</font></html>"));
                    //Beschriftung eingeführt
                    green_b.setLabelTable(labelTable_green);
                    green_b.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    green_b.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(green_b, BorderLayout.CENTER);

                    //Blue Slider
                    blue_B = new JSlider(SwingConstants.HORIZONTAL,0,255,Var.blue_btemp);
                    blue_B.addChangeListener(this);
                    blue_B.setMajorTickSpacing(1);

                    blue_B.setPaintLabels(true);
                    blue_B.setSnapToTicks(true);
                        Dictionary<Integer, Component> labelTable_blue = new Hashtable<Integer, Component>();
                    labelTable_blue.put(0, new JLabel("<html><font color = 'blue'>0</font></html>"));
                    labelTable_blue.put(255, new JLabel("<html><font color = 'blue'>255</font></html>"));
                    //Beschriftung eingeführt
                    blue_B.setLabelTable(labelTable_blue);
                    blue_B.setPreferredSize(new Dimension(buttonWidth,buttonHeigth/2));
                    blue_B.setBackground(MyWindow.backgroundColor);
                    rgb_allingment.add(blue_B, BorderLayout.SOUTH);

            }
        }




        if(e.getSource() == mapSelectter)
        {
            //System.out.println("Die wird ausgelöst");
            mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
            map_loocking.setIcon(new ImageIcon(mapImage));
        }



        //Gamemodes
        if(e.getSource() == modeSelecter)
        {
            if("<html><font color = 'green'><font size = +1>Single Fire</font></html>".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 1");
                gamemode = 1;
            }
            if("<html><font color = 'green'><font size = +1>Team wise</font></html>".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 2");
                gamemode = 2;
            }
            if("<html><font color = 'green'><font size = +1>All at once</font></html>".equals(modeSelecter.getSelectedItem()))
            {
                System.out.println("So finde ich den heraus 3");
                gamemode = 3;
            }

        }





        if (e.getSource() == back) {
            for(int i=0;i<human_amount;i++)
            {
                Var.login_profils[i] = null;
                Var.selected_humanCheckbox_speicher[i] = false;
            }
            preGameModel.backAction();
        }



        //Teams from ai
            for(int i = 0; i<ai_amount;i++) {
                if(e.getSource() == ai_teamSelecter[i])
                {
                    if(("<html><font color = 'green'><font size = +1>Team 1</font></html>").equals(ai_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_ai[i] = 1;
                        Var.team_from_ai_speicher[i] = 1;
                    }

                    if(("<html><font color = 'green'><font size = +1>Team 2</font></html>").equals(ai_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_ai[i] = 2;
                        Var.team_from_ai_speicher[i] = 2;
                    }

                    if(("<html><font color = 'green'><font size = +1>Team 3</font></html>").equals(ai_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_ai[i] = 3;
                        Var.team_from_ai_speicher[i] = 3;
                    }

                    if(("<html><font color = 'green'><font size = +1>Team 4</font></html>").equals(ai_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_ai[i] = 4;
                        Var.team_from_ai_speicher[i] = 4;
                    }

                }
            }



        //Teams from human
            for(int i = 0; i<human_amount;i++) {
                if(e.getSource() == human_teamSelecter[i])
                {
                    if("<html><font color = 'green'><font size = +1>Team 1</font></html>".equals(human_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_human[i] = 1;
                        Var.team_from_human_speicher[i] = 1;
                    }

                    if("<html><font color = 'green'><font size = +1>Team 2</font></html>".equals(human_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_human[i] = 2;
                        Var.team_from_human_speicher[i] = 2;
                    }

                    if("<html><font color = 'green'><font size = +1>Team 3</font></html>".equals(human_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_human[i] = 3;
                        Var.team_from_human_speicher[i] = 3;
                        System.out.println(i + "Index wo was passiert ist"+ team_from_human[i]);
                    }

                    if("<html><font color = 'green'><font size = +1>Team 4</font></html>".equals(human_teamSelecter[i].getSelectedItem()))
                    {
                        team_from_human[i] = 4;
                        Var.team_from_human_speicher[i] = 4;
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
                if(!mulitplayer) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        aiCheckBox[i].setText("<html><font color = 'blue'><font size = +1>Selected</font></html>");
                        icon.setText("<html><font color = 'red'><font size = +1>Choose Difficulty</font></html>");
                        selected_aiCheckbox[i] = true;
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
                            ai_teamSelecter[i].setSelectedItem(team_String[1]);
                            g.gridx = 5;
                            g.gridy = 3+i;
                        optionen.add(ai_teamSelecter[i],g);



                            difficulty_slider[i] = new JSlider(SwingConstants.HORIZONTAL, 0, 10, Var.difficulty_sliderValue_preGameView[i]);
                            difficulty_slider[i].addChangeListener(this);
                            difficulty_slider[i].setMajorTickSpacing(1);

                            difficulty_slider[i].setPaintLabels(true);
                            difficulty_slider[i].setSnapToTicks(true);
                            Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
                            labelTable.put(0, new JLabel("Hard"));
                            labelTable.put(10, new JLabel("Peanuts"));
                            //Beschriftung eingefügt
                            difficulty_slider[i].setLabelTable(labelTable);
                            difficulty_slider[i].setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
                            difficulty_slider[i].setBackground(MyWindow.backgroundColor);

                            g.gridx = 1;
                            g.gridy = 3 + i;
                            g.gridwidth = 1;
                            g.gridheight = 1;
                            g.weightx = 0;
                            g.insets = new Insets(10, 0, 0, 0);
                        optionen.add(difficulty_slider[i], g);
                    }


                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        aiCheckBox[i].setText("<html><font color = 'white'><font size = +1>Ai (Unselected)</font></html>");
                        selected_aiCheckbox[i] = false;
                        for(int a = 0;a<ai_amount;a++)
                        {
                            if(selected_aiCheckbox[a])
                            {
                                icon.setText("<html><font color = 'red'><font size = +1>Choose Difficulty</font></html>");
                                a = ai_amount;
                            }else
                            {
                                icon.setText("");
                            }
                        }
                        optionen.remove(difficulty_slider[i]);
                        optionen.remove(ai_teamSelecter[i]);
                        optionen.revalidate();
                        optionen.repaint();
                    }

                } else {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        aiCheckBox[i].setText("<html><font color = 'blue'>Ai (Selected)</font></html>");
                        if(i<humanCheckBox.length){
                            connecting[i].remove(humanCheckBox[i]);
                            connecting[i].revalidate();
                            connecting[i].repaint();
                        }
                        Var.selected_aiCheckbox_speicher[i] = true;
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
                            ai_teamSelecter[i].setSelectedItem(team_String[1]);
                            g.gridx = 5;
                            g.gridy = 3+i;
                        optionen.add(ai_teamSelecter[i],g);


                            for(int c =0;c<human_amount;c++)
                            {
                                if(selected_humanCheckbox[c])
                                {
                                    System.out.println(c+" " +"Der Index"+ selected_humanCheckbox[c] +" "+ "Boolean Wert");
                                    icon.setText("<html><font color = 'red'>Login Buttons/Choose Difficulty</font></html>");
                                    c = human_amount;
                                } else {
                                    icon.setText("<html><font color = 'red'><font size = +1>Choose Difficulty</font></html>");
                                }
                            }


                        selected_aiCheckbox[i] = true;

                                difficulty_slider[i] = new JSlider(SwingConstants.HORIZONTAL, 0, 10, Var.difficulty_sliderValue_preGameView[i]);
                                difficulty_slider[i].addChangeListener(this);
                                difficulty_slider[i].setMajorTickSpacing(1);

                                difficulty_slider[i].setPaintLabels(true);
                                difficulty_slider[i].setSnapToTicks(true);
                                Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
                                labelTable.put(0, new JLabel("Hard"));
                                labelTable.put(10, new JLabel("Peanuts"));
                                //Beschriftung eingefügt
                                difficulty_slider[i].setLabelTable(labelTable);
                                difficulty_slider[i].setPreferredSize(new Dimension(buttonWidth, buttonHeigth));
                                difficulty_slider[i].setBackground(MyWindow.backgroundColor);

                                g.gridx = 1;
                                g.gridy = 3 + i;
                                g.gridwidth = 1;
                                g.gridheight = 1;
                                g.weightx = 0;
                                g.insets = new Insets(10, 0, 0, 0);
                            optionen.add(difficulty_slider[i], g);
                    }



                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        if(i<=4){
                            connecting[i].add(humanCheckBox[i],BorderLayout.CENTER);
                            connecting[i].revalidate();
                            connecting[i].repaint();
                        }
                        aiCheckBox[i].setText("<html><font color = 'white'>Ai (Unselected)</font></html>");
                        selected_aiCheckbox[i] = false;
                        //Checken ob noch was ausgewählt ist in der icon-Spalte(Login Button oder Ai-Slider)
                            for(int a = 0;a<selected_aiCheckbox.length;a++) {
                                if (selected_aiCheckbox[a]) {
                                    System.out.println(a + " " + "Das ist der Index wo es true ist");
                                    icon.setText("<html><font color = 'red'><font size = +1>Choose Difficulty</font></html>");
                                    a=selected_aiCheckbox.length;
                                } else {
                                    icon.setText("");

                                }
                            }
                            for(int l = 0;l<human_amount;l++)
                            {
                                if(selected_humanCheckbox[l])
                                {
                                    System.out.println(l + " " + "Das ist der Index wo es true ist");
                                    icon.setText("<html><font color = 'red'><font size = +1>Login Buttons</font></html>");
                                    l=selected_aiCheckbox.length;
                                } else {
                                    icon.setText("");
                                }
                            }
                            if(difficulty_slider[i] != null && ai_teamSelecter[i] != null){
                                optionen.remove(difficulty_slider[i]);
                                optionen.remove(ai_teamSelecter[i]);
                                optionen.repaint();
                                optionen.revalidate();
                            }
                    }

                }
            }
        }

        for(int i = 0;i<humanCheckBox.length;i++)
        {
            if(e.getSource() == humanCheckBox[i])
            {
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    Var.selected_humanCheckbox_speicher[i] = false;
                    selected_humanCheckbox[i] =false;
                    //Reactivating ai checkbox
                        connecting[i].add(aiCheckBox[i],BorderLayout.NORTH);
                        connecting[i].revalidate();
                        connecting[i].repaint();
                    //Back to beginning
                    humanCheckBox[i].setText("<html><font color = 'white'>Humanplayer (Unselected)</font></html>");


                   for(int a = 0;a<selected_aiCheckbox.length;a++) {
                        if (selected_aiCheckbox[a]) {
                            System.out.println(a + " " + "Das ist der Index wo es true ist (LoginButton)");
                            icon.setText("<html><font color = 'red'><font size = +1>Choose Difficulty</font></html>");
                            a=selected_aiCheckbox.length;
                        } else {
                            for(int l = 0;l<human_amount;l++)
                            {
                                if(selected_humanCheckbox[l])
                                {
                                    System.out.println(l + " " + "Das ist der Index wo es true ist");
                                    icon.setText("<html><font color = 'red'><font size = +1>Login Buttons</font></html>");
                                    l=selected_aiCheckbox.length;
                                } else {
                                    icon.setText("");
                                }
                            }
                        }
                    }
                    if(human_teamSelecter[i] != null) {
                        optionen.remove(human_teamSelecter[i]);
                    }
                   if(Var.login_profils[i] == null)
                   {
                       optionen.remove(login_human[i]);
                       optionen.revalidate();
                       optionen.repaint();
                   }

                    for(int a = i;a<Var.login_profils.length-1;a++)
                    {
                        Var.login_profils[a] = Var.login_profils[i+1];
                    }
                    Var.login_profils[Var.login_profils.length-1] = null;
                }



                if(e.getStateChange() == ItemEvent.SELECTED) {
                    Var.selected_humanCheckbox_speicher[i] = true;
                    selected_humanCheckbox[i] = true;
                        //Deactivating ai checkbox
                            connecting[i].remove(aiCheckBox[i]);
                            connecting[i].revalidate();
                            connecting[i].repaint();

                     //Team Selecter
                            human_teamSelecter[i] = new JComboBox();
                            human_teamSelecter[i].setBackground(MyWindow.backgroundColor);
                            human_teamSelecter[i].setBorder(BorderFactory.createRaisedBevelBorder());
                            human_teamSelecter[i].addActionListener(this);
                            human_teamSelecter[i].setPreferredSize(new Dimension(buttonWidth,buttonHeigth));
                            for(String s : team_String)
                            {
                                human_teamSelecter[i].addItem(s);
                            }
                            human_teamSelecter[i].setSelectedItem(team_String[1]);
                            g.gridx = 5;
                            g.gridy = 3+i;
                        optionen.add(human_teamSelecter[i],g);
                    //Adding Login Button
                    for(int c =0;c<ai_amount;c++)
                    {
                        if(selected_aiCheckbox[c])
                        {
                            System.out.println(c+" " +"Der Index"+ selected_aiCheckbox[c] +" "+ "Boolean Wert123123");
                            icon.setText("<html><font color = 'red'>Login Buttons/Choose Difficulty</font></html>");
                            c = ai_amount;
                        } else {
                            icon.setText("<html><font color = 'red'><font size = +1>Login Buttons</font></html>");
                        }
                    }
                        humanCheckBox[i].setText("<html><font color = 'blue'>Please connect a profile </font></html>");

                        login_human[i] = new MyButton("KnopfProfilLoginMetallic1.png","Press to connect a profile");
                        login_human[i].addActionListener(e1 -> {new LogInModel(true);});
                        g.gridx = 1;
                        g.gridy = 3 + i;
                        g.gridwidth = 1;
                        g.gridheight = 1;
                        g.weightx = 0;
                        g.insets = new Insets(10, 0, 0, 0);
                    optionen.add(login_human[i],g);
                    optionen.repaint();
                    optionen.revalidate();
                    //new LogInModel(true);
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for(int i= 0; i<difficulty_slider.length;i++)
        {
            if(e.getSource() == difficulty_slider[i]){
                Var.difficulty_sliderValue_preGameView[i] = difficulty_slider[i].getValue();
                if(selected_aiCheckbox[i])
                {
                    difficulty_value_Ai[i] = difficulty_slider[i].getValue();
                    System.out.println(difficulty_value_Ai[i]+ "This is its Index"+i);
                }
            }
        }
        //RGB Slider Changed -> MapImage shall change
            if(e.getSource() == red)
            {
                    Var.redtemp = red.getValue(); // Speichern von Wert

                    //Map ändern
                    foreground = new Color(red.getValue(),green.getValue(),blue.getValue());

                    mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                    map_loocking.setIcon(new ImageIcon(mapImage));

            }


            if(e.getSource() == blue)
            {
                    Var.bluetemp = blue.getValue();

                    foreground = new Color(red.getValue(),green.getValue(),blue.getValue());

                    mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                    map_loocking.setIcon(new ImageIcon(mapImage));
            }


            if(e.getSource() == green)
            {
                    Var.greentemp = green.getValue();

                    foreground = new Color(red.getValue(),green.getValue(),blue.getValue());

                    mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                    map_loocking.setIcon(new ImageIcon(mapImage));
            }


        //RGB Background_Slider
            if(e.getSource() == red_b)
            {
                Var.red_btemp = red_b.getValue();

                background = new Color(red_b.getValue(),green_b.getValue(),blue_B.getValue());

                mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                map_loocking.setIcon(new ImageIcon(mapImage));

            }


            if(e.getSource() == green_b)
            {
                Var.green_btemp = green_b.getValue();

                background = new Color(red_b.getValue(),green_b.getValue(),blue_B.getValue());

                mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                map_loocking.setIcon(new ImageIcon(mapImage));

            }


            if(e.getSource() == blue_B)
            {
                Var.blue_btemp = blue_B.getValue();

                background = new Color(red_b.getValue(),green_b.getValue(),blue_B.getValue());

                mapImage = preGameModel.mapSelectingAction(mapSelectter.getSelectedIndex()+1,foreground,background).getScaledInstance(buttonWidth,buttonHeigth,Image.SCALE_SMOOTH);
                map_loocking.setIcon(new ImageIcon(mapImage));

            }
    }
}
