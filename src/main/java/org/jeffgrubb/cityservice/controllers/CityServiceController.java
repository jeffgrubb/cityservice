package org.jeffgrubb.cityservice.controllers;

/**
 * Created by jgrubb on 10/18/16.
 */
import org.jeffgrubb.cityservice.data.City;
import org.jeffgrubb.cityservice.data.CityDao;
import org.jeffgrubb.cityservice.data.CityDaoFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityServiceController {

    @RequestMapping("/search")
    public List<City> search(@RequestParam(value="lat", defaultValue="0") String lat,
                           @RequestParam(value="lon", defaultValue="0") String lon,
                           @RequestParam(value="dist", defaultValue="0") String dist) {

        CityDao cityData = CityDaoFactory.get();
        return cityData.getCities(lat, lon, dist);
    }

    @RequestMapping("/random")
    public City random() {
        CityDao cityData = CityDaoFactory.get();
        return cityData.getRandomCity();
    }

}
