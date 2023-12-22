package com.shibam.swapicacheengine.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Vehicle;

import java.util.List;

@Service
public class VehicleService {

    private final RestTemplate restTemplate;

    @Autowired
    public VehicleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    
    public List<Vehicle> getAllVehicles() {
        String url = "https://swapi.dev/api/vehicles/";
        VehicleWrapper vehicleWrapper = restTemplate.getForObject(url, VehicleWrapper.class);

        
        if (vehicleWrapper != null) {
            return vehicleWrapper.getResults();
        } else {
            
            return null;
        }
    }

   
    public Vehicle getVehicleByName(String name) {
        String url = "https://swapi.dev/api/vehicles/?search=" + name;
        VehicleWrapper vehicleWrapper = restTemplate.getForObject(url, VehicleWrapper.class);

        
        if (vehicleWrapper != null && !vehicleWrapper.getResults().isEmpty()) {
            return vehicleWrapper.getResults().get(0);
        } else {
            
            return null;
        }
    }

    
    public List<Vehicle> getVehiclesByModel(String model) {
        String url = "https://swapi.dev/api/vehicles/?model=" + model;
        VehicleWrapper vehicleWrapper = restTemplate.getForObject(url, VehicleWrapper.class);

        if (vehicleWrapper != null) {
            return vehicleWrapper.getResults();
        } else {
            
            return null;
        }
    }

    
    public List<Vehicle> getVehiclesByManufacturer(String manufacturer) {
        String url = "https://swapi.dev/api/vehicles/?manufacturer=" + manufacturer;
        VehicleWrapper vehicleWrapper = restTemplate.getForObject(url, VehicleWrapper.class);

        if (vehicleWrapper != null) {
            return vehicleWrapper.getResults();
        } else {
            
            return null;
        }
    }

    
    private static class VehicleWrapper {
        private List<Vehicle> results;

        public List<Vehicle> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
        public void setResults(List<Vehicle> results) {
            this.results = results;
        }
    }
}
