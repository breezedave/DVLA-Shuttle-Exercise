package gov.uk.dvla.dsd.shuttle.resources;

/**
 * Created by breezed on 10/04/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import gov.uk.dvla.dsd.shuttle.HomeScreenActivity;

public class ParamsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLite dbHelper;
    private String[] allColumns = { MySQLite.COLUMN_ID,
            MySQLite.PARAM_NAME, MySQLite.PARAM_VAL };

    public ParamsDataSource(Context context) {
        dbHelper = new MySQLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addParam(Tuple param) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.PARAM_NAME, param.key);
        values.put(MySQLite.PARAM_VAL,param.val);
        database.insert(MySQLite.PARAM_TABLE_NAME,null,values);
    }

    public void deleteParam(String param) {
        database.delete(MySQLite.PARAM_TABLE_NAME, MySQLite.PARAM_NAME
                + " = '" + param + "'", null);
    }

    public List<String> getAllParams() {
        List<String> params = new ArrayList<String>();

        Cursor cursor = database.query(MySQLite.PARAM_TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String param = cursor.getString(2);
            params.add(param);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return params;
    }

    public String getParam(String paramName) {
        try {
            String query = "Select * from " + MySQLite.PARAM_TABLE_NAME + " where " + MySQLite.PARAM_NAME + "= '" + paramName + "'";
            Cursor cursor = database.rawQuery(query,null);
            cursor.moveToFirst();
            String result = cursor.getString(2);
            cursor.close();
            return result;
        } catch (Exception e) {
            return "not found";
        }
    }

    public int carCount() {
        try {
            String query = "Select count(*) from " + MySQLite.SHUTTLE_TABLE_NAME + ";";
            Cursor cursor = database.rawQuery(query,null);
            cursor.moveToFirst();
            int result = cursor.getInt(0);
            cursor.close();
            Log.i("Car Count:","" + result);
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    public void getShuttleCols() {
        String query = "Select * from " + MySQLite.SHUTTLE_TABLE_NAME + ";";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getColumnCount();i++) {
            Log.i("Shuttle Columns:", cursor.getColumnName(i));
        }
        cursor.close();
    }

    public Boolean fillShuttleFromTxt(Context context) {
        try {
            //getShuttleCols();
            ReadFromAsset readFromAsset = new ReadFromAsset();
            List<String> carStrList = readFromAsset.getCarString(context, "shuttles.txt");
            for (String str : carStrList) {
                String[] splitValues = str.split(",");
                ContentValues values = new ContentValues();
                values.put(MySQLite.SHUTTLE_VRM, splitValues[0]);
                values.put(MySQLite.SHUTTLE_LOC, splitValues[1]);
                values.put(MySQLite.SHUTTLE_TIME, splitValues[2]);
                database.insertOrThrow(MySQLite.SHUTTLE_TABLE_NAME, null, values);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cars getCarsFromDb(Context context) {
        Cars cars = new Cars();

        String query = "Select * from " + MySQLite.SHUTTLE_TABLE_NAME + ";";
        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Boolean found = false;
            String VRM = cursor.getString(1);
            String location = cursor.getString(2);
            int hr =  Integer.parseInt(cursor.getString(3).split(":")[0]);
            int min = Integer.parseInt(cursor.getString(3).split(":")[1]);

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
            cursor.moveToNext();
        }
        cursor.close();

        return cars;
    }

}
