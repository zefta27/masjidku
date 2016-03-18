package com.coba.zefta.Masjidku;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectPeta extends ActionBarActivity {
    private GoogleMap map;

    //Create position of marker

    Double latitude, longitude;
    String detail, alamat;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_peta);

        //make screen stays active
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Peta Masjid");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // get mark potiton
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFrag.getMap();
        map.setMyLocationEnabled(true);
        markers();
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

    private void markers() {
        //add marker to maps

        longitude = getIntent().getExtras().getDouble("longitude");

        latitude = getIntent().getExtras().getDouble("latitude");

        detail = getIntent().getExtras().getString("nama");
        alamat = getIntent().getExtras().getString("alamat");

        map.addMarker(new MarkerOptions()
                .position(new LatLng(-2.971392, 104.721901))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title(detail).snippet(alamat));


        //Marker latLngMera zoomed
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-2.971392, 104.721901), 3));
    }
}
/*public class SelectPeta extends AppCompatActivity {


    TextView  tvLatitude, tvLongitude;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_peta);
        longitude = getIntent().getExtras().getString("longitude");
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);

        latitude = getIntent().getExtras().getString("latitude");
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
    }
}*/
