package Views;

import Model.Profil;
import Model.ProfilModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilView extends JPanel implements ActionListener {

    ProfilModel profilModell;

    String name;

    int level;

    MyButton back,deleteProfil;

    JLabel panzer;

    ImageIcon backImg,deleProfilImg,panzerImg;

    JLabel nameDisplay,levelDisplay;

    public ProfilView (ProfilModel profilModell, String name,int level)
    {
        this.profilModell = profilModell;
        this.setBackground(MyWindow.backgroundColor);

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        this.name = name;
        nameDisplay = new JLabel("Profilname: "+"\t"+name);
        //Erstellen der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(nameDisplay,g);


        this.level = level;
        levelDisplay = new JLabel("Level :"+"\t"+level );
        //Erstellen der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 1;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(levelDisplay,g);





        panzer = new JLabel();
        if(System.getProperty("os.name").contains("Win")) {
            panzerImg = new ImageIcon("res\\buttons\\KnopfSingleplayerMetallic1.png" );
        }
        else{
            panzerImg = new ImageIcon("res/buttons/KnopfSingleplayerMetallic1.png" );
        }
        panzer.setIcon(panzerImg);
        //Erstellen der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 2;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(panzer,g);


        deleteProfil = new MyButton("","Profil löschen",deleProfilImg);
        deleteProfil.addActionListener(this);
        //Erstellen der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 3;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(deleteProfil,g);



        back = new MyButton("KnopfZurückMetallic1.png","back",backImg);
        back.addActionListener(this);
        //Erstellen der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 4;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(back,g);

    }

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
