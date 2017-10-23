package com.example.nick.geocalc;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final int VICE_SELECTION = 1;
    private String currentD = "Kilometers";
    private String currentB = "Degrees";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText longOne = (EditText) findViewById(R.id.longOne);
        EditText latOne = (EditText) findViewById(R.id.latOne);
        EditText longTwo = (EditText) findViewById(R.id.longTwo);
        EditText latTwo = (EditText) findViewById(R.id.latTwo);

        TextView distance = (TextView) findViewById(R.id.distance);
        TextView bearing = (TextView) findViewById(R.id.bearing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button calc = (Button) findViewById(R.id.calc);
        calc.setOnClickListener(v -> {
            if (latOne.getText().toString().isEmpty() ||
                    latTwo.getText().toString().isEmpty() ||
                    longOne.getText().toString().isEmpty() ||
                    longOne.getText().toString().isEmpty()) {
                return;
            }
            double latOnedis = Double.parseDouble(latOne.getText().toString());
            double latTwodis = Double.parseDouble(latTwo.getText().toString());
            double longOnedis = Double.parseDouble(longOne.getText().toString());
            double longTwodis = Double.parseDouble(longTwo.getText().toString());
            Location loc1 = new Location("");
            loc1.setLatitude(latOnedis);
            loc1.setLongitude(longOnedis);
            Location loc2 = new Location("");
            loc2.setLatitude(latTwodis);
            loc2.setLongitude(longTwodis);
            double distance1 = loc1.distanceTo(loc2);
            double bearing1 = loc1.bearingTo(loc2);
            distance1 = distance1 / 1000;
            if (!currentD.equals("Kilometers")) {
                distance1 = distance1 * .621371;
            }
            if (!currentB.equals("Degrees")) {
                bearing1 = bearing1 * 17.777778;
            }
            double rDis = (double) Math.round(distance1 * 100) / 100;
            double rBear = (double) Math.round(bearing1 * 100) / 100;
            distance.setText("Distance: " + Double.toString(rDis) + " " + currentD);
            bearing.setText("Bearing: " + Double.toString(rBear) + " " + currentB);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            latOne.setText("");
            latTwo.setText("");
            longOne.setText("");
            longTwo.setText("");
            distance.setText("Distance: ");
            bearing.setText("Bearing: ");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("dist", currentD);
            intent.putExtra("bear", currentB);
            startActivityForResult(intent, VICE_SELECTION);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == VICE_SELECTION) {
            currentD = data.getStringExtra("distUnit");
            currentB = data.getStringExtra("bearUnit");
            Button calc = (Button) findViewById(R.id.calc);
            calc.performClick();
        }
    }
}
