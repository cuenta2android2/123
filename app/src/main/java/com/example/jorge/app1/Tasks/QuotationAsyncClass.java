package com.example.jorge.app1.Tasks;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.jorge.app1.Activities.FavouriteActivity;
import com.example.jorge.app1.Activities.QuotationActivity;
import com.example.jorge.app1.Pojo.Quotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class QuotationAsyncClass extends AsyncTask<Void, Void, Quotation> {
    private WeakReference<QuotationActivity> reference;

    public QuotationAsyncClass(QuotationActivity f) {
        this.reference = new WeakReference<>(f);
    }
    String lan;
    String htmlpref;

    @Override
    protected Quotation doInBackground(Void... voids) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(reference.get().getApplicationContext());
        htmlpref = sharedPrefs.getString("prefs_http_method", "");
        Quotation q = new Quotation("Ha habido un error", "Vuelva a intentarlo");
        if(sharedPrefs.getString("language", "English")=="English" ) {lan="en";}
        else lan="ru";
        if (htmlpref == "POST") {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("api.forismatic.com");
            builder.appendPath("api");
            builder.appendPath("1.0");
            builder.appendPath("");
            String body = "mehod=getQuote&format=json&lang=" + lan;


            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                   // Gson quotation = new Gson();
                    //q=quotation.fromJson(reader,Quotation.class);
                   // q.setAuthor("Hay conexion"+reader.toString());
                    reader.close();

                }
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("api.forismatic.com");
            builder.appendPath("api");
            builder.appendPath("1.0");
            builder.appendPath("");
            builder.appendQueryParameter("method", "getQuotes");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("lang", lan);

            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                   // Gson quotation = new Gson();
                    //q = quotation.fromJson(reader, Quotation.class);
                    reader.close();
                }
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return q;
    }

    @Override
    protected void onPostExecute(Quotation qu) {

        //Este es un codigo para probar la aplicacion ya que no conseguimos obtener la cit aleatoria del serivor
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(reference.get().getApplicationContext());
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(reference.get().getApplicationContext()).edit();
        int cont= sharedPrefs.getInt("fakeNumber", 0);
        qu.setQuote("Cita de prueba: "+cont);
        editor.putInt("fakeNumber",cont+1);
        editor.apply();

        //Esta linea de codigo seria la uncica necesaria en caso de obtener correctamente la cita aleatoria
        reference.get().updateWebService(qu);
    }

    @Override
    protected void onPreExecute() {
        reference.get().hiddeActionbar();
    }
}
