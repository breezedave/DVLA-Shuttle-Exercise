package shuttle.dsd.dvla.uk.gov.myapplication.resources;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by breezed on 28/02/2015.
 */
public class Car implements Comparable<Car>{
    private String VRM;
    private List<Destination> destinations;

    public Car(String VRM) {
        this.VRM = VRM;
        this.destinations = new ArrayList<Destination>();
    }

    public Car(String VRM, List<Destination> destinations) {
        this.VRM = VRM;
        this.destinations = destinations;
    }

    public String getVRM() {
        return VRM;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void addDestinations(Destination destination) {
        this.destinations.add(destination);
        Collections.sort(this.destinations);
    }

    public int findNextDepartureFrom(String location,DateTime fromTime) {
        int i = 0;
        for(Destination destination: this.destinations) {
            if(location.equals(destination.getLocation()) && !fromTime.isAfter(destination.getDeparture())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Car getRestOfJourney(int destinationStart) {
        List<Destination> destinations = this.destinations.subList(destinationStart,this.destinations.size());
        String VRM = this.VRM;
        return new Car(VRM,destinations);
    }


    @Override
    public int compareTo(Car car) {
        DateTime compareTime = car.getDestinations().get(0).getDeparture();

        if(this.destinations.get(0).getDeparture().isBefore(compareTime)) { return -1;};
        return 1;
    }


}
