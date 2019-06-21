package Views;

import Model.Profil;
import Model.ProfilModel;
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

public class ProfilView extends JPanel implements ActionListener, ItemListener {

    ProfilModel profilModell;

    String name;

    int level;

    MyButton back,deleteProfil;

    JLabel panzer,heavy,light;

    JCheckBox panzer_box,heavy_box,light_box;

    JLabel locked_heavy,locked_light;

    JPanel panzer_label;

    ImageIcon panzerImg,heavy_tank,light_tank;

    JLabel nameDisplay,levelDisplay;

    public ProfilView (ProfilModel profilModell, String name,int level)
    {
        this.profilModell = profilModell;
        this.setBackground(MyWindow.backgroundColor);
        this.level = level;
        this.name = name;
        erzeugenOverlay();

    }
    //TODO:  Image von beiden Panzern einfügen!
    private void erzeugenOverlay()
    {
        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        //Initialisierung/Erzeugen des Inhalts
            nameDisplay = new JLabel("<html><font size = +5><font color = 'white'>Profilname : <font color='green'><font size = +5>"+name+"</font></html>");
            //Erstellen der Anordnung

                g.gridx = 1;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;
            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(nameDisplay,g);

        //Initialisierung/Erzeugen des Inhalts
            levelDisplay = new JLabel("<html><font size = +5><font color = 'white'>Level : <font color='red'><font size = +5>"+level+"</font></html>");
            //Erstellen der Anordnung

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(levelDisplay,g);

        //Initialisierung/Erzeugen des Inhalts
            panzer = new JLabel();

                panzerImg = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png" );

            panzer.setIcon(panzerImg);
            g.gridy = 2;
            this.add(panzer,g);


            panzer_box = new JCheckBox("<html><font color = 'blue'><font size = +1>Selected</font></html>",true);
            panzer_box.addItemListener(this);
            panzer_box.setBackground(MyWindow.backgroundColor);
                g.gridy = 3;
            this.add(panzer_box,g);

            heavy = new JLabel();

                heavy_tank = new ImageIcon("res/buttons/PanzerHeavyMetallic.png" );

            heavy.setIcon(heavy_tank);
            g.gridx = 0;
            g.gridy = 2;
            this.add(heavy,g);
            if(Var.activeUser.getLevel() >=20) {
                heavy_box = new JCheckBox("<html><font color = 'white'><font size = +1>Not Selected</font></html>", false);
                heavy_box.addItemListener(this);
                heavy_box.setBackground(MyWindow.backgroundColor);
                g.gridy = 3;
                this.add(heavy_box, g);
            } else
            {
                locked_heavy = new JLabel("<html><font color = 'red'><font size = +1>You unlock this tank at Level 20</font></html>");
                locked_heavy.setPreferredSize(new Dimension(panzerImg.getIconWidth()*2,panzerImg.getIconHeight()));
                g.gridy = 3;
                g.gridwidth = 2;
                g.anchor = GridBagConstraints.WEST;
                this.add(locked_heavy,g);
            }

            light = new JLabel();

                light_tank = new ImageIcon("res/buttons/PanzerLightMetallic.png" );

            light.setIcon(light_tank);
            g.gridx = 2;
            g.gridy = 2;
            g.gridwidth = 1;
            this.add(light,g);
            if(Var.activeUser.getLevel() >= 10) {
                light_box = new JCheckBox("<html><font color = 'white'><font size = +1>Not Selected</font></html>", false);
                light_box.addItemListener(this);
                light_box.setBackground(MyWindow.backgroundColor);
                g.gridy = 3;
                this.add(light_box, g);
            } else
            {
                locked_light = new JLabel("<html><font color = 'red'><font size = +1>You unlock this tank at Level 10</font></html>");
                locked_light.setPreferredSize(new Dimension(panzerImg.getIconWidth()*2,panzerImg.getIconHeight()));
                g.gridy = 3;
                g.gridwidth = 3;
                this.add(locked_light,g);
            }

        //Initialisierung/Erzeugen des Inhalts
            deleteProfil = new MyButton("KnopfProfilLöschenMetallic1.png","Profil löschen");
            deleteProfil.addActionListener(this);
            //Erstellen der Anordnung
                g.gridx = 1;
                g.gridy = 4;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid
                g.gridwidth = 1;
            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(deleteProfil,g);


        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton("KnopfZurückMetallic1.png","back");
            back.addActionListener(this);
            //Erstellen der Anordnung

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 5;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(back,g);

    }


    //Aufruf/Schnittstelle mit Manuel-Methoden/Was bei knopfdruck ausgeführt werden soll
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == back)
      {
          profilModell.backAction();
      }

      if(e.getSource() == deleteProfil)
      {
          profilModell.profilLoeschenAction();
      }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == panzer_box)
        {
            if(e.getStateChange()== ItemEvent.DESELECTED)
            {
                panzer_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                panzer_box.setSelected(false);
            }
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                panzer_box.setText("<html><font color = 'blue'><font size = +1>Selected</font></html>");
                panzer_box.setSelected(true);

                light_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                light_box.setSelected(false);

                heavy_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                heavy_box.setSelected(false);
            }
        }




        if(e.getSource() == heavy_box)
        {
            if(e.getStateChange()== ItemEvent.DESELECTED)
            {
                heavy_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                heavy_box.setSelected(false);
            }
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                heavy_box.setText("<html><font color = 'blue'><font size = +1>Selected</font></html>");
                heavy_box.setSelected(true);

                panzer_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                panzer_box.setSelected(false);

                light_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                light_box.setSelected(false);
            }
        }




        if(e.getSource() == light_box)
        {
            if(e.getStateChange()== ItemEvent.DESELECTED)
            {
                light_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                light_box.setSelected(false);
            }
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                light_box.setText("<html><font color = 'blue'><font size = +1>Selected</font></html>");
                light_box.setSelected(true);

                heavy_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                heavy_box.setSelected(false);

                panzer_box.setText("<html><font color = 'white'><font size = +1>Not Selected</font></html>");
                panzer_box.setSelected(false);
            }
        }
    }
}
