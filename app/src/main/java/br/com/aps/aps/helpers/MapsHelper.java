package br.com.aps.aps.helpers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.aps.aps.R;
import br.com.aps.aps.models.Location;
import br.com.aps.aps.storages.sqlite.location.LocationsRepository;
import br.com.aps.aps.utils.AppUtil;

public abstract class MapsHelper {

    private static final float ZOOM = 12.5f;
    private static final String MY_LOCATION = AppUtil.CONTEXT.getString(R.string.my_location);

    private static List<Location> mLocations = new ArrayList<>();

    public static List<Location> getLocations() {
        return mLocations;
    }

    public static String getMyLocation() {
        return MY_LOCATION;
    }

    public static float getZoom() {
        return ZOOM;
    }

    public static void locationsMap(List<Location> result) {
        if (result.size() > 0) {
            for (Location location : result) {
                if (LocationsRepository.getInstance().getLocationId(location) == null) {
                    LocationsRepository.getInstance().save(location);
                } else {
                    result.remove(location);
                }
            }
        }

        mLocations = result;
    }

    public static void createMarkers(GoogleMap map, double latitude, double longitude) {
        final LatLng latLng = new LatLng(latitude, longitude);
        final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(icon);
        map.addMarker(markerOptions);
    }

    public static void markMaps(GoogleMap map) {
        if (mLocations.size() > 0) {
            for (Location location : mLocations) {
                createMarkers(map, location.getLatitude(), location.getLongitude());
            }
        } else {
            List<Location> locations = LocationsRepository.getInstance().getLocationDate();
            if (locations.size() > 0) {
                for (Location location : locations) {
                    createMarkers(map, location.getLatitude(), location.getLongitude());
                }
            }
        }
    }
}
