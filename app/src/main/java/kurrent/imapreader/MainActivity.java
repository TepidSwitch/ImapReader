package kurrent.imapreader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends ListActivity {

    private EmailData datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new EmailData(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        datasource.addEmail(new Email(1, "Hello", "World"));
        datasource.addEmail(new Email(2, "Baker", "Go Home"));
        datasource.addEmail(new Email(3, "Work", "Why God Why"));

        List<Email> emails = datasource.getAllEmails();

        ArrayAdapter<Email> adapter = new ArrayAdapter<Email>(this, android.R.layout.simple_list_item_1, emails);
        setListAdapter(adapter);
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
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
