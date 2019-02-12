package com.example.jorge.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    protected void changeWindow(View view) {
        switch( view.getId() ) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.settings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.getq:
                Intent intent3 = new Intent(this, QuotationActivity.class);
                startActivity(intent3);
                break;
            case R.id.favouriteq:
                Intent intent4 = new Intent(this, FavouriteActivity.class);
                startActivity(intent4);
                break;

        }
    }
}
