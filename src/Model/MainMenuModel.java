package Model;

import Views.MainMenuView;
import Window.MyWindow;
import Window.Var;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainMenuModel {


    public MainMenuModel(){

        MyWindow.setContent(new MainMenuView(this));
    }
    //Button actions

    public void singlePlayerAction(){
        new SinglePlayerModel();
    }

    public void multiplayerAction(){
        new MultiPlayerModel();
    }

    public void einstellungenAction(){
        new SettingsModel();
    }

    public void profilAction(){
        new ProfilModel();
    }

    public void exitAction(){
        saveProfile(Var.activeUser);
        System.exit(0);
    }

    //method for saving user
    public void saveProfile(Profil profil){
        new File("data/").mkdirs();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + profil.getName() + ".bin"));
            objectOutputStream.writeObject(profil);
            objectOutputStream.close();

        } catch (IOException e) {
            System.out.println("Write error while saving game: " + e);
        }
    }
}
