package Views;

import Model.Profil;
import Model.ProfilModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilView extends JPanel implements ActionListener {

    ProfilModel profilModell;

    String name;

    int level;

    MyButton back,deleteProfil;

    JLabel panzer;

    JSlider musicbar;

    ImageIcon backImg,deleProfilImg,panzerImg;

    JLabel nameDisplay,levelDisplay;

    public ProfilView (ProfilModel profilModell, String name,int level)
    {
        this.profilModell = profilModell;
        this.setBackground(MyWindow.backgroundColor);
        this.level = level;
        this.name = name;
        erzeugenOverlay();

    }

    private void erzeugenOverlay()
    {
        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        //Initialisierung/Erzeugen des Inhalts
            nameDisplay = new JLabel("<html><font size = +5><font color = 'white'>Profilname : <font color='green'><font size = +5>"+name+"</font></html>");
            //Erstellen der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;
            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(nameDisplay,g);

        //Initialisierung/Erzeugen des Inhalts
            levelDisplay = new JLabel("<html><font size = +5><font color = 'white'>Level : <font color='red'><font size = +5>"+level+"</font></html>");
            //Erstellen der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(levelDisplay,g);

        //Initialisierung/Erzeugen des Inhalts
            panzer = new JLabel();

                panzerImg = new ImageIcon("res/buttons/PanzerDefaultMetallic1.png" );

            panzer.setIcon(panzerImg);
            //Erstellen der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 2;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(panzer,g);

        //Initialisierung/Erzeugen des Inhalts
            deleteProfil = new MyButton("KnopfProfilLöschenMetallic1.png","Profil löschen",deleProfilImg);
            deleteProfil.addActionListener(this);
            //Erstellen der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 3;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Buttons in Abhängigkeit der GridBag
            this.add(deleteProfil,g);


        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton("KnopfZurückMetallic1.png","back",backImg);
            back.addActionListener(this);
            //Erstellen der Anordnung
                g.weightx = 0.5;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 4;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

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


}
