package gov.uk.dvla.dsd.shuttle;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.joda.time.DateTime;

import gov.uk.dvla.dsd.shuttle.resources.Car;
import gov.uk.dvla.dsd.shuttle.resources.Cars;
import gov.uk.dvla.dsd.shuttle.resources.Destination;


public class HomeScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        Cars cars = new Cars();
        Car car1 = new Car("A1");
        Car car2 = new Car("A2");
        cars.add(car1);
        cars.add(car2);
        car1.addDestinations(new Destination("Newport",new DateTime(0,1,1,10,0)));
        car1.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,10,30)));
        car1.addDestinations(new Destination("Newport",new DateTime(0,1,1,11,0)));
        car1.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,11,30)));
        car1.addDestinations(new Destination("Newport",new DateTime(0,1,1,12,0)));
        car1.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,12,30)));
        car1.addDestinations(new Destination("Newport",new DateTime(0,1,1,13,0)));
        car1.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,13,30)));
        car2.addDestinations(new Destination("Newport",new DateTime(0,1,1,12,0)));
        car2.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,12,30)));
        car2.addDestinations(new Destination("Newport",new DateTime(0,1,1,13,0)));
        car2.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,13,30)));
        car2.addDestinations(new Destination("Newport",new DateTime(0,1,1,14,0)));
        car2.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,14,30)));
        car2.addDestinations(new Destination("Newport",new DateTime(0,1,1,15,0)));
        car2.addDestinations(new Destination("Cardiff",new DateTime(0,1,1,15,30)));

        String dummyToast = cars.getNextCar("Newport",new DateTime(0,1,1,new DateTime().getHourOfDay(),new DateTime().getMinuteOfHour())).getVRM();
        Toast toast = Toast.makeText(this,dummyToast,Toast.LENGTH_LONG);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
