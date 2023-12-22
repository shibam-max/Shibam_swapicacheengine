package com.shibam.swapicacheengine.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.StarshipService;
import com.shibam.swapicacheengine.model.Starship;

import java.util.List;

@RestController
@RequestMapping("/api/starships")
public class StarshipController {

    private final StarshipService starshipService;
    private final SwapiCache swapiCache;

    @Autowired
    public StarshipController(StarshipService starshipService, SwapiCache swapiCache) {
        this.starshipService = starshipService;
        this.swapiCache = swapiCache;
    }

    
    @GetMapping
    public List<Starship> getAllStarships() {
        String cacheKey = "all_starships";
        @SuppressWarnings("unchecked")
		List<Starship> starships = (List<Starship>) swapiCache.get(cacheKey);
        
        if (starships == null) {
            starships = starshipService.getAllStarships();
            swapiCache.put(cacheKey, starships);
        }
        
        return starships;
    }

   
    @GetMapping("/{name}")
    public Starship getStarshipByName(@PathVariable String name) {
        String cacheKey = "starship_" + name;
        Starship starship = (Starship) swapiCache.get(cacheKey);
        
        if (starship == null) {
            starship = starshipService.getStarshipByName(name);
            swapiCache.put(cacheKey, starship);
        }
        
        return starship;
    }

   
   
}
