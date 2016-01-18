package br.com.aps.aps.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.aps.aps.R;
import br.com.aps.aps.helpers.LocationHelper;
import br.com.aps.aps.helpers.MapsHelper;
import br.com.aps.aps.utils.AppUtil;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        final double latitude = LocationHelper.getLatitude(AppUtil.CONTEXT);
        final double longitude = LocationHelper.getLongitude(AppUtil.CONTEXT);
        LatLng myLocation = new LatLng(latitude, longitude);

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(myLocation).title(MapsHelper.getMyLocation()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(MapsHelper.getZoom()));

        MapsHelper.markMaps(mMap);
    }
}
