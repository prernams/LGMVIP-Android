package com.example.covid_tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        pd=new ProgressDialog(MainActivity.this);
        pd.setTitle("Loading Data...");
        String url = "https://data.covid19india.org/state_district_wise.json";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.e("Information:", String.valueOf(response));
                        String[] cities = new String[0];
                        try {
                            cities = getState(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final List<String> cities_list = new ArrayList<>(Arrays.asList(cities));
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, cities_list);
                        final ListView list=(ListView) findViewById(R.id.list);
                        pd.dismiss();
                        list.setAdapter(arrayAdapter);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String State = adapterView.getItemAtPosition(i).toString();
                                Log.e("Information","State:"+State);
                                try {
                                    JSONObject resp=response.getJSONObject(State).getJSONObject("districtData");
                                    Intent in=new Intent(MainActivity.this,Cities.class);
                                    in.putExtra("Response",resp.toString());
                                    startActivity(in);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                    }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
        queue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String[] getState(JSONObject obj) throws JSONException {
        final int n = obj.length();
        String[] m = new String[n-1];
        JSONArray array = obj.names();
        for (int i = 1; i < n; ++i) {
            m[i - 1] = array.getString(i);
            Log.e("Information","City"+m[i-1]+"\n");
        }
        return m;
    }
}