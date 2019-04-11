package Model;


import Views.ProfilView;
import Window.MyWindow;
import javax.swing.JOptionPane;

import java.io.*;


public class ProfilModel {

    public ProfilModel(){

        String name = "Default Tom";
        int level = 0;


            try {
                //Laden der Profildatei und setzen der Atribute
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/" + MyWindow.getActiveUser() + ".bin"));
                Profil profilRead = (Profil) objectInputStream.readObject();
                objectInputStream.close();

                level = profilRead.getLevel();
                name = profilRead.getName();
            }

            catch(IOException | ClassNotFoundException e){
                System.out.println("Read Fehler: " + e);
            }



        //Neue View mit Attributen erstellen

        MyWindow.setContent(new ProfilView(this, name, level));
    }



    public void backAction(){
        new MainMenuModel();
    }

    public void profilLoeschenAction(){
        int response = JOptionPane.showConfirmDialog(null,"Willst du wirklich dein Profil löschen. Dein ganzer Fortschritt geht verloren",
                "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        if(response == JOptionPane.YES_OPTION) {
            File file = new File("data/" + MyWindow.getActiveUser() + ".bin");
            file.delete();
            new MainMenuModel();
        }

    }

}
