package kurrent.imapreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 6/19/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "emailManager";

    // Emails table name
    private static final String TABLE_EMAILS = "emails";

    // Emails Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BODY = "body";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMAILS_TABLE = "CREATE TABLE " + TABLE_EMAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
                + KEY_BODY + " TEXT" + ")";
        db.execSQL(CREATE_EMAILS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS);

        // Create tables again
        onCreate(db);

    }

    // CRUD Operations

    // Adding new email
    public void addEmail(Email email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email.getEmail()); // Email Subject
        values.put(KEY_BODY, email.getBody()); // Email Body

        // Inserting Row
        db.insert(TABLE_EMAILS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single email
    public Email getEmail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMAILS, new String[] { KEY_ID,
                        KEY_EMAIL, KEY_BODY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Email email = new Email(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return email
        return email;
    }

    // Getting All Emails
    public List<Email> getAllEmails() {
        List<Email> emailList = new ArrayList<Email>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Email email = new Email();
                email.setID(Integer.parseInt(cursor.getString(0)));
                email.setEmail(cursor.getString(1));
                email.setBody(cursor.getString(2));
                // Adding email to list
                emailList.add(email);
            } while (cursor.moveToNext());
        }

        // return contact list
        return emailList;
    }

    // Updating single email
    public int updateEmail(Email email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email.getEmail());
        values.put(KEY_BODY, email.getBody());

        // updating row
        return db.update(TABLE_EMAILS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(email.getID()) });
    }

    // Deleting single email
    public void deleteEmail(Email email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMAILS, KEY_ID + " = ?",
                new String[] { String.valueOf(email.getID()) });
        db.close();
    }


    // Getting emails Count
    public int getEmailCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}