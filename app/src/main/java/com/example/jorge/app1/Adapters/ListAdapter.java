package com.example.jorge.app1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.jorge.app1.Pojo.Quotation;
import com.example.jorge.app1.R;

public class ListAdapter extends ArrayAdapter
{
    private Context context;
    private int layout;
    private List<Quotation> quotationList;

    public ListAdapter(@NonNull Context context, int layout, List<Quotation> quotationList) {
        super(context, layout, quotationList);
        this.context = context;
        this.layout = layout;
        this.quotationList = quotationList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.quotation_list_row, null);
        }
        TextView quoteTextView = (TextView) view.findViewById(R.id.tvQuote);
        TextView authorTextView = (TextView) view.findViewById(R.id.tvAuthor);

        quoteTextView.setText(this.quotationList.get(position).getQuote());
        authorTextView.setText(this.quotationList.get(position).getAuthor());

        return view;
    }
}
