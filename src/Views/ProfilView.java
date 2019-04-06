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

        this.name = name;
        this.level = level;

        this.setBackground(MyWindow.backgroundColor);

        deleteProfil = new MyButton("","Profil löschen",deleProfilImg);
        deleteProfil.addActionListener(this);
       // deleProfilImg = new ImageIcon("");
        //deleteProfil.setIcon(deleProfilImg);
        this.add(deleteProfil);

        back = new MyButton("KnopfZurückMetallic1.png","back",backImg);
        back.addActionListener(this);
        // backImg = new ImageIcon("");
        //back.setIcon(backImg);
        this.add(back);

        nameDisplay = new JLabel("Profilname: "+"\t"+name);
        levelDisplay = new JLabel("Level :"+"\t"+level );

        this.add(nameDisplay);
        this.add(levelDisplay);
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
