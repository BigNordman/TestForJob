package com.nordman.big.testforjob;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s_vershinin on 17.06.2016.
 *
 */
public class CountryBundle {
    private Context context;
    private CountryBundleHandler handler;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    List<String> listAllChildren = new ArrayList<String>();


    public CountryBundle(Context context, CountryBundleHandler handler) {
        this.context = context;
        this.handler = handler;
        new ParseTask().execute();
    }


    private class ParseTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL(context.getString(R.string.url_datasourse));

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }
        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            JSONObject dataJsonObj;
            List<String> listCities;
            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray jsonCountries = dataJsonObj.getJSONArray("Result");

                for (int i = 0; i < jsonCountries.length(); i++) {

                    JSONObject jsonCountry = jsonCountries.getJSONObject(i);
                    String countryName = jsonCountry.getString("Name");
                    listDataHeader.add(countryName);


                    JSONArray jsonCities = jsonCountry.getJSONArray("Cities");
                    listCities = new ArrayList<String>();

                    for (int j = 0; j < jsonCities.length(); j++) {
                        JSONObject jsonCity = jsonCities.getJSONObject(j);
                        String cityName = jsonCity.getString("Name");
                        listCities.add(cityName);
                        listAllChildren.add(cityName);
                    }
                    listDataChild.put(countryName, listCities);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.onDataLoaded();
        }
    }

}
