package com.example.jorge.app1.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jorge.app1.Databases.RoomDatabaseHelper;
import com.example.jorge.app1.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
      boolean firstrun   = sharedPrefs.getBoolean("first_run",true);
        if(firstrun){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    RoomDatabaseHelper.getInstance(getApplicationContext()).dao().getAllQuotation();
                }
            }).start();
            sharedPrefs.edit().putBoolean("first_run",false).apply();

        }
    }

    protected void changeWindow(View v) {

        Intent intent = null;

        switch( v.getId() ) {
            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.getq:
                intent = new Intent(this, QuotationActivity.class);
                break;
            case R.id.favouriteq:
                intent = new Intent(this, FavouriteActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
