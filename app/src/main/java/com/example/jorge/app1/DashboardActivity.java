package com.example.jorge.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    protected void cambiarVentana(View v) {
        switch( v.toString() ) {
            case "button":
                //...
            case "button1":
                //...
            case "button2":
                //...
            case "button3":
                //...
        }
    }
}
