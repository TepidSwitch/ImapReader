package kurrent.imapreader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EMAIL_INFO = "kurrent.imapreader.MESSAGE";

    public static EmailData datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new EmailData(this);

        try {
            datasource.open();
        } catch (SQLException e) {
            Log.e(TAG, "Exception caught: ", e);
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EmailReader.fetch();
            }
        });

        thread.start();

//        new DownloadEmailsTask().execute();

        ArrayList<Email> data = datasource.getAllEmails();
        ArrayList<String> emails = new ArrayList<String>();
        for (Email e : data) {
            emails.add("From: " + e.getFrom());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emails);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, EmailViewActivity.class);
        String data = datasource.getEmail(position + 1).toString();
        intent.putExtra(EMAIL_INFO, data);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        try {
            datasource.open();
        } catch (SQLException e) {
            Log.e(TAG, "Exception caught: ", e);
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
//        datasource.close();
        super.onPause();
    }

//    private class DownloadEmailsTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            EmailReader.fetch();
//            return null;
//        }
//    }
}
