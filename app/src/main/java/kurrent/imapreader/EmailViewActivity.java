package kurrent.imapreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.SQLException;

public class EmailViewActivity extends ActionBarActivity {

    public static final String TAG = EmailViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Open the damn database
        try {
            MainActivity.datasource.open();
        } catch (SQLException e) {
            Log.e(TAG, "Exception caught: ", e);
            e.printStackTrace();
        }

        // Get message from intent
        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EMAIL_INFO);

        // Create Text View
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(data);

        // Set the context view
        setContentView(textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.email_view, menu);
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
    protected void onPause() {
        MainActivity.datasource.close();
        super.onPause();
    }
}
