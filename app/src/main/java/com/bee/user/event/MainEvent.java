package com.bee.user.event;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/22  17：05
 * 描述：
 */
public class MainEvent {
    public static final int TYPE_login = 0;
    public static final int TYPE_set_index = 1;
    public static final int TYPE_reLocation = 2;
    public static final int TYPE_reset_Location = 3;

    public static final int TYPE_reset_Location_from_SelectLocationActivity = 4;

    public int index = 0;
    public int TYPE = 0;
    public String str;

    public LocationInfo locationInfo;
    public MainEvent(int TYPE) {
        this.TYPE = TYPE;
    }

    public static class LocationInfo{
        public String name;
        public String latitude;
        public String longitude;

        public LocationInfo(String name, String latitude, String longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
