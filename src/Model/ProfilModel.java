package Model;

import Views.ProfilView;



public class ProfilModel {

    private ProfilView profilView;

    public ProfilModel(){
        String name = "Default Tom";

        profilView = new ProfilView(this, name);
    }
}
