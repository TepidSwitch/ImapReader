package kurrent.imapreader;

/**
 * Created by Stanislav on 6/19/2014.
 */
public class Email {

    int _id;
    String _email;
    String _body;

    public Email(){}

    public Email(int id, String email, String body) {
        this._id = id;
        this._email = email;
        this._body = body;
    }

    public Email(String email, String body) {
        this._email = email;
        this._body = body;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getEmail(){
        return this._email;
    }

    // setting name
    public void setEmail(String email){
        this._email = email;
    }

    // getting phone number
    public String getBody(){
        return this._body;
    }

    // setting phone number
    public void setBody(String body){
        this._body = body;
    }

    @Override
    public String toString() {
        return _body;
    }

}
