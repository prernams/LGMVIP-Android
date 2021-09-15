package com.example.covid_tracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] city;
    private final String[] active;
    private final String[] confirmed;
    private final String[] dead;
    private final String[] recovered;
    private final String[] Dconfirmed;
    private final String[] Ddead;
    private final String[] Drecovered;

    public MyListAdapter(Context context, String[] city, String[] act, String[] ded, String[] rec, String[] con, String[] dded, String[] drec, String[] dcon) {
        super(context, R.layout.testing, city);
        this.context = (Activity) context;
        this.city = city;
        this.active = act;
        this.confirmed = con;
        this.dead = ded;
        this.recovered = rec;
        this.Dconfirmed = dcon;
        this.Ddead = dded;
        this.Drecovered = drec;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.testing, null,true);

        TextView City = (TextView) rowView.findViewById(R.id.city);
        TextView Active = (TextView) rowView.findViewById(R.id.active);
        TextView Recovered = (TextView) rowView.findViewById(R.id.recovered);
        TextView Dead = (TextView) rowView.findViewById(R.id.dead);
        TextView Confirmed = (TextView) rowView.findViewById(R.id.confirm);
        TextView Delta_Dead = (TextView) rowView.findViewById(R.id.ddead);
        TextView Delta_Confirmed = (TextView) rowView.findViewById(R.id.dconfirm);
        TextView Delta_Recovered = (TextView) rowView.findViewById(R.id.drecovered);

        City.setText(city[position]);
        Active.setText(active[position]);
        Recovered.setText(recovered[position]);
        Dead.setText(dead[position]);
        Confirmed.setText(confirmed[position]);
        Delta_Dead.setText(Ddead[position]);
        Delta_Confirmed.setText(Dconfirmed[position]);
        Delta_Recovered.setText(Drecovered[position]);

        return rowView;

    };
}
