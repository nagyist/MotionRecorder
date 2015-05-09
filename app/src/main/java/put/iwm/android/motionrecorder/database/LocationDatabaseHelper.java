package put.iwm.android.motionrecorder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Szymon on 2015-04-23.
 */
public class LocationDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_COMMAND =
            "CREATE TABLE route_points (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "latitude REAL NOT NULL," +
            "longitude REAL NOT NULL," +
            "altitude REAL," +
            "move_distance REAL," +
            "move_time INTEGER," +
            "serial_number INTEGER," +
            "previous_point_id INTEGER REFERENCES route_points(id));";

    public static final String DATABASE_NAME = "MotionRecorder";
    public static final String TABLE_NAME = "route_points";

    public LocationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS route_points");
        onCreate(db);
    }
}
