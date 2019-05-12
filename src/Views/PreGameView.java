package Views;

import Model.PreGameModel;
import Model.SinglePlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGameView extends JPanel implements ActionListener{
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    JComboBox gameSettingsTest;

    String [] optionen = {"SpielOptionen","UltraGG","SALZKÖNIGREICH", "Unendlich Munition","Unendlich Sprit"};

    ImageIcon gameSettingsImg,startImg,backImg;

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;
        this.setBackground(MyWindow.backgroundColor);

        erzeugenOverlay();
    }

    private void erzeugenOverlay()
    {
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

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(gameSettings,g);

        //Initialisierung/Erzeugen des Inhalts
            back = new MyButton ("KnopfZurückMetallic1.png","BACK", backImg);
            back.addActionListener(this);
            //Einstellen von der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

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

            g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

            g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

            g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

            g.gridy = 3;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBag
        gameSettingsTest.addActionListener(this);
        gameSettingsTest.setPreferredSize(new Dimension(250,100));
        this.add(gameSettingsTest,g);

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
}
