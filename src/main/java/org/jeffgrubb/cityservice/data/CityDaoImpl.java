package org.jeffgrubb.cityservice.data;

/**
 * Created by jgrubb on 10/18/16.
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CityDaoImpl implements CityDao {

    private List<City> cities = new ArrayList<City>();

    @Override
    public void init() throws Exception {

        cities.clear();
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://postgresql.home.prod.jeffgrubb.org/cities";
        Connection connection = DriverManager.getConnection(url, "postgres", "");

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select name, zip, state, latitude, longitude from cities");

        int cnt = 0;

        while(rs.next()) {
            cnt++;
            City city = new City();
            int i = 1;
            city.setName(rs.getString(i++));
            city.setZipCode(rs.getString(i++));
            city.setState(rs.getString(i++));
            city.setLatitude(rs.getString(i++));
            city.setLongitude(rs.getString(i++));

            cities.add(city);
        }

        System.out.println("Cities Added: " + cnt);

        rs.close();
        connection.close();
    }

    @Override
    public void addCity(City city) throws Exception {

        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://postgresql.home.prod.jeffgrubb.org/cities";
        Connection connection = DriverManager.getConnection(url, "postgres", "");

        PreparedStatement ps = connection.prepareStatement("insert into cities \n" +
                "(name, zip, state, latitude, longitude)\n" +
                "values\n" +
                "(?, ?, ?, ?, ?)"
        );

        int i = 1;
        ps.setString(i++, city.getName());
        ps.setString(i++, city.getZipCode());
        ps.setString(i++, city.getState());
        ps.setDouble(i++, Double.parseDouble(city.getLatitude()));
        ps.setDouble(i++, Double.parseDouble(city.getLongitude()));

        ps.executeUpdate();

        ps.close();

        connection.close();
    }

    @Override
    public City getRandomCity() {
        return cities.get(new Random().nextInt(cities.size()));
    }


    public List<City> getCities(String latitude, String longitude, String distance) {

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);
        double dist = Double.parseDouble(distance);

        ArrayList<City> retVal = new ArrayList<City>();

        Iterator<City> iterator = cities.iterator();

        while(iterator.hasNext()) {

            City city = iterator.next();

            double citylat = Double.parseDouble(city.getLatitude());
            double citylon = Double.parseDouble(city.getLongitude());

            double distance_between = distanceBetween(lat, citylat, lon, citylon, 0.0, 0.0);

            // convert statute miles to meters
            if(distance_between < (dist * 1609.34)) {
                retVal.add(city);
            }
        }

        return retVal;
    }

    /*
    * Calculate distance between two points in latitude and longitude taking
    * into account height difference. If you are not interested in height
    * difference pass 0.0. Uses Haversine method as its base.
    *
    * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
    * el2 End altitude in meters
    * @returns Distance in Meters
    */
    public static double distanceBetween(double lat1, double lat2, double lon1,
                                         double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}