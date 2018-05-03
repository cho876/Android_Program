package com.example.skcho.smartcarrier.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.skcho.smartcarrier.BeaconCtl;
import com.example.skcho.smartcarrier.GpsDAO;
import com.example.skcho.smartcarrier.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    double lat_my;
    double lon_my;
    double lat_car;
    double lon_car;
    GpsDAO gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BeaconCtl.map_flag = false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lat_car = 37.554690;  // GPS 센서를 통해 받아와야함
        lon_car = 126.970702;  //  ''

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(lat_car, lon_car))
                .title("카트 위치")
                .snippet("Carrier Station");
        googleMap.addMarker(marker).showInfoWindow();

        mMap.setMyLocationEnabled(true);

        gps = new GpsDAO(MapsActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            lat_my = gps.getLatitude();
            lon_my = gps.getLongitude();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
        MarkerOptions marker2 = new MarkerOptions();
        marker2.position(new LatLng(lat_my, lon_my))
                .title("내 위치")
                .snippet("My location");
        googleMap.addMarker(marker2).showInfoWindow();

        double mid_lat = (lat_my + lat_car) / 2;
        double mid_lon = (lon_my + lon_car) / 2;


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mid_lat, mid_lon)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
        googleMap.animateCamera(zoom);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BeaconCtl.map_flag = true;
    }
}