package com.example.jorge.app1.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.jorge.app1.Databases.QuotationSqlHelper;
import com.example.jorge.app1.R;


public class QuotationActivity extends AppCompatActivity {

    boolean newQuotation = true;
    boolean addQuotation = false;

    ProgressBar progressBar = null;
    TextView tvQuote;
    TextView tvAuthor;

    int fakeNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        progressBar = (ProgressBar) findViewById(R.id.pbQuotation);
        tvQuote = ((TextView) findViewById(R.id.tvQuotation));
        tvAuthor = ((TextView) findViewById(R.id.tvAuthor));

        if (savedInstanceState == null) {

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String name = sharedPrefs.getString("prefs_name", getResources().getString(R.string.nameless));
            tvQuote.setText(String.format(getResources().getString(R.string.greetings), name));
        }

        else {

            tvQuote.setText(savedInstanceState.getString("text"));
            tvAuthor.setText(savedInstanceState.getString("author"));
            addQuotation = savedInstanceState.getBoolean("add");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("text", tvQuote.getText().toString());
        outState.putString("author", tvAuthor.getText().toString());
        outState.putBoolean("add", addQuotation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotation, menu);
        menu.findItem(R.id.action_refresh).setVisible(newQuotation);
        menu.findItem(R.id.action_add).setVisible(newQuotation && addQuotation);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh:

                tvQuote.setText(String.format(getResources().getString(R.string.sample_quotation), fakeNumber));
                tvAuthor.setText(String.format(getResources().getString(R.string.sample_author), fakeNumber));
                fakeNumber++;

                newQuotation = true;
                addQuotation = true;

                supportInvalidateOptionsMenu();

                return true;

            case R.id.action_add:

                QuotationSqlHelper.getInstance(this).addQuotation(
                        tvQuote.getText().toString(), tvAuthor.getText().toString());

                addQuotation = false;

                supportInvalidateOptionsMenu();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        fakeNumber = PreferenceManager.getDefaultSharedPreferences(this).getInt("fakeNumber", 0);
        super.onResume();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt("fakeNumber", fakeNumber);
        editor.apply();
        super.onPause();
    }
}
