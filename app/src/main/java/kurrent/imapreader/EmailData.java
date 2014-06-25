package kurrent.imapreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.List;

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

    public void addEmail(Email email) {
        handler.addEmail(email);
    }

    public Email getEmail(int id) {
        return handler.getEmail(id);
    }

    public List<Email> getAllEmails() {
        return handler.getAllEmails();
    }

    public int updateEmail (Email email) {
        return handler.updateEmail(email);
    }

    public void deleteEmail(Email email) {
        handler.deleteEmail(email);
    }

    public int getEmailCount() {
        return handler.getEmailCount();
    }

}
