package com.scraper.requests;

import com.scraper.utils.MarketAlertStatusCode;

public class HelpRequest {
    //helps the make request class by returning the status code
    private MakeRequest makeRequest;
    public HelpRequest(){
        makeRequest = null;
    }
    public HelpRequest(MakeRequest makeRequest){
        this.makeRequest = makeRequest;
    }
    public void setMakeRequest(MakeRequest makeRequest) {
        this.makeRequest = makeRequest;
    }

    // successful post requests return statusCode 201 whilst successful delete requests return statusCode 200
    public int makePost() {
        if(makeRequest == null){
            return MarketAlertStatusCode.SERVICE_UNAVAILABLE; // no request is made
        }else {
            int statusCode;
            int numberOfTries = 1;
            do{
                statusCode = makeRequest.makePostRequest();
                if (statusCode == 201) {
                    return statusCode;
                } else {
                    numberOfTries++;
                }
            }while(numberOfTries <= 3); // once 3 recalls are done, return code
            return statusCode;
        }
    }
    public int deletePost() {
        if(makeRequest == null){
            return MarketAlertStatusCode.SERVICE_UNAVAILABLE; // no request is made
        }else {
            int statusCode;
            int numberOfTries = 1;
            do{
                statusCode = makeRequest.makeDeleteRequest();
                if (statusCode == 200) {
                    return statusCode;
                } else {
                    numberOfTries++;
                }
            }while(numberOfTries <= 3);
            return statusCode;
        }
    }

}
