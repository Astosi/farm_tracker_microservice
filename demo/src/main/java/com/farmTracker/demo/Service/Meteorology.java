package com.farmTracker.demo.Service;

import com.farmTracker.demo.ApiFactory;
import farmtracker.springbootmongodb.Converter;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.*;



public class Meteorology {

    private String longitude;
    private String latitude;
    private Calendar time;
    private Calendar time1;
    ApiFactory factory;
    Converter converter;
    JSONObject myObj;

    public Meteorology(String longitude, String latitude , String time) {

        this.longitude = longitude;
        this.latitude = latitude;

        this.converter = new Converter();
        this.time = converter.stringToCal(time);
        this.time1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        this.factory = new ApiFactory(longitude, latitude);

    }

    public double getAvPrecip(){ //precipitation

        System.out.println("method --> get avarage precip working...");
        time1.setTime(time.getTime());  //creating local variable

        double totalIntensity = 0;
        int numberOfDays = 5;
        //String as "yyyy-MM-dd"
        // number of days we checked is 6 because we add our date 5 but api calculates our starting date as well

        String endDate = converter.calToString(time1);
        //String endDate = converter.subDays(time1, 1); //returns me string
        String startDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate,endDate);
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        for (int i = 0; i<=numberOfDays ; i++) {
            totalIntensity += Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("totalprecip_mm").toString());
            //jsonArray.getJSONObject(i) is starting date
        }

        System.out.println("average precipitation ---->"+totalIntensity/numberOfDays + "\n");
        return totalIntensity/numberOfDays;

    }

    public Map<String,Double> getPrecip(){ //for 5 days in past

        System.out.println("method --> get precip working...");
        time1.setTime(time.getTime());

        Map<String, Double> forecast5days= new LinkedHashMap<>();
        int numberOfDays = 5;

        String endDate = converter.calToString(time1);
       // String endDate = converter.subDays(time1, 1);
        String startDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate, endDate);

        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        for (int i = 0; i<numberOfDays ; i++) {

            String dateOfData = jsonArray.getJSONObject(i).get("date").toString();


            Calendar cal= converter.stringToCal(dateOfData);
            Double totalprecipMM = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("totalprecip_mm").toString());
            System.out.println(cal.get(Calendar.DAY_OF_MONTH) + " " + totalprecipMM);

            forecast5days.put(dateOfData , totalprecipMM);
        }

        return forecast5days;

    }

    public double getAvarageHumidity(){

        System.out.println("method --> get average humidity is working...");
        time1.setTime(time.getTime());

        double totalHumidity = 0;
        int numberOfDays = 5;

        String endDate = converter.calToString(time1);
        //String endDate = converter.subDays(time1,1);     //start from a day before
        String startDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate, endDate);
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        for (int i = 0; i<numberOfDays ; i++) {
            totalHumidity += Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("avghumidity").toString());
        }

        System.out.println("getAvarageHumidity -------> " + totalHumidity/numberOfDays);
        return totalHumidity/numberOfDays;

    }
    /*
            public double getSoilTemp(){

                myObj = factory.getApi("soildata" ,time);
                return  Double.parseDouble(myObj.get("soil_temperature").toString());
            }

            public double getSoilMoisture(){

                myObj = factory.getApi("soildata" ,time);
                return  Double.parseDouble(myObj.get("soil_moisture").toString());

            }
    */
    public List<Double> getMaxMinTemp(){

        System.out.println("method --> get max-min temp is working...");
        time1.setTime(time.getTime());

        double minTemp = 100;
        double maxTemp = -10;
        int numberOfDays = 5;

        //String endDate = converter.calToString(time1);
        String endDate = converter.subDays(time1, 1);
        String startDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate, endDate);   //6 days in the past, get(5) is today
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        for (int i = 0; i< numberOfDays ; i++) {

            double tempLow = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("mintemp_c").toString());
            minTemp =  Math.min(minTemp , tempLow);

            double tempMax =  Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("maxtemp_c").toString());
            maxTemp = Math.max(maxTemp , tempMax);
        }

        List<Double> maxMinTemps = new ArrayList<>();
        maxMinTemps.add(maxTemp);
        maxMinTemps.add(minTemp);

        System.out.println("max temp ------> " + maxMinTemps.get(0) + "\nmin temp ----->" + maxMinTemps.get(1));
        return maxMinTemps;
    }

    public double getAvMaxTempChange(){

        time1.setTime(time.getTime());

        double maxDifference = 0;
        int numberOfDays = 5;

        System.out.println("method --> get avarage temp change is working... ");

       // String endDate = converter.calToString(time1);
        String endDate = converter.subDays(time1, 1);
        String startDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate, endDate);
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        for (int i = 0; i< numberOfDays ; i++) {

            double tempLow = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("mintemp_c").toString());
            double tempMax =  Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("maxtemp_c").toString());

            double difference = tempMax - tempLow;

            maxDifference = Math.max(difference, maxDifference);
        }

        System.out.println("max difference --------> " + maxDifference + "\n");
        return maxDifference;
    }

    public Map<String, Double> forecastTempDif(){ //3 days in past

        System.out.println("method --> get average humidity is working...");

        myObj = factory.getApi("forecast");
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        int numberOfDays = 3;

        Map<String, Double> forecast3days= new LinkedHashMap<>();

        for (int i = 0; i< numberOfDays ; i++) {

            double tempLow = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("mintemp_c").toString());
            double tempMax = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("maxtemp_c").toString());
            double difference = tempMax - tempLow;

            String dateOfData = jsonArray.getJSONObject(i).get("date").toString();

            System.out.println("temp max" + tempMax + " " + tempLow + "=--> "+ dateOfData);

         //   System.out.println(cal.getTime() + "---> new cal");

            forecast3days.put(dateOfData , difference);
        }

        return forecast3days;
    }

    public Map<String, Map<String, Double>> forecastMaxMinTemp(){ //3 days in the future //0 is today

        System.out.println("method --> forecast max-min temp is working...");

        myObj = factory.getApi("forecast");
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        int numberOfDays = 3;

        Map<String,  Map<String, Double>> forecast3days= new LinkedHashMap<>();

        for (int i = 0; i< numberOfDays ; i++) {

            double temMin = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("mintemp_c").toString());
            double tempMax = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("maxtemp_c").toString());

            String dateOfData = jsonArray.getJSONObject(i).get("date").toString();

            forecast3days.put(dateOfData , Map.of( "max" ,tempMax,"min" ,temMin));
        }

        return forecast3days;
    }

    public Map<String, Double> forecastPrecip(){ //3 days in future 0 is today

        System.out.println("method --> forecast precip is working...");

        myObj = factory.getApi("forecast" );
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        int numberOfDays = 3;

        Map<String, Double> forecast3days= new LinkedHashMap<>();

        for (int i = 0; i<numberOfDays ; i++) {

            double rainFreq = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("totalprecip_mm").toString());

            String dateOfData = jsonArray.getJSONObject(i).get("date").toString();
            //System.out.println(rainFreq + " " + i + " " + dateOfData);

            forecast3days.put(dateOfData , rainFreq);
        }

        return forecast3days;
    }

    public Map<String, Double> forecastIfSnow(){ //3 days in future 0 is today

        System.out.println("method --> forecast if snow is working...");

        myObj = factory.getApi("forecast");  //forecast gets data starting from when the call occurs
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        int numberOfDays = 3;

        Map<String, Double> forecast3days= new HashMap<>();

        for (int i = 0; i< numberOfDays ; i++) {

            double chanceOfSnow = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("day").get("daily_chance_of_snow").toString());

            String dateOfData = jsonArray.getJSONObject(i).get("date").toString();
            System.out.println(chanceOfSnow + " " + i + " " + dateOfData);

            forecast3days.put(dateOfData , chanceOfSnow);
        }

        return forecast3days;
    }

    public Map<String, String> getSunriseSunsetTime(){

        System.out.println("method --> sunrise - sunset time is working...");

        time1.setTime(time.getTime());

        Map<String, String> SunriseSunsetTime = new HashMap<>();
        int numberOfDays = 5;

        String startDate = converter.subDays(time1,1);     //start from a day before
        String endDate = converter.subDays(time1, numberOfDays);

        myObj = factory.getApi("meteorology" ,startDate, endDate);
        JSONArray jsonArray = myObj.getJSONArray("forecastday");

        SunriseSunsetTime.put("sunrise" , jsonArray.getJSONObject(0).getJSONObject("astro").get("sunrise").toString());
        SunriseSunsetTime.put("sunset" ,jsonArray.getJSONObject(0).getJSONObject("astro").get("sunset").toString());

        System.out.println("Sunrise ---------> " +SunriseSunsetTime.get("sunrise") + "\n  Sunset---->" + SunriseSunsetTime.get("sunset"));
        return SunriseSunsetTime;

    }

    public int getElevation(){
        myObj = factory.getApi("elevation");
        return Integer.parseInt(myObj.get("elevation").toString());
    }

}