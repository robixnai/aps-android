package br.com.aps.aps.helpers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.LocationListener;

public abstract class LocationHelper implements LocationListener {

    private static LocationManager mLocationManager;
    private static String mProvider;

    @Override
    public void onLocationChanged(Location location) {}

    public static String timeUpdate() {
        return "30";
    }

    public static double getLatitude(final Context context) {
        final Location location = getLocation(context);
        return location.getLatitude();
    }

    public static double getLongitude(final Context context) {
        final Location location = getLocation(context);
        return location.getLongitude();
    }

    private static Location getLocation(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        final Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);

        return mLocationManager.getLastKnownLocation(mProvider);
    }


}
