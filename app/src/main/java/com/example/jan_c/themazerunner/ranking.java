package com.example.jan_c.themazerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
    String[] tijd;
    String[] naam;
    ArrayList<Tijd> klassement;
    Integer klassementLengte;

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
                ///position = id
                KlassementUitlezen klassementUitlezen = new KlassementUitlezen(parcourDictionary.get(position));
                try {
                    klassementUitlezen.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                klassement = klassementUitlezen.klassement;
               tijd = new String[klassement.size()];
               naam = new String[klassement.size()];
                for (Integer i = 0; i < klassement.size();i++){
                    tijd[i] = klassement.get(i).tijd;
                    naam[i] = klassement.get(i).naam;
                }
                klassementLengte = klassement.size();
                 ListView theListView = (ListView) findViewById(R.id.theListView);
               CustomAdapter customAdapter = new CustomAdapter();
                theListView.setAdapter(customAdapter);

                 Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is geselecteerd.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return klassementLengte;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlistview,null);
            TextView tijdTextView = (TextView)convertView.findViewById(R.id.TijdtextView);
            TextView naamTextView = (TextView)convertView.findViewById(R.id.NaamtextView);
            TextView rankingTextView = (TextView)convertView.findViewById(R.id.RankingtextView);

            naamTextView.setText(naam[position]);
            tijdTextView.setText(tijd[position]);
            rankingTextView.setText(position);

            return convertView;
        }
    }
}