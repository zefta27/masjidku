package com.coba.zefta.Masjidku;

/**
 * Created by zefta on 27/02/16.
 */
public class Masjid {
    private String nama;
    private String latitude;
    private String longitude;
    private String alamat;
    private String gambar_url;
    private String tags;
    private String distance;

    public Masjid() {
        this.nama = nama;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gambar_url = gambar_url;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getGambar_Url() {
        return gambar_url;
    }
    public void setGambar_Url(String gambar_url) {
        this.gambar_url = gambar_url;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String dapatLatitude(String latitude)
    {
        return latitude;
    }
    public String dapatLongitude(String longitude)
    {
        return longitude;
    }

}
