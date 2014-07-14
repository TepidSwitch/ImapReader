package kurrent.imapreader;

/**
 * Created by Stanislav on 6/19/2014.
 */
public class Email {

    int _id;
    String _from;
    String _subject;
    String _sentDate;
    String _body;

    public static String newline = System.getProperty("line.separator");

    public Email() {
    }

    public Email(int id, String from, String subject, String sentDate, String body) {
        this._id = id;
        this._from = from;
        this._subject = subject;
        this._sentDate = sentDate;
        this._body = body;
    }

    public Email(String email, String body) {
        this._from = email;
        this._body = body;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getFrom() {
        return this._from;
    }

    // setting name
    public void setFrom(String from) {
        this._from = from;
    }

    // getting body
    public String getBody() {
        return this._body;
    }

    // setting body
    public void setBody(String body) {
        this._body = body;
    }

    // get subject
    public String getSubject() {
        return this._subject;
    }

    // set subject
    public void setSubject(String subject) {
        this._subject = subject;
    }

    // get sent date
    public String getSentDate() {
        return this._sentDate;
    }

    // set sent date
    public void setSentDate(String date) {
        this._sentDate = date;
    }

    @Override
    public String toString() {
        return "From: " + this._from + newline + "Subject: " + this._subject + newline +
                "Sent Date: " + this._sentDate + newline +
                "----------------------------------------" + newline + this._body;
    }
}
