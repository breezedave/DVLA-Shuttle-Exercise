package gov.uk.dvla.dsd.shuttle.resources;

import android.util.Log;

/**
 * Created by breezed on 01/03/2015.
 */
public class Validation {
    public boolean validateLoc(String value) {
        Log.i("validate","validate: " + value);
        if(value!= null && value.length()>0) {return true;}
        return false;
    }

    public boolean validateTime(String value) {
        try {
            int hr = Integer.parseInt(value.split(":")[0]);
            int min = Integer.parseInt(value.split(":")[1]);
            if(hr>=0 && hr<24) {
                if(min>=0 && min<60) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
