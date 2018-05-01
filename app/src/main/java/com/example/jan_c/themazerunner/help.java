package com.example.jan_c.themazerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class help extends AppCompatActivity {

    private TextView antwoord1;
    private TextView antwoord2;
    private TextView antwoord3;
    private TextView antwoord4;
    private TextView antwoord5;
    private TextView antwoord6;
    private Button vraag1;
    private Button vraag2;
    private Button vraag3;
    private Button vraag4;
    private Button vraag5;
    private Button vraag6;
    String huidigeVisibility = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        antwoord1 = (TextView) findViewById(R.id.antvraag1TextView);
        antwoord2 = (TextView) findViewById(R.id.antvraag2TextView);
        antwoord3 = (TextView) findViewById(R.id.antvraag3TextView);
        antwoord4 = (TextView) findViewById(R.id.antvraag4TextView);
        antwoord5 = (TextView) findViewById(R.id.antvraag5TextView);
        antwoord6 = (TextView) findViewById(R.id.antvraag6TextView);
        vraag1 = (Button) findViewById(R.id.vraag1button);
        vraag2 = (Button) findViewById(R.id.vraag2button);
        vraag3 = (Button) findViewById(R.id.vraag3button);
        vraag4 = (Button) findViewById(R.id.vraag4button);
        vraag5 = (Button) findViewById(R.id.vraag5button);
        vraag6=(Button) findViewById(R.id.vraag6button);
        vraag1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (huidigeVisibility.equals("vraag1"))
                {antwoord1.setVisibility(View.INVISIBLE);
                    huidigeVisibility="";}
                else {
               antwoord1.setVisibility(View.VISIBLE);
                antwoord2.setVisibility(View.INVISIBLE);
                antwoord3.setVisibility(View.INVISIBLE);
                antwoord4.setVisibility(View.INVISIBLE);
                antwoord5.setVisibility(View.INVISIBLE);
                antwoord6.setVisibility(View.INVISIBLE);
                    huidigeVisibility = "vraag1";}
            }
        });

        vraag2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (huidigeVisibility.toString()=="vraag2")
                {antwoord2.setVisibility(View.INVISIBLE);
                    huidigeVisibility="";}
                else {
                antwoord1.setVisibility(View.INVISIBLE);
                antwoord2.setVisibility(View.VISIBLE);
                antwoord3.setVisibility(View.INVISIBLE);
                antwoord4.setVisibility(View.INVISIBLE);
                antwoord5.setVisibility(View.INVISIBLE);
                antwoord6.setVisibility(View.INVISIBLE);
                huidigeVisibility = "vraag2";}
            }
        });

        vraag3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (huidigeVisibility.equals("vraag3"))
                {antwoord3.setVisibility(View.INVISIBLE);
                    huidigeVisibility="";}
                else {
                antwoord1.setVisibility(View.INVISIBLE);
                antwoord2.setVisibility(View.INVISIBLE);
                antwoord3.setVisibility(View.VISIBLE);
                antwoord4.setVisibility(View.INVISIBLE);
                antwoord5.setVisibility(View.INVISIBLE);
                antwoord6.setVisibility(View.INVISIBLE);
                    huidigeVisibility = "vraag3";}
            }
        });
        vraag4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (huidigeVisibility.equals("vraag4"))
                {antwoord4.setVisibility(View.INVISIBLE);
                huidigeVisibility="";}
                else {
                antwoord1.setVisibility(View.INVISIBLE);
                antwoord2.setVisibility(View.INVISIBLE);
                antwoord3.setVisibility(View.INVISIBLE);
                antwoord4.setVisibility(View.VISIBLE);
                antwoord5.setVisibility(View.INVISIBLE);
                antwoord6.setVisibility(View.INVISIBLE);
                    huidigeVisibility = "vraag4";}
            }
        });
        vraag5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    if (huidigeVisibility.equals("vraag5"))
                    {antwoord5.setVisibility(View.INVISIBLE);
                        huidigeVisibility="";}
                    else {
                antwoord1.setVisibility(View.INVISIBLE);
                antwoord2.setVisibility(View.INVISIBLE);
                antwoord3.setVisibility(View.INVISIBLE);
                antwoord4.setVisibility(View.INVISIBLE);
                antwoord5.setVisibility(View.VISIBLE);
                antwoord6.setVisibility(View.INVISIBLE);
                huidigeVisibility = "vraag5";}
            }
        });

        vraag6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (huidigeVisibility.equals("vraag6"))
                {antwoord6.setVisibility(View.INVISIBLE);
                    huidigeVisibility="";}
                else {
                    antwoord1.setVisibility(View.INVISIBLE);
                    antwoord2.setVisibility(View.INVISIBLE);
                    antwoord3.setVisibility(View.INVISIBLE);
                    antwoord4.setVisibility(View.INVISIBLE);
                    antwoord5.setVisibility(View.INVISIBLE);
                    antwoord6.setVisibility(View.VISIBLE);
                    huidigeVisibility = "vraag6";}
            }
        });





    }
}
