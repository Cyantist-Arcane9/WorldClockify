package tk.arcane9.worldclockify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;
import java.util.TimeZone;

public class WorldClockAdapter extends BaseAdapter {
    private List<TimeZonify> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public WorldClockAdapter(List<TimeZonify> tzlist, Context applicationContext) {
        this.context = applicationContext;
        this.layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = tzlist;
    }

    static class TimeZoneObj {
        TextView world_id;
        TextView world_name;
        TextView world_gmt;
        ImageView world_flag;
        TextClock world_clock;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeZoneObj viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.world_clock_list, parent, false);

            viewHolder = new TimeZoneObj();
            viewHolder.world_id = (TextView) convertView.findViewById(R.id.world_id);
            viewHolder.world_name = (TextView) convertView.findViewById(R.id.world_name);
            viewHolder.world_gmt = (TextView) convertView.findViewById(R.id.world_gmt);
            viewHolder.world_flag = (ImageView) convertView.findViewById(R.id.world_flag);
            viewHolder.world_clock = (TextClock) convertView.findViewById(R.id.world_clock);
            viewHolder.world_id.setText(list.get(position).tz_id);
            viewHolder.world_name.setText(list.get(position).tz_name);
            viewHolder.world_gmt.setText(list.get(position).tz_gmt);
            viewHolder.world_flag.setImageResource(fLaginator(TimeZone.getTimeZone(list.get(position).tz_id)));
            viewHolder.world_clock.setTimeZone(getTime(TimeZone.getTimeZone(list.get(position).tz_id)));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TimeZoneObj) convertView.getTag();
            viewHolder.world_id.setText(list.get(position).tz_id);
            viewHolder.world_name.setText(list.get(position).tz_name);
            viewHolder.world_gmt.setText(list.get(position).tz_gmt);
            viewHolder.world_flag.setImageResource(fLaginator(TimeZone.getTimeZone(list.get(position).tz_id)));
            viewHolder.world_clock.setTimeZone(getTime(TimeZone.getTimeZone(list.get(position).tz_id)));

        }

        return convertView;
    }
    private String getTime (TimeZone timeZone){
        return  timeZone.getID();
    }
    private int fLaginator(TimeZone timeZone) {
        int fl_id;
        fl_id = context.getResources().getIdentifier(flag_id(timeZone), "drawable",context.getPackageName());
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
}