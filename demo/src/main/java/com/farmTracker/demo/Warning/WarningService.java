package com.farmTracker.demo.Warning;

import com.farmTracker.demo.Model.Plant;
import com.farmTracker.demo.Service.Meteorology;

import java.util.*;
import java.util.stream.Collectors;

import static com.farmTracker.demo.Service.PlantService.plant;


public class WarningService {
    private String longitude;
    private String latitude;

    Meteorology meteorology;

    Map<String, Map<String, String>> warningMessages =new HashMap<>();      //plant , warning type and warning message
    Map<String, String> messages = new HashMap<>();

    public WarningService(String longitude, String latitude , String time) {

        this.longitude = longitude;
        this.latitude = latitude;

        this.meteorology = new Meteorology(longitude, latitude, time);

    }

        public Map<String, Map<String, String>> getWarningMessages() {

        Map<String, Map<String, Double>> maxMinTemp = meteorology.forecastMaxMinTemp();
        Map<String, Double> tempDiff = meteorology.forecastTempDif();

        Map<String, Double> precipMM = meteorology.getPrecip();
        Map<String, Double> forecastIfSnow = meteorology.forecastIfSnow();



            checkForMaxMinTemp(plant, maxMinTemp);
            checkForIrrigation(plant, precipMM);
            checkForTempChange(plant, tempDiff);
            checkForSnow(plant, forecastIfSnow);

            warningMessages.put(plant.getPlantType().getPlant_name(), messages);

        return warningMessages;
    }
        protected void checkForIrrigation(Plant plant, Map<String, Double> precipMM){

        System.out.println("warning service --> check for irrigarion");

         Map<String, Boolean> farmCalendar = plant.getFarmCalendar();  //get farm calendar which includes your irrigation information
         Map<String, Boolean> rainFlag = new LinkedHashMap<>();  //initialize the rain flag

        for (String cal : precipMM.keySet()) {
            if (precipMM.get(cal) > 5.0)
                rainFlag.put(cal, true);        // set rain flags
            else
                rainFlag.put(cal, false);
        }

            List<String> ReversedKeySet = farmCalendar.keySet().stream()
                    .sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
            //Reversed because we wanted values from today to past


             int counter = 0; //counter for counting days without watering

             String message = "no warning"; //default message

                for (String cal : ReversedKeySet) {
                    if (farmCalendar.get(cal) != null && rainFlag.get(cal) != null) {

                        if (farmCalendar.get(cal) || rainFlag.get(cal)) {      //if we watered or rained, break
                            break;
                        } else {
                            counter++;
                        }

                        if (counter > plant.getProperIrrigation().get("max")) {        //if counter reaches max days without watering for the plant
                            message = counter + " days without watering for plant " + plant.getPlantType().getPlant_name();
                            break;
                        }
                    }
                    messages.put("irrigation_warn", message);
                }
    }
        protected void checkForTempChange(Plant plant, Map<String, Double> tempDiff){

        System.out.println("warning service --> check for tempreture change");

        List<String> cal1 = new ArrayList<>();

        for(String cal : tempDiff.keySet()) {
            if (plant.getProperTempDiff() < tempDiff.get(cal))
                cal1.add(cal);
        }

            cal1.forEach(System.out::println);
            System.out.println(cal1.isEmpty());

            String message = cal1.isEmpty() ? "no warnings" : "Expecting extreme temp change on dates " + cal1 + ". It can be dangerous for " + plant.getPlantType().getPlant_name();

            messages.put("temp_change_warn", message);

    }
        protected void checkForMaxMinTemp(Plant plant, Map<String, Map<String, Double>> maxMinTemp) {

            System.out.println("warning service --> check for tempreture change");

            List<String> overHeat = new ArrayList<>();
            List<String> lowHeat = new ArrayList<>();

            for (String cal : maxMinTemp.keySet()) {
                if (plant.getProperTemp().get("max") < maxMinTemp.get(cal).get("max"))      //if max temp of our plant is more than temp of location's
                    overHeat.add(cal);
                else

                if(plant.getProperTemp().get("min") > maxMinTemp.get(cal).get("min"))
                    lowHeat.add(cal);
            }

            String message = overHeat.isEmpty() ?  "no warning" : "Expecting high temp on " + overHeat + ". It can be dangerous for " + plant.getPlantType().getPlant_name();
            String message2 = lowHeat.isEmpty() ?  "no warning" : "Expecting low temp on " + lowHeat + ". It can be dangerous for " + plant.getPlantType().getPlant_name();


            messages.put("over_heat_warn", message);

            messages.put("low_temp_warn", message2);

        }
        protected void checkForSnow(Plant plant, Map<String, Double> forecastIfSnow) {
            System.out.println("warning service --> checkForSnow");

            List<String> cal1 = new ArrayList<>();

            for (String cal : forecastIfSnow.keySet()) {
                if (forecastIfSnow.get(cal) > 80.0)      //possiblity of snow
                    cal1.add(cal);
            }

            cal1.forEach(System.out::println);

            String message = cal1.isEmpty() ? "no warning" : "Expecting snow on " + cal1 + " it can be dangerous for " + plant.getPlantType().getPlant_name();

            messages.put("snow_warn", message);
        }
}