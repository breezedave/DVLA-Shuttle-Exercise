package gov.uk.dvla.dsd.shuttle.resources;

/**
 * Created by breezed on 01/03/2015.
 */
public class Validation {
    public boolean validateLoc(String value) {
        if(value.length()>0) {return true;}
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
