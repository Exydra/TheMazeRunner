package com.example.jan_c.themazerunner;


import java.util.concurrent.ExecutionException;

public class Aanmelden   {
    String _email;
    String _password;
    Boolean BooleanToast = false;
    Loper loper;
    AanmeldenUitlezen aanmeldenUitlezen;
    private Aanmelden(){
        loper = new Loper();
    }

    static Aanmelden instance = null;

    public static Aanmelden getInstance()  {
        if(instance == null) {
            instance = new Aanmelden();
        }
        return instance;
    }
 public void doAanmeldenUitlezen(){
     instance.aanmeldenUitlezen = new AanmeldenUitlezen();
     try {
         instance.aanmeldenUitlezen.execute().get();
     } catch (InterruptedException e) {
         e.printStackTrace();
     } catch (ExecutionException e) {
         e.printStackTrace();
     }
 }
 }



