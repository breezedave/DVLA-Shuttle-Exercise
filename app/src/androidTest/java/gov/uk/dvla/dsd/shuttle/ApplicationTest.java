package shuttle.dsd.dvla.uk.gov.myapplication;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import junit.framework.Assert;
import org.joda.time.DateTime;


import shuttle.dsd.dvla.uk.gov.myapplication.resources.Car;
import shuttle.dsd.dvla.uk.gov.myapplication.resources.Cars;
import shuttle.dsd.dvla.uk.gov.myapplication.resources.Destination;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAssertNumbersWork() {
        int value = 1;
        int expectedResult = 2;
        int result = ++value;
        assertEquals(expectedResult, result);
    }

    public void testSuccessDestination() {
        String location = "Newport";
        DateTime departure = new DateTime(0, 1, 1, 12, 30);
        String expectedResult = "Newport 12:30";

        String result = new Destination(location, departure).toString();

        Assert.assertEquals(expectedResult, result);
    }

    public void testSuccessCarDepartureSort() {
        Car car = new Car("A1");

        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 12, 30)));
        car.addDestinations(new Destination("Abertawe", new DateTime(0, 1, 1, 14, 20)));
        car.addDestinations(new Destination("Cardiff", new DateTime(0, 1, 1, 10, 20)));

        String firstDest = "Cardiff 10:20";
        String secondDest = "Newport 12:30";
        String thirdDest = "Abertawe 14:20";

        assertEquals(firstDest, car.getDestinations().get(0).toString());
        assertEquals(secondDest, car.getDestinations().get(1).toString());
        assertEquals(thirdDest, car.getDestinations().get(2).toString());
    }

    public void testNextDepartureFromNewport() {
        Car car = new Car("A1");

        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 12, 30)));
        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 13, 30)));
        car.addDestinations(new Destination("Cardiff", new DateTime(0, 1, 1, 14, 20)));
        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 14, 30)));
        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 15, 30)));
        car.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 16, 30)));

        int expectedResult = 3;
        int expectedJourneySize = 3;
        int result = car.findNextDepartureFrom("Newport", new DateTime(0, 1, 1, 14, 10));

        int resultJourneySize = car.getRestOfJourney(result).getDestinations().size();

        assertEquals(expectedResult, result);
        assertEquals(expectedJourneySize, resultJourneySize);
    }

    public void testNextDepartureFromNewportMultipleCars() {
        Car car1 = new Car("A1");
        Car car2 = new Car("A2");

        car1.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 12, 30)));
        car2.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 13, 30)));
        car1.addDestinations(new Destination("Cardiff", new DateTime(0, 1, 1, 14, 20)));
        car2.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 14, 30)));
        car1.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 15, 30)));
        car2.addDestinations(new Destination("Newport", new DateTime(0, 1, 1, 16, 30)));
        car1.addDestinations(new Destination("Cardiff", new DateTime(0, 1, 1, 18, 20)));

        Cars cars = new Cars();
        cars.add(car1);
        cars.add(car2);

        String expectedCar1 = "A2";
        String expectedCar2 = "A1";
        String expectedDestination = "Newport 14:30";

        Car resultCar1 = cars.getNextCar("Newport", new DateTime(0,1,1,14,10));
        Car resultCar2 = cars.getNextCarTo("Newport", "Cardiff", new DateTime(0,1,1,14,10));
        Car resultCar3 = cars.getNextCarTo("Newport", "Borth", new DateTime(0,1,1,14,10));

        assertEquals(expectedCar1,resultCar1.getVRM());
        assertEquals(expectedDestination,resultCar1.getDestinations().get(0).toString());
        assertEquals(expectedCar2,resultCar2.getVRM());
        assertNull(resultCar3);

    }
}