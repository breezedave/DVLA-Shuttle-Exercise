package shuttle.dsd.dvla.uk.gov.myapplication.resources;

import org.joda.time.DateTime;

import java.util.Date;


/**
 * Created by breezed on 27/02/2015.
 */
public class Destination implements Comparable<Destination>{
    private DateTime departure;
    private String location;

    public DateTime getDeparture() {
        return departure;
    }

    public void setDeparture(DateTime departure) {
        this.departure = departure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Destination(String location, DateTime departure) {
        setDeparture(departure);
        setLocation(location);
    }

    @Override
    public String toString() {
        return getLocation() + " " + getDeparture().toString("HH:mm");
    }

    @Override
    public int compareTo(Destination destination) {
        DateTime compareTime = destination.getDeparture();

        if(this.departure.isBefore(compareTime)) { return -1;};
        return 1;
    }


}


