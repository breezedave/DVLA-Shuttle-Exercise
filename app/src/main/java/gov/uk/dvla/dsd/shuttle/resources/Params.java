package gov.uk.dvla.dsd.shuttle.resources;

/**
 * Created by breezed on 09/04/2015.
 */
public class Params {
    public String getDefaultFrom() {
        return defaultFrom;
    }

    public void setDefaultFrom(String defaultFrom) {
        this.defaultFrom = defaultFrom;
    }

    public String getDefaultTo() {
        return defaultTo;
    }

    public void setDefaultTo(String defaultTo) {
        this.defaultTo = defaultTo;
    }

    private String defaultFrom;
    private String defaultTo;
}
