package com.example.jorge.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;


public class QuotationActivity extends AppCompatActivity {
ImageButton button;
TextView text,text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
    button=(ImageButton)findViewById(R.id.imageButton);
    text=(TextView)findViewById(R.id.textView3);
    text2=(TextView)findViewById(R.id.textView);
    View view3;
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text.setText("Sample author");
            text.setText("Sample quotation");
        }
    });
    }
}
