package com.coba.zefta.Masjidku;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "RecyclerViewExample";
    private List<Masjid> feedItemList = new ArrayList<Masjid>();
    private RecyclerView mRecyclerView;
    private MasjidAdapter adapter;
    public LinearLayout ll;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //insialisasi Linear Layout Untuk loading
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.setVisibility(View.VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Masjidku");
        setSupportActionBar(toolbar);
        

        //inisialisai receycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // mendownload data dari url
        final String url = "http://heroza.ilkom.unsri.ac.id/places/api/nearby?lat=5&lng=5&tags=masjid";
        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {

                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();


                if (statusCode ==  200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1;
                }else{
                    result = 0;
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //sembunyikan loading
            ll.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MasjidAdapter(MainActivity.this, feedItemList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONArray response = new JSONArray(result);

            if (null == feedItemList) {
                feedItemList = new ArrayList<Masjid>();
            }

            for (int i = 0; i < response.length(); i++) {
                JSONObject post = response.optJSONObject(i);

                Masjid item = new Masjid();
                item.setNama(post.optString("nama"));
                item.setAlamat(post.optString("alamat"));
                item.setLatitude(post.optString("latitude"));
                item.setLongitude(post.optString("longitude"));
                item.setGambar_Url(post.optString("gambar_url"));
                item.setDistance(post.optString("distance"));
                item.setTags(post.optString("tags"));

                feedItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent in = new Intent("com.coba.zefta.belajarrecycle2.Main2Activity");
            startActivity(in);
            return true;
        }
        if (id == R.id.peta) {
            Intent in = new Intent("com.coba.zefta.belajarrecycle2.MapsActivity");
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}