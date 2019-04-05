package Model;

import Views.ProfilView;
import Window.MyWindow;

import java.io.*;


public class ProfilModel {

    private ProfilView profilView;

    public ProfilModel(){
        String name = "Default Tom";

        File f = new File("data/profil.bin");
        if(f.exists() && !f.isDirectory()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/profil.bin"));

                Profil profilRead = (Profil) objectInputStream.readObject();
                objectInputStream.close();
                name = profilRead.getName();
            }
            catch(IOException | ClassNotFoundException e){
                System.out.println("Read Fehler: " + e);
            }
        }
        else{
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/profil.bin"));
                Profil profil = new Profil("Tom");
                objectOutputStream.writeObject(profil);
                objectOutputStream.close();
            }
            catch(IOException e){
                System.out.println("Write Fehler: " + e);
            }
        }




        profilView = new ProfilView(this, name);

        MyWindow.setContent(profilView);
    }

    public void backAction(){

    }
}
