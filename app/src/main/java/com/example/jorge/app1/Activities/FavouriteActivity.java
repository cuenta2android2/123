package com.example.jorge.app1.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.app1.R;
import com.example.jorge.app1.adapters.ListAdapter;
import com.example.jorge.app1.Pojo.Quotation;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
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
    }

    public void getAuthorInfo(String author) {

        if (author != null && !author.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(
                    Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search="
                            + author));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, R.string.anonymous, Toast.LENGTH_SHORT).show();
        }
    }

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
}
