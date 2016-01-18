package br.com.aps.aps.storages.sqlite.location;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.aps.aps.helpers.LocationHelper;
import br.com.aps.aps.models.Location;
import br.com.aps.aps.storages.sqlite.DatabaseHelper;
import br.com.aps.aps.utils.AppUtil;

public class LocationsRepository {

    private static class Singleton {
        public static final LocationsRepository INSTANCE = new LocationsRepository();
    }

    private LocationsRepository() {
        super();
    }

    public static LocationsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Location location) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(LocationContract.TABLE, null, LocationContract.getContentValues(location));
        db.close();
        helper.close();
    }

    public List<Location> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(LocationContract.TABLE, LocationContract.COLUNS, null, null, null, null, LocationContract.DATE);
        List<Location> locations = LocationContract.bindList(cursor);
        db.close();
        helper.close();
        return locations;
    }

    public Location getLocationId(Location location) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = LocationContract.ID + " = ?";
        String[] selectionArgs = {location.getId().toString()};
        Cursor cursor = db.query(LocationContract.TABLE, LocationContract.COLUNS, selection, selectionArgs, null, null, null);
        Location hasLocation = LocationContract.bind(cursor);
        db.close();
        helper.close();
        return hasLocation;
    }

    public List<Location> getLocationDate() {
        final int minutes = -Integer.parseInt(LocationHelper.timeUpdate());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.set(Calendar.MINUTE, minutes);

        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = LocationContract.DATE + " between ? and ?";
        String[] selectionArgs = {calendar.getTime().toString(), new Date(System.currentTimeMillis()).toString()};
        Cursor cursor = db.query(LocationContract.TABLE, LocationContract.COLUNS, selection, selectionArgs, null, null, null);
        List<Location> hasLocation = LocationContract.bindList(cursor);
        db.close();
        helper.close();
        return hasLocation;
    }

}
