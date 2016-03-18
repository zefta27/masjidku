package com.coba.zefta.Masjidku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends ActionBarActivity {
    private static final String LOG_TAG = "ExampleApp";

    private static final String SERVICE_URL = "http://heroza.ilkom.unsri.ac.id/places/api/nearby?lat=5&lng=5&tags=masjid";

    protected GoogleMap map;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Peta Masjid");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setUpMapIfNeeded();
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

        if(id == android.R.id.home){
            finish();
        }
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

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (map != null) {
                SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                map = mapFrag.getMap();
                map.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .title(jsonObj.getString("nama"))
                            .snippet(jsonObj.getString("alamat"))
                            .position(new LatLng(
                                    jsonObj.getDouble("latitude"),
                                    jsonObj.getDouble("longitude")
                            ))
            );

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    jsonObj.getDouble("latitude"),
                    jsonObj.getDouble("longitude")
            ), 8));
        }
    }


}

/*public class MapsActivity extends FragmentActivity {
    private static final String TAG = "MapsActivity";
    private List<Masjid> feedItemList = new ArrayList<Masjid>();
    private RecyclerView mRecyclerView;
    private MasjidAdapter adapter;
    public LinearLayout ll;
    Toolbar toolbar;

    private GoogleMap map;

    //Create position of marker
    private LatLng latLngBiru = new LatLng(-6.967168, 110.412992);
    private LatLng latLngKuning = new LatLng(-7.726843, 109.004748);
    private LatLng latLngMerah = new LatLng(-7.383182, 109.357403);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //make screen stays active
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // get mark potiton
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFrag.getMap();
        map.setMyLocationEnabled(true);
        final String url = "http://heroza.ilkom.unsri.ac.id/places/api/nearby?lat=5&lng=5&tags=masjid";
        new AsyncHttpTask().execute(url);
        //markers();
    }

    private void markers() {
        //add marker to maps
        map.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Title Popup Window").snippet("Description Popup"));
        map.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .title("Title Popup Window").snippet("Description Popup"));
        map.addMarker(new MarkerOptions()
                .position(latLngMerah)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("Title Popup Window").snippet("Description Popup"));
        //Marker latLngMera zoomed

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngMerah, 3));
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

            if (result != 1) {
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
                //item.setLatitude(post.optString("latitude"));
                //item.setLongitude(post.optString("longitude"));


                Double lat= Double.parseDouble(item.dapatLatitude(post.optString("latitude")));
                Double lng= Double.parseDouble(item.dapatLongitude(post.optString("longitude")));


                LatLng hasil = new LatLng(lat, lng);
                //LatLng hasil = new LatLng(item.setLatitude(post.optString("latitude")),item.setLongitude(post.optString("longitude")));

                //latLng = new google.maps.LatLng(data.lat, data.lng);

                map.addMarker(new MarkerOptions()
                        .position(hasil)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("Title Popup Window").snippet("Description Popup"));
                //Marker latLngMera zoomed

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(hasil, 3));
               JSONObject post = response.optJSONObject(i);

                Masjid item = new Masjid();
                item.setNama(post.optString("nama"));

*/
//}
  ///      } catch (JSONException e) {
     //       e.printStackTrace();
       // }
   // }

//}