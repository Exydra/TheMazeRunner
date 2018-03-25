package com.example.jan_c.themazerunner;

import java.util.concurrent.ExecutionException;

/**
 * Created by jan_c on 25/03/2018.
 */

public class Registreren {
    RegistrerenUitschrijven registrerenUitschrijven;
    Loper loper;
   public Registreren(){
       loper = new Loper();
   }
    private static Registreren instance = null;

    public static Registreren getInstance()  {
        if(instance == null) {
            instance = new Registreren ();
        }
        return instance;
    }
    public void doRegistrerenUitschrijven(){
        instance.registrerenUitschrijven = new RegistrerenUitschrijven();
        try {
            instance.registrerenUitschrijven.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
