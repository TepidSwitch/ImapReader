package kurrent.imapreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Stanislav on 6/19/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables

    // Own instance
    private static DatabaseHandler sInstance;

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "emailManager";

    // Emails table name
    public static final String TABLE_EMAILS = "emails";

    // Emails Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_FROM = "sentFrom";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_SENTDATE = "sentDate";
    public static final String KEY_BODY = "body";

    public static DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMAILS_TABLE = "CREATE TABLE " + TABLE_EMAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FROM + " TEXT,"
                + KEY_SUBJECT + " TEXT," + KEY_SENTDATE + " TEXT,"
                + KEY_BODY + " TEXT" + ")";
        db.execSQL(CREATE_EMAILS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Alert the Log
        Log.w(DatabaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS);

        // Create tables again
        onCreate(db);
    }

}