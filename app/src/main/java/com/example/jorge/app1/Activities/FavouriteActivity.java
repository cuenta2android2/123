package com.example.jorge.app1.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jorge.app1.R;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
    }

    public void infoAuthor(View view) {
        Uri uri = Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=");
        String author = "Albert Einstein";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri + author));
        startActivity(intent);
    }
}
