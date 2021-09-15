package com.example.covid_tracker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Model {

    public String[] getCity(JSONObject response) throws JSONException {
        JSONArray array = response.names();
        Log.e("Information","Array"+array+"\n");
        final int n = response.length();
        String[] m = new String[n];
        for (int i = 0; i < n; ++i) {
            m[i] = array.getString(i);
            Log.e("Information","City "+m[i]+"\n");
        }
        return m;
    }

    public String getActive(JSONObject response) throws JSONException {
        String active = response.getString("active");
        int o = Integer.parseInt(active);
        if(o<0)
            return  ""+(o-2*o);
        return active;
    }

    public String getDead(JSONObject response) throws JSONException {
        String dead = response.getString("deceased");
        int o = Integer.parseInt(dead);
        if(o<0)
            return  ""+(o-2*o);
        return dead;
    }

    public String getRecovered(JSONObject response) throws JSONException {
        String recovered = response.getString("recovered");
        int o = Integer.parseInt(recovered);
        if(o<0)
            return  ""+(o-2*o);
        return recovered;
    }

    public String getDrecovered(JSONObject response) throws JSONException {
        String drecovered = response.getJSONObject("delta").getString("recovered");
        int o = Integer.parseInt(drecovered);
        if(o<0)
            return  ""+(o-2*o);
        return drecovered;
    }

    public String getDdead(JSONObject response) throws JSONException {
        String ddead = response.getJSONObject("delta").getString("deceased");
        int o = Integer.parseInt(ddead);
        if(o<0)
            return  ""+(o-2*o);
        return ddead;
    }

    public String getDconfirmed(JSONObject response) throws JSONException {
        String dconfirmed = response.getJSONObject("delta").getString("confirmed");
        int o = Integer.parseInt(dconfirmed);
        if(o<0)
            return  ""+(o-2*o);
        return dconfirmed;
    }

    public String getConfirmed(JSONObject response) throws JSONException {
        String confirmed = response.getString("confirmed");
        int o = Integer.parseInt(confirmed);
        if(o<0)
            return  ""+(o-2*o);
        return confirmed;
    }

}
