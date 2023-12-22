package com.shibam.swapicacheengine.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.VehicleService;
import com.shibam.swapicacheengine.model.Vehicle;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final SwapiCache swapiCache;

    @Autowired
    public VehicleController(VehicleService vehicleService, SwapiCache swapiCache) {
        this.vehicleService = vehicleService;
        this.swapiCache = swapiCache;
    }

    
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        String cacheKey = "all_vehicles";
        @SuppressWarnings("unchecked")
        List<Vehicle> vehicles = (List<Vehicle>) swapiCache.get(cacheKey);

        if (vehicles == null) {
            vehicles = vehicleService.getAllVehicles();
            swapiCache.put(cacheKey, vehicles);
        }

        return vehicles;
    }

    
    @GetMapping("/{name}")
    public Vehicle getVehicleByName(@PathVariable String name) {
        String cacheKey = "vehicle_" + name;
        Vehicle vehicle = (Vehicle) swapiCache.get(cacheKey);

        if (vehicle == null) {
            vehicle = vehicleService.getVehicleByName(name);
            swapiCache.put(cacheKey, vehicle);
        }

        return vehicle;
    }

    
    @GetMapping("/model/{model}")
    public List<Vehicle> getVehiclesByModel(@PathVariable String model) {
        return vehicleService.getVehiclesByModel(model);
    }

   
    @GetMapping("/manufacturer/{manufacturer}")
    public List<Vehicle> getVehiclesByManufacturer(@PathVariable String manufacturer) {
        return vehicleService.getVehiclesByManufacturer(manufacturer);
    }

    // You can add more endpoints based on other criteria as needed.
}
