package com.example.bloodbuddy;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class UserLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    Button save_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);
        save_location = (Button)  findViewById(R.id.save_location);
        Intent intent = getIntent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        { Toast.makeText(getApplicationContext(),"Network Provider enabled", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                 final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                final Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List <Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    final String address = addressList.get(0).getAddressLine(0);
                    // + addressList.get(0).getLocality() +  addressList.get(0).getCountryName();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));


                    save_location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            double saved_lat = latitude;
                            double saved_long = longitude;
                            String saved_address = address;

                            Intent saved_loc = new Intent();
                            saved_loc.putExtra("final lat",saved_lat);
                            saved_loc.putExtra("final long",saved_long);
                            saved_loc.putExtra("final address",saved_address);

                            setResult(RESULT_OK, saved_loc);
                            finish();
                            Toast.makeText(getApplicationContext(),"Location saved",Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {


            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
        }
        else  if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getApplicationContext(),"GPS Provider enabled", Toast.LENGTH_SHORT).show();
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
             @Override
             public void onLocationChanged(Location location) {

                 final double latitude = location.getLatitude();
                  final double longitude = location.getLongitude();
                 LatLng latLng = new LatLng(latitude, longitude);
                 Geocoder geocoder = new Geocoder(getApplicationContext());
                 try {
                     List <Address> addressList = geocoder.getFromLocation(latitude, longitude, 2);
                     final String address = addressList.get(0).getAddressLine(0) ;/*+ addressList.get(0).getLocality() +  addressList.get(0).getCountryName()*/
                     mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                     save_location.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             double saved_lat = latitude;
                             double saved_long = longitude;
                             String saved_address = address;


                             Intent saved_loc = new Intent();
                             saved_loc.putExtra("final lat",saved_lat);
                             saved_loc.putExtra("final long",saved_long);
                             saved_loc.putExtra("final address",saved_address);

                             setResult(RESULT_OK, saved_loc);
                             finish();
                         }
                     });


                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }

             @Override
             public void onStatusChanged(String s, int i, Bundle bundle) {

             }

             @Override
             public void onProviderEnabled(String s) {

             }

             @Override
             public void onProviderDisabled(String s) {

             }
         });
        }



    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.2f));*/

    }


}
