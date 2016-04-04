package tk.arcane9.worldclockify;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimezoneListing extends AppCompatActivity implements SearchView.OnQueryTextListener , SearchView.OnCloseListener {

    private ListView TimeList;
    private List<TimeZonify> tzlist;
    private TimeZoneListAdapter myTimeZoneAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timezone_listing);
        final TimeZoneDAO dao = new TimeZoneDAO(this);
        TimeList = (ListView) findViewById(R.id.TimeZones_List);
        tzlist = new ArrayList<>();
        String[] ids = TimeZone.getAvailableIDs();
        for (String id : ids){
            tzlist.add(new TimeZonify(TimeZone.getTimeZone(id)
                    ,gMTinator(TimeZone.getTimeZone(id))
                    ,fLaginator(TimeZone.getTimeZone(id))));
        }
        myTimeZoneAdapter = new TimeZoneListAdapter(tzlist, this.getApplicationContext());
        TimeList.setAdapter(myTimeZoneAdapter);
        TimeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimeZonify  tempTimeZonify = (TimeZonify) myTimeZoneAdapter.getItem(position);
                //Database access
                dao.insert(tempTimeZonify);
                Toast.makeText(TimezoneListing.this,tempTimeZonify.tz_id+"\n"+tempTimeZonify.tz_name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int fLaginator(TimeZone timeZone) {
        int fl_id;
        fl_id = getResources().getIdentifier(flag_id(timeZone), "drawable",getPackageName());
        return fl_id;
    }

    public String flag_id(TimeZone tz){
        String flag_name ,country_name;
        if(CountryTimeZone.getTzCountryMap().containsKey(tz.getID())){
            country_name = CountryTimeZone.getTzCountryMap().get(tz.getID()).name().toLowerCase();
            flag_name= "flag_"+country_name;
            return flag_name;
        }
        else{
            flag_name= "flag_xx";
            return flag_name;
        }

    }
    public String gMTinator (TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("(GMT+%d:%02d)", hours, minutes);
        } else {
            result = String.format("(GMT%d:%02d)", hours, minutes);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        return true;
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        myTimeZoneAdapter.getFilter().filter(query.toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myTimeZoneAdapter.getFilter().filter(newText.toString());
        return false;
    }
}
