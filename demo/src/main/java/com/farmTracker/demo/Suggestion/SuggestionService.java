package com.farmTracker.demo.Suggestion;

import com.farmTracker.demo.Model.PlantType;
import com.farmTracker.demo.Service.Meteorology;

import java.util.List;
import java.util.stream.Collectors;

import static com.farmTracker.demo.Controller.PlantTypeController.plantTypeService;

public class SuggestionService  {

    Meteorology meteorology;

    public List<PlantType> getSuggestedPlants(String longitude, String latitude , String time){

        this.meteorology = new Meteorology(longitude, latitude ,time);
        //   this.allPlantTypes = plantTypeController.getAllPlantTypes();
        //List<PlantType> allPlantTypes = plantTypes;
        // List<PlantType> allPlantTypes = plantTypeService.getAllPlantTypes();

        double avarageHumidity = meteorology.getAvarageHumidity();

        List<PlantType> suggestedPlats = plantTypeService.getAllPlantTypes().stream().filter(plantType -> (plantType.getProper_humidity()*50)/100 < avarageHumidity)
                .filter(plantType -> (plantType.getProper_humidity()*150)/100 > avarageHumidity)
                //  .filter(plantType -> (plantType.getProperSoil().get("soilMosture")*60)/100 < meteorology.getSoilMoisture())
                //   .filter(plantType -> (plantType.getProperSoil().get("soilTemp")*60)/100 < meteorology.getSoilTemp())
                .filter(plantType -> (plantType.getProper_precip()*60)/100 < meteorology.getAvPrecip())
                .filter(plantType -> plantType.getMax_temp()> meteorology.getMaxMinTemp().get(0))
                .filter(plantType -> plantType.getMin_temp() < meteorology.getMaxMinTemp().get(1))
                .filter(plantType -> plantType.getProper_tempchg() > meteorology.getAvMaxTempChange())
                .collect(Collectors.toList());


        return suggestedPlats;

    }

}

