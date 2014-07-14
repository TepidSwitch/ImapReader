package kurrent.imapreader;

import android.util.Log;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;


public class EmailReader {

    public static final String TAG = EmailReader.class.getSimpleName();

    public static void fetch() {
        // TODO: solve this final dilemma
        final Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        final String host = "imap.gmail.com";
        final String username = "stas.moogle@gmail.com";
        final String password = "keedmtufkiavbjrd";

        Email email = new Email();
        email.setID(1);

        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            for (Address address : in) {
                // TODO: put From info into database
                // System.out.println("FROM:" + address.toString());
                email.setFrom(address.toString());
            }
            Object content = msg.getContent();
            if (content instanceof String) {
                String body = (String) content;
                email.setSentDate(msg.getSentDate().toString());
                email.setSubject(msg.getSubject());
                email.setBody(body);
            } else if (content instanceof Multipart) {
                Multipart mp = (Multipart) content;
                BodyPart bp = mp.getBodyPart(0);
                email.setSentDate(msg.getSentDate().toString());
                email.setSubject(msg.getSubject());
                email.setBody(bp.getContent().toString());
            }
            // TODO: put SentDate, Subject, and Content info into database
            // System.out.println("SENT DATE:" + msg.getSentDate());
            // System.out.println("SUBJECT:" + msg.getSubject());
            // System.out.println("CONTENT:" + bp.getContent());
            MainActivity.datasource.addEmail(email);
            //} catch (NoSuchProviderException e) {
            //    Log.e(TAG, "No such provider caught: ", e);
            //    e.printStackTrace();
        } catch (MessagingException e) {
            Log.e(TAG, "Messaging error caught: ", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IO Exception caught: ", e);
            e.printStackTrace();
        }


    }

}