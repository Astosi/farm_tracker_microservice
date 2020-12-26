package com.farmTracker.demo.Service;

import com.farmTracker.demo.ApiFactory;
import com.farmTracker.demo.Model.Plant;
import com.farmTracker.demo.Model.PlantType;
import com.farmTracker.demo.Reminder.ReminderService;
import com.farmTracker.demo.Suggestion.SuggestionService;
import com.farmTracker.demo.Warning.WarningService;
import farmtracker.springbootmongodb.Converter;

import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @Service        //Servis olarak springe tanittik
    public class PlantService {

        public static Plant plant;

        public void addPlant(Plant plant){

            JSONObject myObj;

            ApiFactory apifactory = new ApiFactory(plant.getLongitude(), plant.getLatitude());

            myObj = apifactory.getApi("elevation");

            double elevation = Integer.parseInt(myObj.get("elevation").toString());

            myObj = apifactory.getApi("address");

            Map<String, String> address =new HashMap<>();
            address.put("name", myObj.get("name").toString());

            if (myObj.has("street"))
                address.put("street", myObj.get("street").toString());

            if (myObj.has("suburb") )
                address.put("suburb", myObj.get("suburb").toString());

            address.put("county", myObj.get("county").toString());
            address.put("state", myObj.get("state").toString());
            address.put("country", myObj.get("country").toString());
            address.put("country_code", myObj.get("country_code").toString());


            plant.setFarmCalendarDefault(); //sets calendar today to next harvest date.
            plant.setNextHarvestDate(new Converter().addDays(plant.getPlantingDate(), plant.getDaysToHarvest()));

            plant.setElevation(elevation);
            plant.setAddress(address);
            plant.setSuggestedPlants(new SuggestionService().getSuggestedPlants(plant.getLongitude(), plant.getLatitude(), new Converter().getDateToday()));

            this.plant = plant;

        }


        public Map<String, String> getFarmAddress() {
            return plant.getAddress();
        }


        public List<PlantType> getSuggestedPlants(){
            return plant.getSuggestedPlants();
        }

        public String getNextHarvestDate(){
            return plant.getNextHarvestDate();
        }


        public  Map<String, Map<String, String>> getReminderMessage(){

               if (plant != null){
                ReminderService reminderService = new ReminderService(plant.getLongitude(), plant.getLatitude(), new Converter().getDateToday());
                plant.setReminderMessages(reminderService.getReminderMessage());
            }
            return plant.getReminderMessages();
        }

        public Map<String, Map<String, String>> getWarningMessage(){

            if (plant!=null) {
                WarningService warningService = new WarningService(plant.getLongitude(), plant.getLatitude(), new Converter().getDateToday());
                plant.setWarningMessages(warningService.getWarningMessages());

            }
            return plant.getWarningMessages();
        }

        public Map<String, String> getSunriseSunset(){

            return new Meteorology(plant.getLongitude(), plant.getLatitude(),"").getSunriseSunsetTime();
        }

        public void addIrrigationDate(List<String> irrigationDate) {

            plant.setIrrigationDate(irrigationDate);

        }

        public List<String> getWateredDays() {
            return plant.getIrrigationDates();
        }
    }