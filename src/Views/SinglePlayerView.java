package Views;

import Model.SinglePlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerView extends JPanel implements ActionListener {

    SinglePlayerModel singlePlayerModel;

    MyButton gegenKi,sandbox,back;

    ImageIcon gegenKiImg,sandboxImg,backImg;

    public SinglePlayerView(SinglePlayerModel singlePlayerModel)
    {
        this.singlePlayerModel = singlePlayerModel;
        this.setBackground(MyWindow.backgroundColor);

        erzeugenOverlay();
    }

    private void erzeugenOverlay()
    {
        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Initialisierung/Erzeugen des Inhalts
            gegenKi = new MyButton("","GegenKI", gegenKiImg);
            gegenKi.addActionListener(this);
            //Einstellen von der Anordnung
                g.weightx = 0.5;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(gegenKi,g);



        //Initialisierung/Erzeugen des Inhalts
            sandbox = new MyButton("KnopfSandboxMetallic1.png","Sandbox",sandboxImg);
            sandbox.addActionListener(this);

            //Einstellen von der Anordnung
                g.weightx = 0.5;// Festlegung der Größe --> Button bleibt gleich bei 1.0 größer

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets= new Insets(100,0,0,0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(sandbox,g);

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
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gegenKi)
        {
            singlePlayerModel.gegenKiAction();
        }

        if (e.getSource() == sandbox)
        {
            singlePlayerModel.sandboxAction();
        }

        if (e.getSource() == back)
        {
            singlePlayerModel.backAction();
        }
    }

}
