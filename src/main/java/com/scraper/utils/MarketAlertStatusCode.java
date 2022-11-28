package com.scraper.utils;

public interface MarketAlertStatusCode {
    //https://restfulapi.net/http-status-codes/
    int OK = 200; // delete request accepted
    int CREATED = 201; // post request accepted
    int BAD_REQUEST = 400;
    int UNSUPPORTED_MEDIA_TYPE = 415;
    int SERVICE_UNAVAILABLE = 503;

}
