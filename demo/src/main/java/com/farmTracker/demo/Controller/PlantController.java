package com.farmTracker.demo.Controller;

import com.farmTracker.demo.Model.Plant;
import com.farmTracker.demo.Model.PlantType;
import com.farmTracker.demo.Service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class PlantController {

    //TODO:adjust sensitivity

    public PlantService service;

    @Autowired
    public PlantController(PlantService service){
    this.service = service;
    }


    @PostMapping
    public void addPlant(@RequestBody Plant plant){
        service.addPlant(plant);
    }


    @GetMapping("/get_address")
    public Map<String, String> getFarmAddress(){
        return service.getFarmAddress();
    }

    @PutMapping(path = "/set_IfWatered")
    public void addIrrigationDate(@RequestBody List<String> irrigationDate){
        service.addIrrigationDate(irrigationDate);
    }

    @GetMapping(path = "/next_harvest")
    public String getNextHarvestDate(){
        return service.getNextHarvestDate();
    }

    @GetMapping(path = "/wateredDays")
    public List<String> getWateredDays(){
        return service.getWateredDays();
    }

    @GetMapping(path = "/sug_plants")
    public List<PlantType> getSuggestedPlants(){
        return service.getSuggestedPlants();
    }

    @GetMapping(path = "/reminders")
    public  Map<String, Map<String, String>> getReminderMessage(){
        return service.getReminderMessage();
    }

    @GetMapping(path = "/warnings")
    public Map<String, Map<String, String>> getWarningMessage(){
        return service.getWarningMessage();
    }

    @GetMapping(path = "/sunrise_set")
    public Map<String, String> getSunriseSunset(){
        return service.getSunriseSunset();
    }

}
