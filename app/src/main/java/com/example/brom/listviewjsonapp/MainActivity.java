package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;



// Create a new class, Mountain, that can hold your JSON data *

// Create a ListView as in "Assignment 1 - Toast and ListView" *

// Retrieve data from Internet service using AsyncTask and the included networking code *

// Parse the retrieved JSON and update the ListView adapter

// Implement a "refresh" functionality using Android's menu system


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private String[] mountainNames = {"Matterhorn","Mont Blanc","Denali"};
    private String[] mountainLocations = {"Alps","Alps","Alaska"};
    private int[] mountainHeights ={4478,4808,6190};
    // Create ArrayLists from the raw data above and use these lists when populating your ListView.
    private ArrayList<String> listData=new ArrayList<>(Arrays.asList(mountainNames));

    private ArrayAdapter<String> adapter;
    ListView my_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_textview, R.id.list_item_textviewtest);
        my_listview = (ListView) findViewById(R.id.my_listview);
        my_listview.setAdapter(adapter);

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String korv = new String("");
                Intent intent = new Intent(MainActivity.this, MountainDetailActivity.class);
                intent.putExtra("HEIGHT", mountainHeights[i]);
                intent.putExtra("LOCATION", mountainLocations[i]);
                intent.putExtra("NAME", mountainNames[i]);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), korv+"Name:"+mountainNames[i]+ "|Location:"+mountainLocations[i]+"|Mountain height:"+mountainHeights[i], Toast.LENGTH_SHORT).show();
            }

        });

        new FetchData().execute();


    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }


        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.

            //setContentView(R.layout.parse);
            Log.d("ToweDebugg", "Debugging starting");
            Log.d("ToweDebugg", o);

            try {
                JSONArray json1 = new JSONArray(o);

                Log.d("ToweDebugg", json1.toString());
                for(int i = 0; i < json1.length(); i++) {
                    Log.d("ToweDebugg", json1.getJSONObject(i).getString("name"));
                    adapter.add(json1.getJSONObject(i).getString("name"));
                }
            } catch (JSONException e) {
                Log.e("ToweDebugg","E:"+e.getMessage());
            }
/*

            */
        }
    }
}

