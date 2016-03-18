package com.coba.zefta.Masjidku;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

/**
 * Created by zefta on 14/03/16.
 */
public class splash extends ActionBarActivity {
    public static final int lama = 8;
    public static final int mililama = lama*1000;
    public static final int delay = 2;
    private ProgressBar splashProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashProgress= (ProgressBar) findViewById(R.id.splashProgress);
        splashProgress.setMax(max_progress());
        splashAnimation();
    }

    public  void splashAnimation(){
        new CountDownTimer(mililama, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               splashProgress.setProgress(hitung_progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent pindah = new Intent(splash.this,MainActivity.class);
                startActivity(pindah);
                finish();
            }
        }.start();
    }

    public int hitung_progress(long miliseconds){
        return (int)((mililama-miliseconds)/1000);
    }

    public int max_progress(){
        return lama-delay;
    }

    @Override
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
        if (id == R.id.home) {
            finish();
        }
        return true;
    }
}
