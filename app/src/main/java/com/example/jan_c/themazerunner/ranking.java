package com.example.jan_c.themazerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ranking extends AppCompatActivity {

    Spinner spinner;
    ArrayList<Parcour> parcours;
    Dictionary<Integer, Integer> parcourDictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        spinner = (Spinner) findViewById(R.id.routeSpinner);
        List<String> list = new ArrayList<>();



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
        for (Integer i = 0; i<parcours.size();i++){
           list.add(parcours.get(i).omschrijving);
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is geselecteerd.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
