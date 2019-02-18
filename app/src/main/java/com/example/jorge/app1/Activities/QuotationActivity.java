package com.example.jorge.app1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import com.example.jorge.app1.R;


public class QuotationActivity extends AppCompatActivity {

    boolean newQuotation = true;
    boolean addQuotation = false;

    ProgressBar progressBar = null;
    TextView tvQuote;
    TextView tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        progressBar = (ProgressBar) findViewById(R.id.pbQuotation);
        tvQuote = ((TextView) findViewById(R.id.tvQuotation));
        tvAuthor = ((TextView) findViewById(R.id.tvAutor));

        tvQuote.setText(
                String.format(getResources().getString(R.string.greetings),
                        getResources().getString(R.string.nameless)));
    }

    public void refresh(View v) {
        tvQuote.setText(R.string.sample_quotation);
        tvAuthor.setText(R.string.sample_author);
    }
}
