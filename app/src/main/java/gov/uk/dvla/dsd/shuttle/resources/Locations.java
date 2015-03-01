package gov.uk.dvla.dsd.shuttle.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by breezed on 01/03/2015.
 */
public class Locations {
    private List<String> locations;

    public void addLocation(String location) {
        locations.add(location);
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public Locations(Cars cars) {
        locations = new ArrayList<String>();
        for(Car car:cars.getCarList()) {
            for(Destination destination: car.getDestinations()) {
                if(!locations.contains(destination.getLocation())) {
                    locations.add(destination.getLocation());
                }
            }
        }
        Collections.sort(locations);
    }


}
