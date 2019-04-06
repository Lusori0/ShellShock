package Model;

import Views.ProfilView;
import Window.MyWindow;
import javax.swing.JOptionPane;

import java.io.*;


public class ProfilModel {

    private ProfilView profilView;

    public ProfilModel(){

        String name = "Default Tom";
        int level = 0;


            try {

                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/profil.bin"));
                Profil profilRead = (Profil) objectInputStream.readObject();
                objectInputStream.close();

                level = profilRead.getLevel();
                name = profilRead.getName();
            }

            catch(IOException | ClassNotFoundException e){
                System.out.println("Read Fehler: " + e);
            }




        profilView = new ProfilView(this, name, level);

        MyWindow.setContent(profilView);
    }



    public void backAction(){
        MainMenuModel mainMenuModel = new MainMenuModel();
    }
    public void profilLoeschenAction(){
        int response = JOptionPane.showConfirmDialog(null,"Willst du wirklich dein Profil löschen. Dein ganzer Fortschritt geht verloren",
                "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(response == JOptionPane.YES_OPTION) {
            File file = new File("data/profil.bin");
            file.delete();
            MainMenuModel mainMenuModel = new MainMenuModel();
            MyWindow.makeNewProfile();
        }

    }
}
