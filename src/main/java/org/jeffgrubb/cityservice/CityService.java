package org.jeffgrubb.cityservice;

/**
 * Created by jgrubb on 10/18/16.
 */

import org.jeffgrubb.cityservice.data.CityDao;
import org.jeffgrubb.cityservice.data.CityDaoFactory;
import org.jeffgrubb.cityservice.discovery.RegisterServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


@SpringBootApplication
public class CityService {

    private static final String     PATH = "/org/jeffgrubb/cityservice/discovery/services/";

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient("zookeeper01.home.prod.jeffgrubb.org:2181,zookeeper02.home.prod.jeffgrubb.org:2181,zookeeper02.home.prod.jeffgrubb.org:2181", new ExponentialBackoffRetry(1000, 3));
        System.out.println("Namespace: " + client.getNamespace());
        client.start();
        RegisterServer server = new RegisterServer(client, PATH, "cityservice", "City Service");


        try {
            CityDao cityData = CityDaoFactory.get();
            cityData.init();

            server.start();
            SpringApplication.run(CityService.class, args);
        }
        finally {
            server.close();
        }
    }
}
