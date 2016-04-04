package tk.arcane9.worldclockify;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeZonify {
    private int id;
    protected String tz_name;
    protected String tz_id;
    protected String tz_gmt;
    protected int flag;

    public TimeZonify(int id, String tzName, String tzId, String tzGmt) {
        this.id = id;
        this.tz_name = tzName;
        this.tz_id = tzId;
        this.tz_gmt = tzGmt;
    }

    public TimeZonify(TimeZone timeZone,String tz_gmt,int flag) {

        this(timeZone.getDisplayName(),timeZone.getID(),tz_gmt,flag);
    }

    public TimeZonify(String tz_name, String tz_id, String tz_gmt, int flag) {
        this.tz_name = tz_name;
        this.tz_id = tz_id;
        this.tz_gmt = tz_gmt;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public String getTz_name() {
        return tz_name;
    }

    public String getTz_id() {
        return tz_id;
    }

    public String getTz_gmt() {
        return tz_gmt;
    }

}
