package com.example.jorge.app1.Pojo;

public class Quotation {
    String quoteText;
    String quoteAuthor;
    String senderName;
    String senderLink;
    String quoteLink;

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
