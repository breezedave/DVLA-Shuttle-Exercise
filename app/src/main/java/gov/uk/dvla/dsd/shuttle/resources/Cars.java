package gov.uk.dvla.dsd.shuttle.resources;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by breezed on 28/02/2015.
 */
public class Cars {
    private List<Car> carList;

    public Cars() {
        this.carList = new ArrayList<Car>();
    }

    public void add(Car car) {
        this.carList.add(car);
    }

    public List<Car> getCarList() {
        return this.carList;
    }


    public Car getNextCar(String location, DateTime now) {
        List<Car> journeys = new ArrayList<Car>();
        for(Car car:carList) {
            if(car.findNextDepartureFrom(location,now)!= -1) {
                journeys.add(car.getRestOfJourney(car.findNextDepartureFrom(location,now)));
            }
        }
        Collections.sort(journeys);
        return journeys.get(0);
    }

    public Car getNextCarTo(String location, String destination, DateTime now) {
        List<Car> journeys = new ArrayList<Car>();
        for(Car car:carList) {
            if(car.findNextDepartureFrom(location,now)!= -1) {
                journeys.add(car.getRestOfJourney(car.findNextDepartureFrom(location,now)));
            }
        }
        Collections.sort(journeys);
        for(Car car:journeys) {
            for(Destination destination1:car.getDestinations()) {
                if(destination1.getLocation().equals(destination)) {
                    return car;
                }
            }
        }
        return null;
    }

}
