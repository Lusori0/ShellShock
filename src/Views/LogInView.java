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

        erzeugenOverlay();
    }
    private  void erzeugenOverlay()
    {
        // Ersellen des Loadouts in Abhängigkeit des Gridbaglayouts
        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //Initialisierung/Erzeugen des Inhalts
            JLabel labelName = new JLabel("<html><font size = +5><font color = 'white'>Username</font></html>");

            //Einstellen von der Anordnung

                g.fill = GridBagConstraints.VERTICAL;//Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

                g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

                g.insets = new Insets(100, 0, 0, 0);// Erzeugen eines Abstandes mit dem nächsten Button

                g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(labelName,g);

        //Initialisierung/Erzeugen des Inhalts
            Font font = new Font("SanSerif", Font.PLAIN,20);

            textFieldName = new JTextField(10);
            textFieldName.setBackground(Color.GRAY);
            textFieldName.setBorder(BorderFactory.createRaisedBevelBorder());
            textFieldName.setFont(font);
            //Einstellen von der Anordnung

                g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(textFieldName,g);

        //Initialisierung/Erzeugen des Inhalts
            JLabel labelPw = new JLabel("<html><font size = +5><font color = 'white'>Password</font></html>");

                g.gridy = 2;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(labelPw,g);

        //Initialisierung/Erzeugen des Inhalts
            textFieldPw = new JPasswordField(10);
            textFieldPw.setBackground(Color.GRAY);
            textFieldPw.setBorder(BorderFactory.createRaisedBevelBorder());
            textFieldPw.setFont(font);
            //Einstellen von der Anordnung

                g.gridy = 3;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(textFieldPw,g);


        //Initialisierung/Erzeugen des Inhalts
            ImageIcon singleplayerImg = new ImageIcon();

            submit = new MyButton("KnopfProfilLoginMetallic1.png", "LogIn", singleplayerImg);
            submit.addActionListener(this);
            //Einstellen von der Anordnung


                g.gridy = 4;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(submit, g);

        //Initialisierung/Erzeugen des Inhalts
            createProfil = new MyButton("KnopfProfilErstellenMetallic1.png", "CreateNewAccount", singleplayerImg);
            createProfil.addActionListener(this);
            //Einstellen von der Anordnung


                g.gridy = 5;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

            //Einfügen der Inhalte in Abhängigkeit der GridBag
            this.add(createProfil, g);
    }

    //Aufruf/Schnittstelle mit Manuel-Methoden/Was bei knopfdruck ausgeführt werden soll
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit && !textFieldName.getText().isEmpty() && !textFieldPw.getText().isEmpty()) {
            logInModel.LogInAction(textFieldName.getText(), textFieldPw.getText());
            System.out.println(textFieldPw.getText());
        }
        if(actionEvent.getSource() == createProfil && !textFieldName.getText().isEmpty() && !textFieldPw.getText().isEmpty()){
            logInModel.makeNewProfileAction(textFieldName.getText(), textFieldPw.getText());
        }
    }
}
