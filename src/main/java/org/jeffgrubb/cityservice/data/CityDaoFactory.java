package org.jeffgrubb.cityservice.data;

/**
 * Created by jgrubb on 10/18/16.
 */
public class CityDaoFactory {

    private static CityDaoImpl _cityData = null;

    public static CityDao get() {
        if(_cityData == null) {
            _cityData = new CityDaoImpl();
        }
        return _cityData;
    }
}
