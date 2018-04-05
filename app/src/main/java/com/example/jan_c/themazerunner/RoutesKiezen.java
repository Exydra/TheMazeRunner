package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Bundle;
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
            radioGroup.addView(radioButton);
        }
    }
    class kiesParcourButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            Intent Kaart = new Intent(getApplicationContext(), Kaart.class);
            Integer idOfSelected = radioGroup.getCheckedRadioButtonId();
            Kaart.putExtra("parcourID", parcourDictionary.get(idOfSelected-1));
            startActivity(Kaart);
        }}
}


