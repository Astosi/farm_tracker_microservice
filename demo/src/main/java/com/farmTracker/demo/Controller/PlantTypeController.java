package com.farmTracker.demo.Controller;

import com.farmTracker.demo.Model.PlantType;

import com.farmTracker.demo.Service.PlantTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/plant_type")
@RestController
public class PlantTypeController {

    public static PlantTypeService plantTypeService;

    @Autowired
    public PlantTypeController(PlantTypeService plantTypeService) {
        this.plantTypeService = plantTypeService;
    }


    @GetMapping("/all")
    public List<PlantType> getAllPlantTypes() {
        return plantTypeService.getAllPlantTypes();
    }

    @GetMapping(path = "/find_by_id/{id}")
    public PlantType getPlantTypeById(@PathVariable String id) {
        return plantTypeService.getPlantTypeById(id);
    }

    @GetMapping(path = "/{name}")
    public PlantType getPlantTypeByName(@PathVariable String name) {
        return plantTypeService.getPlantTypeByName(name);
    }


    @GetMapping(path = "/{plantName}/proper_temp")
    public Map<String,Double> getProperTemp(@PathVariable String plantName) {
        return plantTypeService.getProperTemp(plantName);
    }
    @GetMapping(path = "/{plantName}/days_to_harvest")
    public int getDaysToHarvest(@PathVariable String plantName) {
        return plantTypeService.getDaysToHarvest(plantName);
    }
//  public Map<String, Double> getProperSoil() {
    //       return properSoil;
    // }
    @GetMapping(path = "/{plantName}/prop_hum")
    public double getProperHumidity(@PathVariable String plantName) {
        return plantTypeService.getProperHumidity(plantName);
    }
    @GetMapping(path = "/{plantName}/prop_irr")
    public Map<String, Integer> getProperIrrigation(@PathVariable String plantName) {
        return plantTypeService.getProperIrrigation(plantName);
    }

    @GetMapping(path = "/{plantName}/prop_percip")
    public double getProperPrecip(@PathVariable String plantName) {
        return plantTypeService.getProperPrecip(plantName);
    }
    @GetMapping(path = "/{plantName}/prop_ferz")
    public String getProperFert(@PathVariable String plantName) {
        return plantTypeService.getProperFert(plantName);
    }
    @GetMapping(path = "/{plantName}/prop_temp_diff")
    public double getProperTempDiff(@PathVariable String plantName) {
        return plantTypeService.getProperTempDiff(plantName);
    }

}
