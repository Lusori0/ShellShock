package Views;

import Model.LogInModel;
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

public class LogInView extends JPanel implements ActionListener {

    private JTextField textFieldName, textFieldPw;
    private MyButton submit, createProfil;
    private LogInModel logInModel;

    public LogInView(LogInModel model){
        logInModel = model;

        this.setBackground(MyWindow.backgroundColor);


        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        JLabel labelName = new JLabel("<html><font size = +5><font color = 'white'>Username</font></html>");
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 0;
        this.add(labelName,g);

        Font font = new Font("SanSerif", Font.PLAIN,20);

        textFieldName = new JTextField(10);
        textFieldName.setBackground(Color.GRAY);
        textFieldName.setBorder(BorderFactory.createRaisedBevelBorder());
        textFieldName.setFont(font);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 1;
        this.add(textFieldName,g);

        JLabel labelPw = new JLabel("<html><font size = +5><font color = 'white'>Password</font></html>");
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 2;
        this.add(labelPw,g);

        textFieldPw = new JTextField(10);
        textFieldPw.setBackground(Color.GRAY);
        textFieldPw.setBorder(BorderFactory.createRaisedBevelBorder());
        textFieldPw.setFont(font);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 3;
        this.add(textFieldPw,g);



        ImageIcon singleplayerImg = new ImageIcon();

        submit = new MyButton("KnopfSingleplayerMetallic1.png", "submit", singleplayerImg);
        submit.addActionListener(this);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 4;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(submit, g);

        createProfil = new MyButton("KnopfSingleplayerMetallic1.png", "submit", singleplayerImg);
        createProfil.addActionListener(this);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets = new Insets(100, 0, 0, 0);
        g.gridy = 5;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(createProfil, g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit && !textFieldName.getText().isEmpty() && !textFieldPw.getText().isEmpty()) {
            logInModel.LogInAction(textFieldName.getText(), textFieldPw.getText());
        }
        if(actionEvent.getSource() == createProfil && !textFieldName.getText().isEmpty() && !textFieldPw.getText().isEmpty()){
            logInModel.makeNewProfileAction(textFieldName.getText(), textFieldPw.getText());
        }
    }
}
