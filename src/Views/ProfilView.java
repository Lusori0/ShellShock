package Views;

import Model.ProfilModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilView extends JPanel implements ActionListener {

    ProfilModel profilModell;

    String name;

    MyButton back;

    JLabel nameDisplay;

    public ProfilView (ProfilModel profilModel, String name)
    {
        this.profilModell = profilModel;

        this.name = name;

        back = new MyButton("BACK");
        back.addActionListener(this);
        this.add(back);

        nameDisplay = new JLabel(name);

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
