package com.farmTracker.demo.Service;

import com.farmTracker.demo.Model.PlantType;
import com.farmTracker.demo.Repo.PlantTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlantTypeService {

    private final PlantTypeRepo plantTypeDeo;

    @Autowired
    public PlantTypeService(PlantTypeRepo plantTypeDeo){
    this.plantTypeDeo = plantTypeDeo;
    }

    public List<PlantType> getAllPlantTypes(){
        return plantTypeDeo.findAll();
    }

    public PlantType getPlantTypeById(String id){
        System.out.println("----->" +   id);
        return plantTypeDeo.findById(id).orElse(null);
    }


    public PlantType getPlantTypeByName(String plantName) {

       return plantTypeDeo.findAll().stream()
                .filter(plantType -> plantType.getPlant_name().equals(plantName))
                .collect(Collectors.toList()).get(0);
    }


    public Map<String,Double> getProperTemp(String plantName) {
       PlantType plant = getPlantTypeByName(plantName);
        return Map.of("max ", plant.getMax_temp(),"min ",plant.getMin_temp());
    }

//  public Map<String, Double> getProperSoil() {
    //       return properSoil;
    // }

    public double getProperHumidity(String plantName) {
        return getPlantTypeByName(plantName).getProper_humidity();
    }

    public Map<String, Integer> getProperIrrigation(String plantName) {
        PlantType plant = getPlantTypeByName(plantName);
        return Map.of("max ", plant.getMax_irrigation(),"min ",plant.getMin_irrigation());

    }

    public double getProperPrecip(String plantName) {
        return getPlantTypeByName(plantName).getProper_precip();
    }

    public String getProperFert(String plantName) {
        return getPlantTypeByName(plantName).getProper_fertilization();
    }

    public double getProperTempDiff(String plantName) {
        return getPlantTypeByName(plantName).getProper_tempchg();
    }

    public int getDaysToHarvest(String plantName) {
        return getPlantTypeByName(plantName).getDays_harvest();
    }



}