package Model;

import Views.LogInView;
import Window.*;

import javax.swing.*;
import java.io.*;

import java.util.*;

public class LogInModel {

    private LogInView logInView;

    public LogInModel(boolean from_Game){
        logInView = new LogInView(this);
        if(from_Game)
        {
            logInView.erzeugenOverlayGame();
            MyWindow.setContent(logInView);
        }else
        {
            logInView.erzeugenOverlay();
            MyWindow.setContent(logInView);
        }


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
                System.out.println("Read error: " + e);
            }
            //Passwortabgleich
            if (profilRead.getPassword().equals(password)) {
                Var.setActiveUser(profilRead);
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
        new File("data/").mkdirs();
        //Überprüfen ob Nutzer schon existiert
        if(!f.exists()) {
            Profil profil = null;
            try {
                List<Integer> weaponsListTemp = new ArrayList<>();
                for(int i =0;i < 5;i++) {
                    weaponsListTemp.add(1);
                    weaponsListTemp.add(5);
                    weaponsListTemp.add(18);
                    weaponsListTemp.add(12);
                    weaponsListTemp.add(21);
                }
                //erstellen der Profil.bin datei
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + name + ".bin"));


                profil = new Profil(name, 1, password,0,weaponsListTemp,0.5f,0.5f,1);

                objectOutputStream.writeObject(profil);
                objectOutputStream.close();

            } catch (IOException e) {
                System.out.println("Write error: " + e);
            }
            Var.setActiveUser(profil);
            new MainMenuModel();
        }
        else{
            JOptionPane.showMessageDialog(logInView,"This username is already taken");
        }

    }
    //Game Login Profile
    public void LogInAction_Game(String name , String password){

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
                System.out.println("Read error: " + e);
            }
            //Passwortabgleich
            if (profilRead.getPassword().equals(password)) {
                for(int i=0;i<Var.login_profils.length;i++)
                {
                    if( Var.login_profils[i] != null && name.equals(Var.login_profils[i].getName()) ) {
                        JOptionPane.showMessageDialog(logInView, "Already activated, can't connect two times the same profile!");
                        i = Var.login_profils.length;
                    }else
                    {
                        JOptionPane.showMessageDialog(logInView, "Connecting Profile with Player!");
                        i = Var.login_profils.length;
                        new PreGameModel(GameType.MULTI_LOCAL, false, true, profilRead);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(logInView, "Wrong Password");
            }
        }
        else{
            JOptionPane.showMessageDialog(logInView,"Wrong Username");
        }
    }

    public void makeNewProfileAction_Game(String name,String password){
        File f = new File("data/" + name + ".bin");
        new File("data/").mkdirs();
        Profil profil = null;
        //Überprüfen ob Nutzer schon existiert
        if(!f.exists()) {
            try {
                List<Integer> weaponsListTemp = new ArrayList<>();
                for(int i =0;i < 5;i++) {
                    weaponsListTemp.add(1);
                    weaponsListTemp.add(5);
                    weaponsListTemp.add(18);
                    weaponsListTemp.add(12);
                    weaponsListTemp.add(21);
                }
                //erstellen der Profil.bin datei
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + name + ".bin"));


                profil = new Profil(name, 1, password,0,weaponsListTemp,0.5f,0.5f,1);

                objectOutputStream.writeObject(profil);
                objectOutputStream.close();

            } catch (IOException e) {
                System.out.println("Write error: " + e);
            }
            new PreGameModel(GameType.MULTI_LOCAL,false,true,profil);
        }
        else{
            JOptionPane.showMessageDialog(logInView,"This username is already taken");
        }
    }

}
