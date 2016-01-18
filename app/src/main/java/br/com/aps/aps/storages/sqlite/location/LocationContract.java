package br.com.aps.aps.storages.sqlite.location;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.aps.aps.models.Location;

public class LocationContract {

    public static final String TABLE = "location";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final String[] COLUNS = {ID, DATE, LATITUDE, LONGITUDE};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(DATE + " INTEGER, ");
        sql.append(LATITUDE + " REAL, ");
        sql.append(LONGITUDE + " REAL ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Location location) {
        ContentValues content = new ContentValues();
        content.put(ID, location.getId());
        content.put(DATE, location.getDate().getTime());
        content.put(LATITUDE, location.getLatitude());
        content.put(LONGITUDE, location.getLongitude());
        return content;
    }

    public static Location bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Location location = new Location();
            location.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            location.setDate(new Date(cursor.getInt(cursor.getColumnIndex(DATE))));
            location.setLatitude(cursor.getLong(cursor.getColumnIndex(LATITUDE)));
            location.setLongitude(cursor.getLong(cursor.getColumnIndex(LONGITUDE)));
            return location;
        }
        return null;
    }

    public static List<Location> bindList(Cursor cursor) {
        final List<Location> locations = new ArrayList<Location>();
        while (cursor.moveToNext()) {
            locations.add(bind(cursor));
        }
        return locations;
    }

}
