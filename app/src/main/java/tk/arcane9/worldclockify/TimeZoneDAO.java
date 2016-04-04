package tk.arcane9.worldclockify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anshu on 3/24/2016.
 */
public class TimeZoneDAO extends SQLiteOpenHelper {
    public TimeZoneDAO(Context context) {
        super(context, "WorldClock", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql ="CREATE TABLE timezone(id INTEGER PRIMARY KEY, timeZoneId TEXT NOT NULL, timeZoneName TEXT, timeZoneGmt TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(TimeZonify tempTimeZonify) {
        ContentValues data = new ContentValues();
        data.put("timeZoneId",tempTimeZonify.getTz_id());
        data.put("timeZoneName",tempTimeZonify.getTz_name());
        data.put("timeZoneGmt",tempTimeZonify.getTz_gmt());
        SQLiteDatabase database = getWritableDatabase();
        database.insert("timezone",null,data);
    }


    public List<TimeZonify> listAll() {
        List<TimeZonify> timeZones = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM timezone;", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String tzName = cursor.getString(cursor.getColumnIndex("timeZoneName"));
            String tzId = cursor.getString(cursor.getColumnIndex("timeZoneId"));
            String tzGmt = cursor.getString(cursor.getColumnIndex("timeZoneGmt"));
            TimeZonify timeZonify = new TimeZonify(id,tzName,tzId,tzGmt);
            timeZones.add(timeZonify);
        }
        return  timeZones;
    }

    public void remove(TimeZonify timeZonify) {
        SQLiteDatabase database = getWritableDatabase();
        String[] params = {timeZonify.getId() + "" };
        database.delete("timezone","id= ?",params);
    }
}
