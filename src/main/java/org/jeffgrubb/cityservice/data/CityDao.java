package org.jeffgrubb.cityservice.data;

import java.util.List;

/**
 * Created by jgrubb on 10/18/16.
 */
public interface CityDao {
    void init() throws Exception;
    void addCity(City city) throws Exception;
    List<City> getCities(String latitude, String longitude, String distance);
    City getRandomCity();
}