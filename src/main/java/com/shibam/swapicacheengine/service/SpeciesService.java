package com.shibam.swapicacheengine.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Species;

import java.util.List;

@Service
public class SpeciesService {

    private final RestTemplate restTemplate;

    @Autowired
    public SpeciesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

  
    public List<Species> getAllSpecies() {
        String url = "https://swapi.dev/api/species/";
        SpeciesWrapper speciesWrapper = restTemplate.getForObject(url, SpeciesWrapper.class);
        
        if (speciesWrapper != null) {
            return speciesWrapper.getResults();
        } else {
            return null;
        }
    }

    
    public Species getSpeciesByName(String name) {
        String url = "https://swapi.dev/api/species/?search=" + name;
        SpeciesWrapper speciesWrapper = restTemplate.getForObject(url, SpeciesWrapper.class);
        
        if (speciesWrapper != null && !speciesWrapper.getResults().isEmpty()) {
            return speciesWrapper.getResults().get(0);  // Assuming the first result is the desired species
        } else {
            return null;
        }
    }

   
    public List<Species> getSpeciesByLifespan(String lifespan) {
        String url = "https://swapi.dev/api/species/?lifespan=" + lifespan;
        SpeciesWrapper speciesWrapper = restTemplate.getForObject(url, SpeciesWrapper.class);
        
        if (speciesWrapper != null) {
            return speciesWrapper.getResults();
        } else {
            return null;
        }
    }

    
    private static class SpeciesWrapper {
        private List<Species> results;

        public List<Species> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
		public void setResults(List<Species> results) {
            this.results = results;
        }
    }
}
