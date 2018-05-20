package com.example.jan_c.themazerunner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

public class RoutesKiezen extends AppCompatActivity {
    ArrayList<Parcour> parcours;
    RadioGroup radioGroup;
    Dictionary<Integer, Integer> parcourDictionary;
    Button kiesParcourButton;
    Double[] afstand;
    String[] naam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_kiezen);
       /** kiesParcourButton = findViewById(R.id.KiesParcourButton);
        kiesParcourButton.setOnClickListener(new kiesParcourButtonClick());
        */
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
        ListView listView = (ListView) findViewById(R.id.routesListview);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        afstand = new Double[parcours.size()];
        naam = new String[parcours.size()];
        for (Integer i = 0; i < parcours.size();i++){
            afstand[i] = parcours.get(i).afstand;
            naam[i] = parcours.get(i).omschrijving;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent kaartIntent = new Intent(getApplicationContext(), Kaart.class);
                kaartIntent.putExtra("parcourID", parcourDictionary.get(position));
                startActivity(kaartIntent);
            }
        });
       // creatRadiobuttons();
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return parcours.size();
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
            convertView = getLayoutInflater().inflate(R.layout.customrouteslistview, null);
            TextView naamTextview = convertView.findViewById(R.id.NaamParcourtextView);
            TextView afstandTextview = convertView.findViewById(R.id.AfstandParcourTextview);
            TextView tijdTextview = convertView.findViewById(R.id.TijdParcourTextView);
            naamTextview.setText(naam[position]);
            afstandTextview.setText(afstand[position].toString() + " km");
            TotaalTijdUitlezen totaalTijdUitlezen = new TotaalTijdUitlezen(parcourDictionary.get(position),Aanmelden.getInstance().loper.loperID);
            try {
                totaalTijdUitlezen.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (totaalTijdUitlezen.error.equals("00:00:00")){
                tijdTextview.setText("/             ");
            }else {
                tijdTextview.setText(totaalTijdUitlezen.tijd.tijd);
            }


            return convertView;
        }
    }
   /** private void creatRadiobuttons(){
        radioGroup = findViewById(R.id.RadioButtonsGroup);
        for (Integer i = 0; i<parcours.size();i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(parcours.get(i).omschrijving + " " + parcours.get(i).afstand);
            radioButton.setId(i+1);
            radioGroup.addView(radioButton);
        }
    }
    */
   /** class kiesParcourButtonClick implements View.OnClickListener {
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
    */
}


