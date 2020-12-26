package com.farmTracker.demo.Model;

import farmtracker.springbootmongodb.Converter;

import java.util.*;
import java.util.stream.Collectors;

import static com.farmTracker.demo.Controller.PlantTypeController.plantTypeService;

public class Plant {

    private final String plantName;
    private Map<String, Double> properTemp;
    private String plantingDate;
    private double elevation;
    private String longitude;
    private String latitude;
    private List<PlantType> suggestedPlants = new ArrayList<>();
    private  Map<String, Map<String, String>> reminderMessages = new HashMap<>();
    private Map<String, Map<String, String>> warningMessages = new HashMap<>();
    private Map<String, String> address;



    //   private Map<String, Double> properSoil;
    private double properHumidity;            //
    private Map<String, Integer> properIrrigation;
    private Map<String, Boolean> Irrigation = new LinkedHashMap<>();
    private Map<String, Boolean> farmCalendar = new LinkedHashMap<>();
    private String nextHarvestDate;
    private String properFertilization;
    private PlantType plantType;
    private int daysToHarvest;
    private double properTempDiff;


    public Plant(String plantName , String plantingDate, String latitude , String longitude) {

        this.plantName = plantName;

        if (!plantTypeService.getPlantTypeByName(plantName).equals(null)) {

            this.plantType = plantTypeService.getPlantTypeByName(plantName);
            this.properHumidity = plantType.getProper_humidity();

            this.properIrrigation = Map.of("max", plantType.getMax_irrigation(), "min", plantType.getMin_irrigation());
            this.properFertilization = plantType.getProper_fertilization();
            this.properTempDiff = plantType.getProper_tempchg();
            this.daysToHarvest= plantType.getDays_harvest();

            this.properTemp = Map.of("max" , plantType.getMax_temp() ,  "min" , plantType.getMin_temp());
        }


        this.plantingDate = plantingDate;
        this.latitude = latitude;
        this.longitude= longitude;

       // this.properSoil = plantType.getProperSoil();


    }

    public String getPlantName() {
        return plantType.getPlant_name();
    }


    public String getPlantingDate() {
        return plantingDate;
    }

    public Map<String, Double> getProperTemp() {
        return properTemp;
    }

    public void setProperTemp(Map<String, Double> properTemp) {
        this.properTemp = properTemp;
    }

    public double getProperTempDiff() {
        return properTempDiff;
    }

    public void setProperTempDiff(double properTempDiff) {
        this.properTempDiff = properTempDiff;
    }

    /* public Map<String, Double> getProperSoil() {
            return properSoil;
        }

        public void setProperSoil(Map<String, Double> properSoil) {
            this.properSoil = properSoil;
        }
        */
    public double getProperHumidity() {
        return properHumidity;
    }

    public Map<String, Integer> getProperIrrigation() {
        return properIrrigation;
    }

    public void setProperIrrigation(Map<String, Integer> properIrrigation) {
        this.properIrrigation = properIrrigation;
    }

    public Map<String, Boolean> getFarmCalendar() {

        return this.farmCalendar;        //return farm calendar
    }

    public List<String> getIrrigationDates() {

        //watered days
        return farmCalendar.keySet().stream()
                .filter(p -> farmCalendar.get(p).equals(true)).collect(Collectors.toList());
    }

    public void setIrrigationDate(List<String> irrigationDate) {

        irrigationDate.forEach(date -> farmCalendar.put(date,true));;
    }

    public void setFarmCalendarDefault(){

        Converter converter = new Converter();
        String flag = this.plantingDate;

        while (!flag.equals(converter.getDateToday())){

            farmCalendar.putIfAbsent(flag, false);
            flag = converter.addDays(flag,1 );

        }

    }

    public int getDaysToHarvest() {
        return daysToHarvest;
    }

    public void setDaysToHarvest(int daysToHarvest) {
        this.daysToHarvest = daysToHarvest;
    }


    public String getNextHarvestDate() {
        return nextHarvestDate;
    }

    public void setNextHarvestDate(String nextHarvestDate) {
        this.nextHarvestDate = new Converter().addDays(this.plantingDate, this.daysToHarvest);
    }

    public String getProperFertilization() {
        return properFertilization;
    }

    public void setProperFertilization(String properFertilization) {
        this.properFertilization = properFertilization;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }


    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<PlantType> getSuggestedPlants() {
        return suggestedPlants;
    }

    public void setSuggestedPlants(List<PlantType> suggestedPlants) {
        this.suggestedPlants = suggestedPlants;
    }

    public Map<String, Map<String, String>> getReminderMessages() {
        return reminderMessages;
    }

    public void setReminderMessages(Map<String, Map<String, String>> reminderMessages) {
        this.reminderMessages = reminderMessages;
    }

    public Map<String, Map<String, String>> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(Map<String, Map<String, String>> warningMessages) {
        this.warningMessages = warningMessages;
    }


    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }




}
