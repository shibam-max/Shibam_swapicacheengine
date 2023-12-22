package com.shibam.swapicacheengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.PlanetService;
import com.shibam.swapicacheengine.model.Planet;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService planetService;
    private final SwapiCache swapiCache;

    @Autowired
    public PlanetController(PlanetService planetService, SwapiCache swapiCache) {
        this.planetService = planetService;
        this.swapiCache = swapiCache;
    }

    @GetMapping
    public List<Planet> getAllPlanets() {
        String cacheKey = "all_planets";
        @SuppressWarnings("unchecked")
        List<Planet> planets = (List<Planet>) swapiCache.get(cacheKey);
        
        if (planets == null) {
            planets = planetService.getAllPlanets();
            swapiCache.put(cacheKey, planets);
        }
        
        return planets;
    }

    @GetMapping("/{id}")
    public Planet getPlanetById(@PathVariable String id) {
        String cacheKey = "planet_" + id;
        Planet planet = (Planet) swapiCache.get(cacheKey);
        
        if (planet == null) {
            planet = planetService.getPlanetById(id);
            swapiCache.put(cacheKey, planet);
        }
        
        return planet;
    }

    @GetMapping("/name/{name}")
    public Planet getPlanetByName(@PathVariable String name) {
        String cacheKey = "planet_name_" + name;
        Planet planet = (Planet) swapiCache.get(cacheKey);
        
        if (planet == null) {
            planet = planetService.getPlanetByName(name);
            swapiCache.put(cacheKey, planet);
        }
        
        return planet;
    }

    @GetMapping("/climate/{climate}")
    public List<Planet> getPlanetsByClimate(@PathVariable String climate) {
        return planetService.getPlanetsByClimate(climate);
    }
}
