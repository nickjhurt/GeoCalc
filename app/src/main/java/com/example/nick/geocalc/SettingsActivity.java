package com.example.nick.geocalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {
    private String selectionDist = "Kilometers";
    private String selectionBear = "Degrees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent payload = getIntent();
        if (payload.hasExtra("dist")){
            selectionDist = payload.getStringExtra("dist");
        }
        if (payload.hasExtra("bear")){
            selectionBear = payload.getStringExtra("bear");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("distUnit", selectionDist);
                intent.putExtra("bearUnit", selectionBear);
                setResult(MainActivity.VICE_SELECTION,intent);
                finish();
            }
        });

        Spinner spinnerDist = (Spinner) findViewById(R.id.spinnerDist);
        Spinner spinnerBear = (Spinner) findViewById(R.id.spinnerBear);

        ArrayAdapter<CharSequence> adapterDist = ArrayAdapter.createFromResource(this,
                R.array.distUnits, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterBear = ArrayAdapter.createFromResource(this,
                R.array.bearUnits, android.R.layout.simple_spinner_item);
        adapterDist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDist.setAdapter(adapterDist);
        int spinDistInitPosition = adapterDist.getPosition(selectionDist);
        spinnerDist.setSelection(spinDistInitPosition);
        spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionDist = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        adapterBear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBear.setAdapter(adapterBear);
        int spinBearInitPosition = adapterBear.getPosition(selectionBear);
        spinnerBear.setSelection(spinBearInitPosition);
        spinnerBear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionBear = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });
    }

}
