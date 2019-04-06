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

    MyButton back;

    JLabel nameDisplay,levelDisplay;

    public ProfilView (ProfilModel profilModell, String name,int level)
    {
        this.profilModell = profilModell;

        this.name = name;
        this.level = level;

        back = new MyButton("BACK");
        back.addActionListener(this);
        this.add(back);

        nameDisplay = new JLabel("Profilname: "+name);
        levelDisplay = new JLabel("Level :"+level + "");

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
    }
}
