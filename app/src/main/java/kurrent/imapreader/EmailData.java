package kurrent.imapreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import static kurrent.imapreader.DatabaseHandler.KEY_BODY;
import static kurrent.imapreader.DatabaseHandler.KEY_FROM;
import static kurrent.imapreader.DatabaseHandler.KEY_ID;
import static kurrent.imapreader.DatabaseHandler.KEY_SENTDATE;
import static kurrent.imapreader.DatabaseHandler.KEY_SUBJECT;
import static kurrent.imapreader.DatabaseHandler.TABLE_EMAILS;


/**
 * Created by Stanislav on 6/24/2014.
 */
public class EmailData {

    private SQLiteDatabase db;
    private DatabaseHandler handler;

    public EmailData(Context context) {
        handler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        db = handler.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //CRUD

    // Adding new email
    public void addEmail(Email email) {

        ContentValues values = new ContentValues();
        values.put(KEY_FROM, email.getFrom()); // Email From
        values.put(KEY_SUBJECT, email.getSubject()); // Email Subject
        values.put(KEY_SENTDATE, email.getSentDate()); // Email SentDate
        values.put(KEY_BODY, email.getBody()); // Email Body

        // Inserting Row
        db.insert(TABLE_EMAILS, null, values);
    }

    // Getting single email
    public Email getEmail(int id) {

        Cursor cursor = db.query(TABLE_EMAILS, new String[]{KEY_ID,
                        KEY_FROM, KEY_SUBJECT, KEY_SENTDATE, KEY_BODY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();

        // return email
        return new Email(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }

    // Getting All Emails
    public ArrayList<Email> getAllEmails() {
        ArrayList<Email> emailList = new ArrayList<Email>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMAILS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Email email = new Email();
                email.setID(Integer.parseInt(cursor.getString(0)));
                email.setFrom(cursor.getString(1));
                email.setSubject(cursor.getString(2));
                email.setSentDate(cursor.getString(3));
                email.setBody(cursor.getString(4));
                // Adding email to list
                emailList.add(email);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return emailList;
    }

    // Updating single email
    public int updateEmail(Email email) {

        ContentValues values = new ContentValues();
        values.put(KEY_FROM, email.getFrom());
        values.put(KEY_SUBJECT, email.getSubject());
        values.put(KEY_SENTDATE, email.getSentDate());
        values.put(KEY_BODY, email.getBody());

        // updating row
        return db.update(TABLE_EMAILS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(email.getID())});
    }

    // Deleting single email
    public void deleteEmail(Email email) {
        db.delete(TABLE_EMAILS, KEY_ID + " = ?",
                new String[]{String.valueOf(email.getID())});
    }

    // Getting emails Count
    public int getEmailCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMAILS;

        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

}
