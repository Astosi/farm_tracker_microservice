package com.farmTracker.demo.Model;

import javax.persistence.*;

@Entity
@Table(name = "plant_types")
public class PlantType {

        @Id
        private String PTUID;
        private String plant_name;
        private String proper_fertilization;
        private double proper_precip;
        private double proper_tempchg;
        private double proper_humidity;
        private double min_temp;
        private double max_temp;
        private int min_irrigation;
        private int max_irrigation;
        private int days_harvest;
        // private final Map<String, Double> properSoil;


    public void setPTUID(String PTUID) {
        this.PTUID = PTUID;
    }

    public void setPlant_name(String plantName) {
        this.plant_name = plantName;
    }

    public void setProper_fertilization(String properFertilization) {
        this.proper_fertilization = properFertilization;
    }

    public void setProper_precip(double properPrecip) {
        this.proper_precip = properPrecip;
    }

    public void setProper_tempchg(double properTempDiff) {
        this.proper_tempchg = properTempDiff;
    }

    public void setProper_humidity(double properHumidity) {
        this.proper_humidity = properHumidity;
    }

    public void setMin_temp(double minTemp) {
        this.min_temp = minTemp;
    }

    public void setMax_temp(double maxTemp) {
        this.max_temp = maxTemp;
    }

    public void setMin_irrigation(int minIrrigation) {
        this.min_irrigation = minIrrigation;
    }

    public void setMax_irrigation(int maxIrrigation) {
        this.max_irrigation = maxIrrigation;
    }


    public String getPTUID() {
        return PTUID;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public String getProper_fertilization() {
        return proper_fertilization;
    }

    public double getProper_precip() {
        return proper_precip;
    }

    public double getProper_tempchg() {
        return proper_tempchg;
    }

    public double getProper_humidity() {
        return proper_humidity;
    }

    public double getMin_temp() {
        return min_temp;
    }

    public double getMax_temp() {
        return max_temp;
    }

    public int getMin_irrigation() {
        return min_irrigation;
    }

    public int getMax_irrigation() {
        return max_irrigation;
    }

    public int getDays_harvest() {
        return days_harvest;
    }
}


