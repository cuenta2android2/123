package com.example.jorge.app1.Pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.*;

@Entity(tableName = "QuotationsTable",indices = {@Index(value = {"quote"}, unique = true)})
public class Quotation {
    @PrimaryKey(autoGenerate=true)
    public int id;

    @ColumnInfo(name="quote")
    public String quoteText;


    @ColumnInfo(name="author")
    @NonNull
    public String quoteAuthor;
    public  String senderName;
    public String senderLink;
    public String quoteLink;

    public Quotation(){}
    public Quotation(String quoteText,String quoteAuthor){
        this.quoteAuthor=quoteAuthor;
        this.quoteText=quoteText;
    }

    public String getQuote() {
        return quoteText;
    }

    public void setQuote(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getAuthor() {
        return quoteAuthor;
    }

    public void setAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderLink() {
        return senderLink;
    }

    public void setSenderLink(String senderLink) {
        this.senderLink = senderLink;
    }

    public String getQuoteLink() {
        return quoteLink;
    }

    public void setQuoteLink(String quoteLink) {
        this.quoteLink = quoteLink;
    }
}
