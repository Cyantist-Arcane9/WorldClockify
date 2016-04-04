package tk.arcane9.worldclockify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimeZoneListAdapter extends BaseAdapter implements Filterable {

    private List<TimeZonify> list;
    private List<TimeZonify> orig;
    private LayoutInflater layoutInflater;

    public TimeZoneListAdapter(List<TimeZonify> tzlist, Context applicationContext) {
        this.layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = tzlist;
    }



    static class TimeZoneObj {
        TextView time_id;
        TextView time_name;
        TextView time_gmt;
        ImageView time_flag;
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
            convertView = layoutInflater.inflate(R.layout.time_zone_layout, parent, false);

            viewHolder = new TimeZoneObj();
            viewHolder.time_id = (TextView) convertView.findViewById(R.id.tz_id);
            viewHolder.time_name = (TextView) convertView.findViewById(R.id.tz_name);
            viewHolder.time_gmt = (TextView) convertView.findViewById(R.id.tz_gmt);
            viewHolder.time_flag = (ImageView) convertView.findViewById(R.id.timeZone_flag);
            viewHolder.time_id.setText(list.get(position).tz_id);
            viewHolder.time_name.setText(list.get(position).tz_name);
            viewHolder.time_gmt.setText(list.get(position).tz_gmt);
            viewHolder.time_flag.setImageResource(list.get(position).flag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TimeZoneObj) convertView.getTag();
            viewHolder.time_id.setText(list.get(position).tz_id);
            viewHolder.time_name.setText(list.get(position).tz_name);
            viewHolder.time_gmt.setText(list.get(position).tz_gmt);
            viewHolder.time_flag.setImageResource(list.get(position).flag);

        }

        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<TimeZonify> results = new ArrayList<TimeZonify>();
                if (orig == null)
                    orig = list;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final TimeZonify g : orig) {
                            if (g.tz_id.toLowerCase()
                                    .contains(constraint.toString())){
                                results.add(g);
                            }
                            else if (g.tz_name.toLowerCase()
                                    .contains(constraint.toString())){
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<TimeZonify>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}