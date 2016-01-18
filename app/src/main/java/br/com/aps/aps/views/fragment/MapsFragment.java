package br.com.aps.aps.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.aps.aps.R;
import br.com.aps.aps.helpers.LocationHelper;
import br.com.aps.aps.helpers.MapsHelper;
import br.com.aps.aps.models.Locations;
import br.com.aps.aps.storages.internal.async.LocationsTask;
import br.com.aps.aps.storages.internal.listener.LocationsListener;
import br.com.aps.aps.utils.AppUtil;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mSupportMapFragment;
    private View mRootView;
    private GoogleMap mMap;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    public MapsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_map);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSupportMapFragment = SupportMapFragment.newInstance();
        mRootView = inflater.inflate(R.layout.fragment_maps, null);

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().add(R.id.map, mSupportMapFragment).commitAllowingStateLoss();
        }

        mSupportMapFragment.getMapAsync(this);

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        new LocationsTask(new LocationsListener() {
            @Override
            public void onPostExecuteLocations(Locations result) {
                MapsHelper.locationsMap(result.getLocations());
            }
        }).execute();
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
