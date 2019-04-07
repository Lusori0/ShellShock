package Views;

import Model.MainMenuModel;
import Model.Profil;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Window.MyButton;

public class CreateProfil extends JPanel implements ActionListener {

    private JTextField textField;
    private MyButton submit;

    public CreateProfil() {

        this.setBackground(MyWindow.backgroundColor);


        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        JLabel label = new JLabel("<html><font size = +5><font color = 'white'>Gib deinen Namen ein</font></html>");
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 0;
        this.add(label,g);

        Font font = new Font("SanSerif", Font.PLAIN,20);

        textField = new JTextField(10);
        textField.setBackground(Color.GRAY);
        textField.setBorder(BorderFactory.createRaisedBevelBorder());
        textField.setFont(font);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 1;
        this.add(textField,g);



        ImageIcon singleplayerImg = new ImageIcon();

        submit = new MyButton("KnopfSingleplayerMetallic1.png", "submit", singleplayerImg);
        submit.addActionListener(this);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 2;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(submit, g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit && !textField.getText().isEmpty()) {
            String name = textField.getText();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/profil.bin"));

                Profil profil = new Profil(name, 0);
                objectOutputStream.writeObject(profil);
                objectOutputStream.close();

            } catch (IOException e) {
                System.out.println("Write Fehler: " + e);
            }
            System.out.println(name);
            new MainMenuModel();
        }
    }
}
