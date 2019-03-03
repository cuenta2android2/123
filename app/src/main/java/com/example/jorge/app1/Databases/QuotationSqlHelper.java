package com.example.jorge.app1.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jorge.app1.Pojo.Quotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuotationSqlHelper  extends SQLiteOpenHelper {

    private static QuotationSqlHelper instance;

    public synchronized static QuotationSqlHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuotationSqlHelper(context.getApplicationContext());
        }
        return instance;
    }

    private QuotationSqlHelper(Context context) {
        super(context, "quotation_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QuotationsTable " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, quote TEXT NOT NULL, author TEXT, UNIQUE (quote));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Quotation> getQuotations() {

        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> item;
        List<Quotation> list=new ArrayList<Quotation>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(
                "QuotationsTable", new String[]{"quote", "author"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            item = new HashMap<>();
            item.put("quote", cursor.getString(0));
            item.put("author", cursor.getString(1));
            result.add(item);
            list.add(new Quotation(cursor.getString(0), cursor.getString(1)));
        }
        cursor.close();
        database.close();

        return list;
    }

    public void addQuotation(String text, String author) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quote", text);
        values.put("author", author);
        database.insert("QuotationsTable", null, values);
        database.close();
    }

    public void clearAllQuotations() {

        SQLiteDatabase database = getWritableDatabase();
        database.delete("QuotationsTable", null, null);
        database.close();
    }

    public void deleteQuotation(String text) {

        SQLiteDatabase database = getWritableDatabase();
        database.delete("QuotationsTable", "quote=?", new String[]{text});
        database.close();
    }
}

