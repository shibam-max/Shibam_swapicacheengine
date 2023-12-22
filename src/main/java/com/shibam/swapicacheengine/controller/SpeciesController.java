package com.shibam.swapicacheengine.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.SpeciesService;
import com.shibam.swapicacheengine.model.Species;

import java.util.List;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final SpeciesService speciesService;
    private final SwapiCache swapiCache;

    @Autowired
    public SpeciesController(SpeciesService speciesService, SwapiCache swapiCache) {
        this.speciesService = speciesService;
        this.swapiCache = swapiCache;
    }

    
    @GetMapping
    public List<Species> getAllSpecies() {
        String cacheKey = "all_species";
        @SuppressWarnings("unchecked")
		List<Species> speciesList = (List<Species>) swapiCache.get(cacheKey);
        
        if (speciesList == null) {
            speciesList = speciesService.getAllSpecies();
            swapiCache.put(cacheKey, speciesList);
        }
        
        return speciesList;
    }

    
    @GetMapping("/{name}")
    public Species getSpeciesByName(@PathVariable String name) {
        String cacheKey = "species_" + name;
        Species species = (Species) swapiCache.get(cacheKey);
        
        if (species == null) {
            species = speciesService.getSpeciesByName(name);
            swapiCache.put(cacheKey, species);
        }
        
        return species;
    }

   
    @GetMapping("/lifespan/{lifespan}")
    public List<Species> getSpeciesByLifespan(@PathVariable String lifespan) {
        String cacheKey = "species_lifespan_" + lifespan;
        @SuppressWarnings("unchecked")
		List<Species> speciesList = (List<Species>) swapiCache.get(cacheKey);
        
        if (speciesList == null) {
            speciesList = speciesService.getSpeciesByLifespan(lifespan);
            swapiCache.put(cacheKey, speciesList);
        }
        
        return speciesList;
    }
}
