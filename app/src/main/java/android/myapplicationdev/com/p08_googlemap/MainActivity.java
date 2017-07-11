package android.myapplicationdev.com.p08_googlemap;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn = (Spinner)findViewById(R.id.spinner);
        TextView tv = (TextView)findViewById(R.id.textView123);
        tv.setText("ABC Pte Ltd\n\nWe now have 3 branches. Look below for the address and info" );

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                final LatLng poi_north = new LatLng(1.4410734, 103.7720697);
                final LatLng poi_central = new LatLng(1.298081, 103.847409);
                final LatLng poi_east = new LatLng(1.367449, 103.928053);

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (map != null){
                            if (spn.getSelectedItem().toString().equals("North")) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
                            } else if (spn.getSelectedItem().toString().equals("Central")) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                            } else if (spn.getSelectedItem().toString().equals("East")) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                            } else {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 11));
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);


                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                }


                //Place some markers
                final Marker north = map.addMarker(new MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm Tel:65433456")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.headquarter)));

                Marker central = map.addMarker(new MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 Operating hours: 11am-8pm Tel:67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker east = map.addMarker(new MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 Operating hours: 9am-5pm Tel:66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker != null){
                            marker.showInfoWindow();
                            Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
            }
        });




    }
}
