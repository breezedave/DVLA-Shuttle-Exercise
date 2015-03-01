package gov.uk.dvla.dsd.shuttle.resources;

import android.app.Application;
import android.content.Context;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by breezed on 01/03/2015.
 */
public class ReadFromAsset {
    public Cars getCars(Context context, String filePath) {
        Cars cars = new Cars();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            context.getAssets().open(filePath)
                    )
            );

            String mLine = reader.readLine();
            while (mLine != null) {
                Boolean found = false;
                String[] lineArr = mLine.split(",");
                String VRM = lineArr[0];
                String location = lineArr[1];
                int hr =  Integer.parseInt(lineArr[2].split(":")[0]);
                int min = Integer.parseInt(lineArr[2].split(":")[1]);

                for(Car car:cars.getCarList()) {
                    if(car.getVRM().equals(VRM)) {
                        car.addDestinations(new Destination(location, new DateTime(0,1,1,hr,min)));
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    Car car = new Car(VRM);
                    car.addDestinations(new Destination(location,new DateTime(0,1,1,hr,min)));
                    cars.add(car);
                }
                mLine = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cars;
    }

}
