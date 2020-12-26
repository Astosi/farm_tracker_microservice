package com.farmTracker.demo;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;



public class ApiFactory {

    private HttpResponse<JsonNode> response;

    private String longitude;
    private String latitude;

    public ApiFactory(String longitude, String latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public JSONObject getApi(String apiType){

        JSONObject myObj = new JSONObject();

        switch (apiType){

        case "address":

        response = Unirest.get("https://location-to-address.p.rapidapi.com/v1/geocode/reverse?lat="+latitude+"&lon="+longitude+"&limit=1&lang=en")
                .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                .header("x-rapidapi-host", "location-to-address.p.rapidapi.com")
                .asJson();

        myObj = response.getBody().getObject().getJSONArray("features").getJSONObject(0).getJSONObject("properties");

        //myObj.get("name"); //city name
        //"name":"Marbella"
        //"asciiname":"Marbella"
        //"country":"ES"

        break;

        case "elevation" :

        response = Unirest.get("https://geo-services-by-mvpc-com.p.rapidapi.com/elevation?locations=" + longitude + "," + latitude)
                .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                .header("x-rapidapi-host", "geo-services-by-mvpc-com.p.rapidapi.com").asJson();

        myObj = response.getBody().getObject().getJSONArray("data").getJSONObject(0);

        //myObj.get("elevation");
        //  myObj.get("unit");

        break;

        case "forecast":

        response = Unirest.get("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+latitude+"%2C%20"+longitude+"&days=3")
                 .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                 .header("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                  .asJson();

        myObj = response.getBody().getObject().getJSONObject("forecast");

        break;

        }

        return myObj;

    }

    public JSONObject getApi(String apiType , String timeStart, String timeEnd){

        String startDate = timeStart;
        String endDate = timeEnd;

        JSONObject myObj = new JSONObject();

        switch (apiType) {

            case "meteorology":


                response = Unirest.get("https://weatherapi-com.p.rapidapi.com/history.json?dt="+startDate+"&q="+latitude+"%2C%20"+longitude+"&end_dt="+endDate+"&lang=en")
                        .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                        .header("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                        .asJson();

                myObj = response.getBody().getObject().getJSONObject("forecast");


              /*
              * getJSONArray("forecastday").getJSONObject(0).getJSONObject("day");
                */

                break;

            case "soildata":

                response = Unirest.get("https://ambee-soil-data.p.rapidapi.com/soil/latest/by-lat-lng?lng="+longitude+"&lat="+latitude)
                        .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                        .header("x-rapidapi-host", "ambee-soil-data.p.rapidapi.com")
                        .asJson();

                myObj = response.getBody().getObject().getJSONArray("data").getJSONObject(0);

                //myObj.get("soil_temperature");
                //  "soil_temperature"
                //"soil_moisture"
                break;


            case "elevation" :

                response = Unirest.get("https://geo-services-by-mvpc-com.p.rapidapi.com/elevation?locations=" + longitude + "," + latitude)
                        .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                        .header("x-rapidapi-host", "geo-services-by-mvpc-com.p.rapidapi.com").asJson();

                myObj = response.getBody().getObject().getJSONArray("data").getJSONObject(0);

                //myObj.get("elevation");
                //  myObj.get("unit");

                break;

            case "sun_position" :

                response = Unirest.get("https://geo-services-by-mvpc-com.p.rapidapi.com/sun_positions?location=43%2C5&date=2019-10-14")
                        .header("x-rapidapi-key", "6b7ef49cffmsh8087face9b53d4cp1b4bbajsnceb0adcb3a0a")
                        .header("x-rapidapi-host", "geo-services-by-mvpc-com.p.rapidapi.com").asJson();

                myObj = response.getBody().getObject().getJSONArray("data").getJSONObject(0);

                /*
                "sunrise":"2019-10-14T05:52:46Z"
                "sunset":"2019-10-14T17:01:43Z"
                 */

                break;

        }

        return myObj;
    }


}




