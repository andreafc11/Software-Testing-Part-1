package com.scraper.requests;

import com.scraper.models.Item;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MakeRequest {
    //makes the appropriate requests (post or delete)
    private JSONObject jsonLine;
    private final String marketalertUrl;
    private final UUID marketalertID;

    public MakeRequest(){
        jsonLine = new JSONObject();
        marketalertUrl = "https://api.marketalertum.com/Alert";
        marketalertID = UUID.fromString("94817701-f7f3-4f5d-9419-c6da1b8d6d84");
    }

    // getters
    private JSONObject getJSONObject(){
        return jsonLine;
    }

    // setters
    public void setJSONObject(@NotNull Item item){
        jsonLine = new JSONObject();
        jsonLine.put("alertType", item.getAlertType());
        jsonLine.put("heading", item.getHeading());
        jsonLine.put("description", item.getDescription());
        jsonLine.put("url", item.getUrl());
        jsonLine.put("image", item.getImage());
        jsonLine.put("postedBy", marketalertID);
        jsonLine.put("priceInCents", item.getPriceInCents());
    }

    //methods

    // post request using UnirestApi
    public int makePostRequest() {
        HttpResponse<JsonNode> response = Unirest.post(marketalertUrl)
                .header("Content-Type", "application/json")
                .body(getJSONObject())
                .asJson();
        return response.getStatus();
    }

    // delete request using UnirestApi
    public int makeDeleteRequest() {
        HttpResponse<JsonNode> response = Unirest.delete(marketalertUrl + "?userId=" + marketalertID).asJson();
        return response.getStatus();
    }
}
