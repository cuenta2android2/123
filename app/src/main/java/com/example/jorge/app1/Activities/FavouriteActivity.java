package com.example.jorge.app1.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jorge.app1.Databases.QuotationSqlHelper;
import com.example.jorge.app1.Databases.RoomDatabaseHelper;
import com.example.jorge.app1.R;
import com.example.jorge.app1.Pojo.Quotation;
import com.example.jorge.app1.Tasks.FavouriteQuotationAsyncClass;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    List<HashMap<String, String>> hashMapList;
    List<Quotation> lista;
    SimpleAdapter adapter;
    ListView favouriteListView;
    String dbpref;
    boolean clearAllQuotations;
    Context context;
    int selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        dbpref = sharedPrefs.getString("prefs_database", "");
        FavouriteQuotationAsyncClass task = new FavouriteQuotationAsyncClass(this);
        task.execute(PreferenceManager.getDefaultSharedPreferences(this).getString("prefs_database", "Room") == "Room");
context=this;
    }

    public void showQuotations(List<Quotation> list) {
        // hashMapList.addAll(QuotationSqlHelper.getInstance(this).getQuotations());
        lista = list;

        favouriteListView = (ListView) findViewById(R.id.listViewFavQuotes);

        hashMapList = new ArrayList<>();
        for (Quotation q : list) {
            HashMap<String, String> aux = new HashMap<String, String>();
            aux.put("quote", q.quoteText);
            aux.put("author", q.quoteAuthor);
            hashMapList.add(aux);
        }


        adapter = new SimpleAdapter(this, hashMapList, R.layout.quotation_list_row,
                new String[]{"quote", "author"}, new int[]{R.id.tvQuote, R.id.tvAuthor});

        favouriteListView.setAdapter(adapter);

        favouriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String author;
                try {
                    author = URLEncoder.encode(hashMapList.get(position).get("author"), "UTF-8");
                    if (!author.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(
                                Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search=" + author));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.anonymous, Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        favouriteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.confirmation_delete);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    String text=hashMapList.get(selectedItem).get("quote");
                    Quotation q=lista.get(selectedItem);
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dbpref == "Room") {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Include here the code to access the database
                                    RoomDatabaseHelper.getInstance(getApplicationContext()).dao().deleteQuotation(q);
                                }
                            }).start();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Include here the code to access the database
                                    QuotationSqlHelper.getInstance(getApplicationContext()).deleteQuotation(text);
                                }
                            }).start();
                        }

                        hashMapList.remove(selectedItem);
                        adapter.notifyDataSetChanged();

                        if (hashMapList.size() == 0) {
                            clearAllQuotations = false;
                            supportInvalidateOptionsMenu();
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();
                return true;
            }
        });

        if (hashMapList.size() > 0) {
            clearAllQuotations = true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite, menu);
        menu.findItem(R.id.action_clear).setVisible(clearAllQuotations);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_clear:


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirmation_clear);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearQuotations(dbpref == "Room");
                        hashMapList.clear();
                        lista.clear();
                        adapter.notifyDataSetChanged();
                        clearAllQuotations = false;
                        supportInvalidateOptionsMenu();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearQuotations(Boolean r) {
        if (r) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    RoomDatabaseHelper.getInstance(getApplicationContext()).dao().deleteAllQuotation();
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    QuotationSqlHelper.getInstance(getApplicationContext()).clearAllQuotations();
                }
            }).start();
        }
    }
}
