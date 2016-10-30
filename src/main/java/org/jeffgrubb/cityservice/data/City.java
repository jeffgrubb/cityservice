package org.jeffgrubb.cityservice.data;

/**
 * Created by jgrubb on 10/18/16.
 */
public class City {
    private String cityName;
    private String state;
    private String zipCode;
    private String latitude;
    private String longitude;

    public void setName(String name) {
        cityName = name;
    }

    public String getName() {
        return cityName;
    }

    public void setState(String st) {
        state = st;
    }

    public String getState() {
        return state;
    }

    public void setZipCode(String zip) {
        zipCode = zip;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setLatitude(String lat) {
        latitude = lat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String lon) {
        longitude = lon;
    }

    public String getLongitude() {
        return longitude;
    }
}
