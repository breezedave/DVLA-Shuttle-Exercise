package gov.uk.dvla.dsd.shuttle;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import gov.uk.dvla.dsd.shuttle.resources.Car;
import gov.uk.dvla.dsd.shuttle.resources.Cars;
import gov.uk.dvla.dsd.shuttle.resources.Destination;
import gov.uk.dvla.dsd.shuttle.resources.Locations;
import gov.uk.dvla.dsd.shuttle.resources.Params;
import gov.uk.dvla.dsd.shuttle.resources.ReadFromAsset;
import gov.uk.dvla.dsd.shuttle.resources.Validation;


public class HomeScreenActivity extends ActionBarActivity {
    private Cars cars;
    private Params params;
    private Validation validation;
    private Spinner fromLoc;
    private Spinner toLoc;
    private EditText atText;
    private Button button;
    private TextView resultView;
    private Locations locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        String resultText;
        ReadFromAsset readFromAsset = new ReadFromAsset();
        this.validation = new Validation();
        this.cars = readFromAsset.getCars(this,"shuttles.txt");
        this.locations = new Locations(cars);
        this.params = readFromAsset.getParams(this,"params.txt");

        this.fromLoc = (Spinner)(findViewById(R.id.fromLoc));
        ArrayAdapter<String> fromLocAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, locations.getLocations());
        fromLocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLoc.setAdapter(fromLocAdapter);
        fromLoc.setSelection(fromLocAdapter.getPosition(params.getDefaultFrom()));

        this.toLoc = (Spinner)(findViewById(R.id.toLoc));
        ArrayAdapter<String> toLocAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, locations.getLocations());
        toLocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toLoc.setAdapter(toLocAdapter);
        toLoc.setSelection(toLocAdapter.getPosition(params.getDefaultTo()));

        this.atText = (EditText)(findViewById(R.id.time));
        DateTime selTime = new DateTime();
        atText.setText(selTime.toString("HH:mm"));

        resultText = "Please select your journey";
        this.resultView = (TextView)(findViewById(R.id.result));
        resultView.setText(resultText);

        this.button = (Button)(findViewById(R.id.button));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateJourney();
            }
        });

        populateJourney();
    }

    public void populateJourney() {
        String from = (String)(fromLoc.getSelectedItem());
        String to = (String)(toLoc.getSelectedItem());
        String at = atText.getText().toString();
        if(!validation.validateLoc(from)) {return;}
        if(!validation.validateLoc(to)) {return;}
        if(!validation.validateTime(at)) {return;}
        int hr = Integer.parseInt(at.split(":")[0]);
        int min = Integer.parseInt(at.split(":")[1]);
        Car result = cars.getNextCarTo(from,to,new DateTime(0,1,1,hr,min));
        String resultTxt;
        resultTxt = "";
        if(result == null) {
            resultTxt += "Route not found";
        } else {
            resultTxt = result.getVRM() + "\n";
            for (int i = 0; i < result.getDestinations().size(); i++) {
                resultTxt += "\n" + result.getDestinations().get(i).toString();
                if(result.getDestinations().get(i).getLocation().equals(to)) {break;}
            }
        }
        resultView.setText(resultTxt);
    }



}
