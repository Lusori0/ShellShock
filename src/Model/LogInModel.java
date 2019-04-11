package Model;

import Views.LogInView;
import Window.MyWindow;

import javax.swing.*;
import java.io.*;

public class LogInModel {

    private LogInView logInView;

    public LogInModel(){
        logInView = new LogInView(this);
        MyWindow.setContent(logInView);

    }

    public void LogInAction(String name , String password){

        Profil profilRead = null;
        File f = new File("data/" + name + ".bin");
        //Überprüfen ob Benuter existiert
        if(f.exists()) {

            try {
                //Laden des gespeicherten Profils
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/" + name + ".bin"));
                profilRead = (Profil) objectInputStream.readObject();
                objectInputStream.close();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Read Fehler: " + e);
            }
            //Passwortabgleich
            if (profilRead.getPassword().equals(password)) {
                MyWindow.setActiveUser(name);
                new MainMenuModel();
            } else {
                JOptionPane.showMessageDialog(logInView, "Wrong Password");
            }
        }
        else{
            JOptionPane.showMessageDialog(logInView,"Wrong Username");
        }
    }

    public void makeNewProfileAction(String name,String password){
        File f = new File("data/" + name + ".bin");
        //Überprüfen ob Nutzer schon existiert
        if(!f.exists()) {
            try {
                //erstellen der Profil.bin datei
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + name + ".bin"));

                Profil profil = new Profil(name, 0, password);
                objectOutputStream.writeObject(profil);
                objectOutputStream.close();

            } catch (IOException e) {
                System.out.println("Write Fehler: " + e);
            }
            MyWindow.setActiveUser(name);
            new MainMenuModel();
        }
        else{
            JOptionPane.showMessageDialog(logInView,"This username is already taken");
        }

    }
}
