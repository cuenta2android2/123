package com.example.jorge.app1.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.app1.Databases.QuotationSqlHelper;
import com.example.jorge.app1.R;
import com.example.jorge.app1.Adapters.ListAdapter;
import com.example.jorge.app1.Pojo.Quotation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    List<HashMap<String, String>> hashMapList;
    SimpleAdapter adapter;
    ListView favouriteListView;

    boolean clearAllQuotations;

    int selectedItem;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        context = this;

        favouriteListView = (ListView) findViewById(R.id.listViewFavQuotes);

        hashMapList = new ArrayList<>();
        hashMapList.addAll(QuotationSqlHelper.getInstance(this).getQuotations());

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
                                Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search="
                                        + author));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(context, R.string.anonymous, Toast.LENGTH_SHORT).show();
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QuotationSqlHelper.getInstance(context).deleteQuotation(
                                hashMapList.get(selectedItem).get("quote"));
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

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ListAdapter listAdapter = new ListAdapter(this,R.layout.activity_favourite,getMockQuotations());
        final ListView listViewFavQuotes = findViewById(R.id.listViewFavQuotes);
        listViewFavQuotes.setAdapter(listAdapter);
        listViewFavQuotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String authorName = ((TextView)view.findViewById(R.id.tvAuthor)).getText().toString();
                getAuthorInfo(authorName);
            }
        });
        listViewFavQuotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.deleteDialog);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listAdapter.remove(listAdapter.getItem(position));
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();

                return false;
            }
        });
    }*/

    public List<Quotation> getMockQuotations()
    {
        List<Quotation> result = new ArrayList<>();
        Quotation item;

        item = new Quotation();
        item.setQuote("Think Big");
        item.setAuthor("IMAX");
        result.add(item);

        item = new Quotation();
        item.setQuote("Push button publishing");
        item.setAuthor("Blogger");
        result.add(item);

        item = new Quotation();
        item.setQuote("Beauty outside. Beast inside");
        item.setAuthor("Mac Pro");
        result.add(item);

        item = new Quotation();
        item.setQuote("American by birth. Rebel by choice");
        item.setAuthor("Harley Davidson");
        result.add(item);

        item = new Quotation();
        item.setQuote("Don't be evil");
        item.setAuthor("Google");
        result.add(item);

        item = new Quotation();
        item.setQuote("If you want to impress someone, put him on your Black list");
        item.setAuthor("Johnnie Walker");
        result.add(item);

        item = new Quotation();
        item.setQuote("Live in your world. Play in ours");
        item.setAuthor("Playstation");
        result.add(item);

        item = new Quotation();
        item.setQuote("Impossible is nothing");
        item.setAuthor("Adidas");
        result.add(item);

        item = new Quotation();
        item.setQuote("Solutions for a small planet");
        item.setAuthor("IBM");
        result.add(item);

        item = new Quotation();
        item.setQuote("I'm lovin it");
        item.setAuthor("McDonalds");
        result.add(item);

        item = new Quotation();
        item.setQuote("Just do it");
        item.setAuthor("Nike");
        result.add(item);

        item = new Quotation();
        item.setQuote("Melts in your mouth, not in your hands");
        item.setAuthor("M&M");
        result.add(item);

        item = new Quotation();
        item.setQuote("Because you're worth it");
        item.setAuthor("Loreal");
        result.add(item);

        return result;
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
                        QuotationSqlHelper.getInstance(context).clearAllQuotations();
                        hashMapList.clear();
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
}
