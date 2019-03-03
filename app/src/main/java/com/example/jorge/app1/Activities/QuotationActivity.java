package com.example.jorge.app1.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.jorge.app1.Databases.DaoQuotation;
import com.example.jorge.app1.Databases.QuotationSqlHelper;
import com.example.jorge.app1.Databases.RoomDatabaseHelper;
import com.example.jorge.app1.Pojo.Quotation;
import com.example.jorge.app1.R;
import com.example.jorge.app1.Tasks.FavouriteQuotationAsyncClass;
import com.example.jorge.app1.Tasks.QuotationAsyncClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class QuotationActivity extends AppCompatActivity {

    boolean newQuotation = true;
    boolean addQuotation = false;

    ProgressBar progressBar = null;
    TextView tvQuote;
    TextView tvAuthor;
    String dbpref;
    int fakeNumber = 0;
    List<Quotation> list;
    Quotation quotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        dbpref = sharedPrefs.getString("prefs_database", "");
        progressBar = (ProgressBar) findViewById(R.id.pbQuotation);
        tvQuote = ((TextView) findViewById(R.id.tvQuotation));
        tvAuthor = ((TextView) findViewById(R.id.tvAuthor));
        if (dbpref == "Room") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    list = RoomDatabaseHelper.getInstance(getApplicationContext()).dao().getAllQuotation();

                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    list = QuotationSqlHelper.getInstance(getApplicationContext()).getQuotations();

                }
            }).start();

        }
        if (savedInstanceState == null) {

            String name = sharedPrefs.getString("prefs_name", getResources().getString(R.string.nameless));
            tvQuote.setText(String.format(getResources().getString(R.string.greetings), name));
        } else {

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

    public boolean isConnected() {
        boolean res = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        ;

        return ((info != null) && (info.isConnected()));
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
                if (isConnected()) {
                    QuotationAsyncClass task = new QuotationAsyncClass(this);
                    task.execute();
                }

                return true;

            case R.id.action_add:
                if (dbpref == "Room") {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Include here the code to access the database
                            RoomDatabaseHelper.getInstance(getApplicationContext()).dao().addQuotation(quotation);


                            addQuotation = false;


                            supportInvalidateOptionsMenu();
                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Include here the code to access the database
                            QuotationSqlHelper.getInstance(getApplicationContext()).addQuotation(
                                    quotation.getQuote(), quotation.getAuthor());
                        }
                    }).start();
                    addQuotation = false;


                    supportInvalidateOptionsMenu();
                }


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hiddeActionbar() {
    }

    public void updateWebService(Quotation q) {
        if (q != null) {
            quotation = q;
            tvQuote.setText(String.format(q.quoteText, fakeNumber));
            tvAuthor.setText(String.format(q.quoteAuthor, fakeNumber));
            fakeNumber++;

            newQuotation = true;
            addQuotation = true;

            supportInvalidateOptionsMenu();
        }
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
