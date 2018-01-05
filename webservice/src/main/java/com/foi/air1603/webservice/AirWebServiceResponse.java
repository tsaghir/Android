package com.foi.air1603.webservice;

/**
 * Created by Karlo on 9.11.2016..
 */

public class AirWebServiceResponse {

    public String message;
    public int statusCode;
    public String data;
    public String table;

    public String getTable() { return table; }

    public String getMessage() { return message; }

    public int getStatusCode() {
        return statusCode;
    }

    public String getData() { return data;}

}
