package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class Cities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        ListView l=(ListView)findViewById(R.id.list1);
        Intent i1=getIntent();
        String r=i1.getStringExtra("Response");
        JSONObject resp = null;
        try {
            resp = new JSONObject(r);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Model s= new Model();
        final int n = resp.length();
        String[] confirmed=new String[n];
        String[] dconfirmed=new String[n];
        String[] recovered=new String[n];
        String[] drecovered=new String[n];
        String[] dead=new String[n];
        String[] ddead=new String[n];
        String[] active=new String[n];
        String[] cities= new String[n];
        try {
            cities = s.getCity(resp);
            Log.e("Information","Cities : "+cities);
            for(int i=0;i<n;i++)
            {
                JSONObject respon=resp.getJSONObject(cities[i]);
                Log.e("Information","Cities : "+cities[i]);
                confirmed[i]=s.getConfirmed(respon);
                dconfirmed[i]=s.getDconfirmed(respon);
                recovered[i]=s.getRecovered(respon);
                drecovered[i]=s.getDrecovered(respon);
                dead[i]=s.getDead(respon);
                ddead[i]=s.getDdead(respon);
                active[i]=s.getActive(respon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyListAdapter adapter = new MyListAdapter(Cities.this,cities,active,dead,recovered,confirmed,ddead,drecovered,dconfirmed);
        l.setAdapter(adapter);
    }
}