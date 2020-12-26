package com.farmTracker.demo.Reminder;

import com.farmTracker.demo.Model.Plant;
import com.farmTracker.demo.Service.Meteorology;

import java.util.*;
import java.util.stream.Collectors;

import static com.farmTracker.demo.Service.PlantService.plant;


public class ReminderService {

    private String longitude;
    private String latitude;
    private String time;     //String as "yyyy-MM-dd"

    Map<String, Map<String, String>> reminderMessage= new HashMap<>();
    Map<String, String> message = new HashMap<>();

    Meteorology meteorology;

    public ReminderService(String longitude, String latitude , String time) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;

        this.meteorology = new Meteorology(longitude, latitude, time);

    }


      public  Map<String, Map<String, String>> getReminderMessage(){

        Map<String , Boolean> rainFlag =  getRainyDays(meteorology.getPrecip());
        Map<String , Boolean> forecastRain = getRainyDays(meteorology.forecastPrecip()); //3 days, 0 is today



                checkForIrrigation(plant,rainFlag);
                checkForIrrigationForecast(plant, forecastRain);
                //set a reminder message with the plant

                reminderMessage.put(plant.getPlantType().getPlant_name(), message);


            return reminderMessage;
            }

    protected void checkForIrrigationForecast(Plant plant , Map<String , Boolean> forecastRain) {

        System.out.println("reminder service --> check for irrigarion forecast");

        String message = "no suggestions";
        int daysWithoutRain = 0;

        for (String string : forecastRain.keySet()){

            if (!forecastRain.get(string))
                daysWithoutRain++;
           }
            if (daysWithoutRain > plant.getProperIrrigation().get("min")){
                message = "Expecting no rain for " + daysWithoutRain + " days. It can be dangerous for " + plant.getPlantType().getPlant_name();

      }

        this.message.put("suggestion", message);
    }

    protected void checkForIrrigation(Plant plant, Map<String , Boolean> rainFlag){

        Map<String, Boolean> farmCalendar = plant.getFarmCalendar();  //get farm calendar which includes your irrigation information
        String message = "no reminders";  //default message


        List<String> ReversedKeySet = farmCalendar.keySet().stream()
                .sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        //Reversed because we wanted values from today to past

        int counter = 0; //counter for counting days without watering

        for (String cal : ReversedKeySet) {
            if (farmCalendar.get(cal) != null && rainFlag.get(cal) != null) {

                if (farmCalendar.get(cal) || rainFlag.get(cal)) {
                    break;
                } else {
                    counter++;
                }

                if (counter >= plant.getProperIrrigation().get("min")) {      //min day of watering
                    message = counter + " days without watering for " + plant.getPlantType().getPlant_name();
                    break;
                }
            }
        }
        this.message.put("reminder", message);
    }


      protected Map<String , Boolean> getRainyDays(Map<String , Double> precipMM){

           Map<String , Boolean> rainFlag =  new LinkedHashMap<>();

           for (String cal : precipMM.keySet()){
            if (precipMM.get(cal)> 5.0)
                rainFlag.put(cal ,true);
            else
                rainFlag.put(cal ,false);
         }
        return rainFlag;
    }


        }
