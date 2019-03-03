package com.example.jorge.app1.Tasks;

import android.os.AsyncTask;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.preference.PreferenceManager;

import com.example.jorge.app1.Activities.FavouriteActivity;
import com.example.jorge.app1.Databases.DaoQuotation;
import com.example.jorge.app1.Databases.QuotationSqlHelper;
import com.example.jorge.app1.Databases.RoomDatabaseHelper;
import com.example.jorge.app1.Pojo.Quotation;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavouriteQuotationAsyncClass extends AsyncTask<Boolean, Void, List<Quotation>> {
    private WeakReference<FavouriteActivity> reference;

    public FavouriteQuotationAsyncClass(FavouriteActivity f) {
        this.reference = new WeakReference<>(f);
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        if (booleans[0]) return RoomDatabaseHelper.getInstance(reference.get().getApplicationContext()).dao().getAllQuotation();
        else return QuotationSqlHelper.getInstance(reference.get().getApplicationContext()).getQuotations();

    }

    @Override
    protected void onPostExecute(List<Quotation> list) {

        reference.get().showQuotations(list);
    }

    public FavouriteActivity getR() {
        return reference.get();
    }
}
