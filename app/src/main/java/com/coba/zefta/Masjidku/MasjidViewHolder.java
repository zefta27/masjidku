package com.coba.zefta.Masjidku;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zefta on 27/02/16.
 */
public class MasjidViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener{
        public TextView nama, alamat, latitude, longitude, gambar_url, distance, tags ;

        public MasjidViewHolder(View itemView){
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            alamat = (TextView) itemView.findViewById(R.id.alamat);
            latitude = (TextView) itemView.findViewById(R.id.latitude);
            longitude = (TextView) itemView.findViewById(R.id.longitude);
            gambar_url = (TextView) itemView.findViewById(R.id.gambar_url);
            distance = (TextView) itemView.findViewById(R.id.distance);
            tags = (TextView) itemView.findViewById(R.id.tags);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){

            Intent view = new Intent("com.coba.zefta.belajarrecycle2.detail");
            view.putExtra("nama",nama.getText().toString());
            view.putExtra("alamat",alamat.getText().toString());
            view.putExtra("longitude",longitude.getText().toString());
            view.putExtra("latitude",latitude.getText().toString());
            view.putExtra("gambar_url",gambar_url.getText().toString());
            view.putExtra("distance",distance.getText().toString());
            view.putExtra("tags",tags.getText().toString());

            Context ctx = itemView.getContext();
            ctx.startActivity(view);
        }



}
