package com.example.skcho.smartcarrier;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 101;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;

    private int myLatitude;
    private int myLogitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLatLoc();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLatLoc(){
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isGPSEnabled || isNetworkEnabled){
            Toast.makeText(getApplicationContext(), "GPS 위치 찾는 중", Toast.LENGTH_SHORT).show();

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.e("MAPS", "["+location.getLatitude()+", "+location.getLongitude()+"]");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.554690,126.970702)));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        googleMap.animateCamera(zoom);

        MarkerOptions marker = new MarkerOptions();

        // 위치, 경도 수정하기!!!
        marker.position(new LatLng(37.554690, 126.970702))
                .title("캐리어 위치")
                .snippet("Carrier Station");
        googleMap.addMarker(marker).showInfoWindow();

        if(mMap != null)
        {
            int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED)//수락시 마지막에 들어감
            {
                mMap.setMyLocationEnabled(true);
            }
            else
            {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_REQUEST_CODE);//수락, 거절 둘다 첫번째
            }
        }
    }

    protected  void requestPermission(String permissionType, int requestCode)//두번째
    {
        ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults)
    {
        switch(requestCode)
        {
            case LOCATION_REQUEST_CODE:
            {
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)//Permission 거부시
                {
                    Toast.makeText(this,"Unable to show location = permission required",Toast.LENGTH_LONG).show();
                }
                else//수락시
                {
                    SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }
}
