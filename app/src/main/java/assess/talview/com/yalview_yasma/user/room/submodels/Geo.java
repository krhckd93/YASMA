package assess.talview.com.yalview_yasma.user.room.submodels;

import android.arch.persistence.room.Ignore;

public class Geo {
    String lat;
    String lng;

    public Geo() {}

    @Ignore
    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() { return lat; }

    public void setLat(String lat) { this.lat = lat; }

    public String getLng() { return lng; }

    public void setLng(String lng) { this.lng = lng; }

    @Override
    public String toString() {
        return "Geo{" + "lat='" + lat + '\'' + ", lng='" + lng + '\'' + '}';
    }
}
