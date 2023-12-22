package com.shibam.swapicacheengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Starship;

import java.util.List;

@Service
public class StarshipService {

    private final RestTemplate restTemplate;

    @Autowired
    public StarshipService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    
    public List<Starship> getAllStarships() {
        String url = "https://swapi.dev/api/starships/";
        StarshipWrapper starshipWrapper = restTemplate.getForObject(url, StarshipWrapper.class);
        return starshipWrapper != null ? starshipWrapper.getResults() : null;
    }

    
    public Starship getStarshipByName(String name) {
        String url = "https://swapi.dev/api/starships/?search=" + name;
        StarshipWrapper starshipWrapper = restTemplate.getForObject(url, StarshipWrapper.class);
        return starshipWrapper != null && !starshipWrapper.getResults().isEmpty() ? starshipWrapper.getResults().get(0) : null;
    }

   

    
    private static class StarshipWrapper {
        private List<Starship> results;

        public List<Starship> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
		public void setResults(List<Starship> results) {
            this.results = results;
        }
    }
}
