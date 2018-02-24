package com.example.jan_c.themazerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.RadioButton;

public class RoutesKiezen extends AppCompatActivity {
RadioButton EenkmRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_kiezen);
        EenkmRadioButton = (RadioButton) findViewById(R.id.EenkmRadioButton);

    }

}

