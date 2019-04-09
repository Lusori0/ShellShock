package Views;

import Model.MainMenuModel;
import Window.MyWindow;
import Window.MyButton;
import Views.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainMenuView extends JPanel implements ActionListener {

    private MainMenuModel mainMenuModel;


    private MyButton singelplayer, multiplayer, einstellungen, profil, exit;

    private BufferedImage background;

    private ImageIcon singleplayerImg, multiplayerImg, einstellungenImg, profilImg, exitImg;



    public MainMenuView(MainMenuModel mainMenuModel) {
            this.mainMenuModel = mainMenuModel;

            //Erstellen der Standard Backgrpundcolor
            //this.setBackground(MyWindow.backgroundColor);

            // Erzeugen des Overlays
            erzeugenOverlay();

            //Laden und Skalieren des Hintergrundbildes
            File backgroundFile;
            if(System.getProperty("os.name").contains("Win")) {
                backgroundFile = new File("res\\gameimages\\Menübildschirm.png");
            }
            else{
                backgroundFile = new File("res/gameimages/Menübildschirm.png");
            }
            background =new Background(backgroundFile).backgroundScaling(backgroundFile);

            /*
            try {
                background = ImageIO.read(backgroundFile);
            }
            catch(Exception e){
                System.out.println("Error loading background Image: " + e);
            }

            int imageWidth = background.getWidth();
            int imageHeight = background.getHeight();
            int cropX = (imageWidth -MyWindow.WIDTH) / 2;
            int cropY = (imageHeight - MyWindow.HEIGHT) / 2;

            background = background.getSubimage(cropX, cropY,MyWindow.WIDTH, MyWindow.HEIGHT);
            */

    }


    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background,0,0,this);
    }




    private void erzeugenOverlay()
    {
        // Ersellen des Loadouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();


        //Initialisierung/Erzeugen der Buttons


        singelplayer = new MyButton("KnopfSingleplayerMetallic1.png", "Singleplayer", singleplayerImg);
        singelplayer.addActionListener(this);

        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.gridy = 0;
        g.insets = new Insets(100, 0, 0, 0);
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(singelplayer, g);


        //Initialisierung/Erzeugen der Buttons
        multiplayer = new MyButton("KnopfMultiplayerMetallic1.png", "Mehrspieler", multiplayerImg);
        multiplayer.addActionListener(this);

        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.gridy = 1;
        g.insets = new Insets(100, 0, 0, 0);
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(multiplayer, g);

        //Initialisierung/Erzeugen der Buttons
        einstellungen = new MyButton("KnopfEinstellungenMetallic1.png", "Einstellungen", einstellungenImg);
        einstellungen.addActionListener(this);

        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.gridy = 2;
        g.insets = new Insets(100, 0, 0, 0);
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(einstellungen, g);


        //Initialisierung/Erzeugen der Buttons
        profil = new MyButton("KnopfProfilMetallic1.png", "Profil", profilImg);
        profil.addActionListener(this);

        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.gridy = 3;
        g.insets = new Insets(100, 0, 0, 0);
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(profil, g);

        //Initialisierung/Erzeugen der Buttons
        exit = new MyButton("KnopfExitMetallic1.png", "Exit", exitImg);
        exit.addActionListener(this);

        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;   //request any extra vertical space
        g.gridx = 0;
        g.gridy = 4;
        g.insets = new Insets(100, 0, 0, 0);//aligned with button 2
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(exit, g);



    }







    //Aufruf/Schnittstelle mit Manuel-Methoden
    @Override
    public void actionPerformed (ActionEvent e){
            if (e.getSource() == singelplayer) {
                mainMenuModel.singlePlayerAction();
            }
            if (e.getSource() == multiplayer) {
                mainMenuModel.multiplayerAction();
            }
            if (e.getSource() == einstellungen) {
                mainMenuModel.einstellungenAction();
            }
            if (e.getSource() == profil) {
                mainMenuModel.profilAction();
            }
            if (e.getSource() == exit) {
                mainMenuModel.exitAction();
            }

    }
}
