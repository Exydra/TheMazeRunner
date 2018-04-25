package com.example.jan_c.themazerunner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

public class RoutesKiezen extends AppCompatActivity {
    ArrayList<Parcour> parcours;
    RadioGroup radioGroup;
    Dictionary<Integer, Integer> parcourDictionary;
    Button kiesParcourButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_kiezen);
        kiesParcourButton = findViewById(R.id.KiesParcourButton);
        kiesParcourButton.setOnClickListener(new kiesParcourButtonClick());
        getParcoursUitlezen getParcoursUitlezen = new getParcoursUitlezen();
        parcourDictionary = new Hashtable<>();
        try {
            getParcoursUitlezen.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
       parcours = getParcoursUitlezen.parcours;
        for (Integer i = 0; i<parcours.size(); i++){
            parcourDictionary.put(i,parcours.get(i).parcourID);
        }
        creatRadiobuttons();
    }
    private void creatRadiobuttons(){
        radioGroup = findViewById(R.id.RadioButtonsGroup);
        for (Integer i = 0; i<parcours.size();i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(parcours.get(i).omschrijving + " " + parcours.get(i).afstand);
            radioButton.setId(i+1);
            radioGroup.addView(radioButton);
        }
    }
    class kiesParcourButtonClick implements View.OnClickListener {
        public void onClick(View view) {
                Intent kaartIntent = new Intent(getApplicationContext(), Kaart.class);
                Integer idOfSelected = -1;
                idOfSelected = radioGroup.getCheckedRadioButtonId();
                if (idOfSelected == -1) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RoutesKiezen.this);
                    builder1.setMessage("U moet een parcour kiezen");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    idOfSelected = radioGroup.getCheckedRadioButtonId();
                    kaartIntent.putExtra("parcourID", parcourDictionary.get(idOfSelected - 1));
                    startActivity(kaartIntent);
                }
        }
    }
}


