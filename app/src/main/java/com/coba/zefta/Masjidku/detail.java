package com.coba.zefta.Masjidku;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class detail extends AppCompatActivity {
    TextView tvDetail, tvAlamat, tvLatitude, tvLongitude , tvDistance, tvTags;
    String detail, alamat, latitude, longitude, gambar_url, distance, tags;
    ImageView tvGambar_Url;
    Toolbar toolbar;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detail = getIntent().getExtras().getString("nama");
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        alamat = getIntent().getExtras().getString("alamat");
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);

        longitude = getIntent().getExtras().getString("longitude");
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);

        latitude = getIntent().getExtras().getString("latitude");
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);

        distance = getIntent().getExtras().getString("distance");
        tvDistance = (TextView) findViewById(R.id.tvDistance);

        tags = getIntent().getExtras().getString("tags");
        tvTags = (TextView) findViewById(R.id.tvTags);

        gambar_url = getIntent().getExtras().getString("gambar_url");
        tvGambar_Url = (ImageView) findViewById(R.id.tvGambar_Url);

        setTitle(detail);
        tvDetail.setText("Masjid : "+detail);
        tvAlamat.setText("Alamat : " + alamat);
        tvLatitude.setText("Latitude : "+latitude);
        tvLongitude.setText("Longitude : "+longitude);
        tvDistance.setText("Distance : "+distance);
        tvTags.setText("Tags : "+tags);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(detail);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    public void btnPeta(View view){

        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);

        Intent select = new Intent("com.coba.zefta.belajarrecycle2.SelectPeta");
        select.putExtra("longitude",tvLongitude.getText().toString());
        select.putExtra("latitude",tvLatitude.getText().toString());
        select.putExtra("detail",tvDetail.getText().toString());
        select.putExtra("alamat",tvAlamat.getText().toString());
        startActivity(select);
    }

}
