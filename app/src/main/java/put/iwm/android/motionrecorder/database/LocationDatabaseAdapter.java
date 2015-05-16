package put.iwm.android.motionrecorder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import put.iwm.android.motionrecorder.training.RoutePoint;

/**
 * Created by Szymon on 2015-04-23.
 */
public class LocationDatabaseAdapter {

    public static final String ROWID_COLUMN = "_id";
    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";
    public static final String ALTITUDE_COLUMN = "altitude";
    public static final String MOVE_DISTANCE_COLUMN = "move_distance";
    public static final String MOVE_TIME_COLUMN = "move_time";
    public static final String SERIAL_NUMBER_COLUMN = "serial_number";
    public static final String PREVIOUS_POINT_ID_COLUMN = "previous_point_id";

    private LocationDatabaseHelper locationDatabaseHelper;
    private SQLiteDatabase database;
    private Context context;

    public LocationDatabaseAdapter(Context context) {
        this.context = context;
    }

    public LocationDatabaseAdapter open() throws SQLException {
        locationDatabaseHelper = new LocationDatabaseHelper(context);
        database = locationDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        locationDatabaseHelper.close();
    }

    public long addLocation(Location location, long time) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(LATITUDE_COLUMN, location.getLatitude());
        contentValues.put(LONGITUDE_COLUMN, location.getLongitude());
        contentValues.put(ALTITUDE_COLUMN, location.getAltitude());
        contentValues.put(MOVE_DISTANCE_COLUMN, 0);
        contentValues.put(MOVE_TIME_COLUMN, time);
        contentValues.put(SERIAL_NUMBER_COLUMN, 0);

        return database.insert(LocationDatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public LatLng getLastLocation() {

        Cursor cursor = database.query(LocationDatabaseHelper.TABLE_NAME,
                new String[] { ROWID_COLUMN, LATITUDE_COLUMN, LONGITUDE_COLUMN },
                null, null, null, null, "_id DESC", "1");

        cursor.moveToFirst();

        double latitude = cursor.getDouble(1);
        double longitude = cursor.getDouble(2);

        cursor.close();

        return new LatLng(latitude, longitude);
    }

    public List<RoutePoint> getLastLocations() {

        return getLastLocations(0);
    }

    public List<RoutePoint> getLastLocations(int limit) {

        String limitClause = null;

        if(limit > 0)
            limitClause = String.valueOf(limit);

        Cursor cursor = database.query(LocationDatabaseHelper.TABLE_NAME,
                new String[] { ROWID_COLUMN, LATITUDE_COLUMN, LONGITUDE_COLUMN, MOVE_TIME_COLUMN },
                null, null, null, null, "_id DESC", limitClause);

        List<RoutePoint> result = new LinkedList<>();

        cursor.moveToFirst();

        do {
            double latitude = cursor.getDouble(1);
            double longitude = cursor.getDouble(2);
            long moveTime = cursor.getLong(3);

            RoutePoint routePoint = new RoutePoint();
            routePoint.setLatitude(latitude);
            routePoint.setLongitude(longitude);
            routePoint.setMoveTime(moveTime);

            result.add(routePoint);
        } while(cursor.moveToNext());

        cursor.close();

        return result;
    }

    public void deleteLastLocations() {

        database.delete(LocationDatabaseHelper.TABLE_NAME, null, null);
    }

}
