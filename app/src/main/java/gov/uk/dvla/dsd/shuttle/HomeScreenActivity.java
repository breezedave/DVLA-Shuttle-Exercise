package gov.uk.dvla.dsd.shuttle;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import gov.uk.dvla.dsd.shuttle.resources.Car;
import gov.uk.dvla.dsd.shuttle.resources.Cars;
import gov.uk.dvla.dsd.shuttle.resources.Locations;
import gov.uk.dvla.dsd.shuttle.resources.MySQLite;
import gov.uk.dvla.dsd.shuttle.resources.ParamsDataSource;
import gov.uk.dvla.dsd.shuttle.resources.ReadFromAsset;
import gov.uk.dvla.dsd.shuttle.resources.Tuple;
import gov.uk.dvla.dsd.shuttle.resources.Validation;


public class HomeScreenActivity extends ActionBarActivity {
    private Cars cars;
    private Validation validation;
    private Spinner fromLoc;
    private Spinner toLoc;
    private EditText atText;
    private Button button;
    private TextView resultView;
    private Locations locations;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        String resultText;
        ReadFromAsset readFromAsset = new ReadFromAsset();
        this.validation = new Validation();

        final ParamsDataSource dataSource = new ParamsDataSource(this);
        dataSource.open();

        if(dataSource.carCount() <=0) {
            dataSource.fillShuttleFromTxt(this);
        }
        //this.cars = readFromAsset.getCars(this,"shuttles.txt");
        this.cars = dataSource.getCarsFromDb(this);
        this.locations = new Locations(cars);


        this.fromLoc = (Spinner)(findViewById(R.id.fromLoc));
        ArrayAdapter<String> fromLocAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, locations.getLocations());
        fromLocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLoc.setAdapter(fromLocAdapter);
        if(dataSource.getParam("defaultFrom") != "not found") {
            fromLoc.setSelection(fromLocAdapter.getPosition(dataSource.getParam("defaultFrom")));
        }
        fromLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataSource.deleteParam("defaultFrom");
                dataSource.addParam(new Tuple("defaultFrom",(String)(fromLoc.getSelectedItem())));
                Log.i("defaultFrom", dataSource.getParam("defaultFrom"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        this.toLoc = (Spinner)(findViewById(R.id.toLoc));
        ArrayAdapter<String> toLocAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, locations.getLocations());
        toLocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toLoc.setAdapter(toLocAdapter);
        if(dataSource.getParam("defaultTo") != "not found") {
            toLoc.setSelection(fromLocAdapter.getPosition(dataSource.getParam("defaultTo")));
        }
        toLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataSource.deleteParam("defaultTo");
                dataSource.addParam(new Tuple("defaultTo",(String)(toLoc.getSelectedItem())));
                Log.i("defaultTo", dataSource.getParam("defaultTo"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

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
