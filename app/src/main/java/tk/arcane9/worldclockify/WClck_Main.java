package tk.arcane9.worldclockify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WClck_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wclck__main);
        registerForContextMenu(getWorld_listView());
        Button timeBt = (Button) findViewById(R.id.TimeListButton);
        TextView currenttz = (TextView) findViewById(R.id.CurrentTimezone);
        TextView currentdt = (TextView) findViewById(R.id.CurrentDate);
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        DateFormat zoneFormat = new SimpleDateFormat("zzzz");
        Date info = new Date();
        currenttz.setText(zoneFormat.format(info));
        currentdt.setText(dateFormat.format(info));
        timeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeIntent = new Intent(WClck_Main.this,TimezoneListing.class);
                startActivity(timeIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorldTimeZones();
    }

    private void loadWorldTimeZones() {
        //Database access
        TimeZoneDAO dao = new TimeZoneDAO(this);
        List<TimeZonify> timeZonifies = dao.listAll();
        dao.close();
        WorldClockAdapter myWorldClockAdapter = new WorldClockAdapter(timeZonifies,getApplicationContext());
        getWorld_listView().setAdapter(myWorldClockAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final TimeZonify timeZonify = (TimeZonify) getWorld_listView().getItemAtPosition(info.position);
        MenuItem remove = menu.add("Remove");
        remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TimeZoneDAO timeZoneDAO = new TimeZoneDAO(WClck_Main.this);
                timeZoneDAO.remove(timeZonify);
                timeZoneDAO.close();
                loadWorldTimeZones();
                return true;
            }
        });
    }

    public ListView getWorld_listView() {
        return (ListView) findViewById(R.id.world_listView);
    }
}
