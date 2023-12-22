package com.shibam.swapicacheengine.service;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Planet;
import java.util.List;

@Service
public class PlanetService {

    private final RestTemplate restTemplate;

    @Autowired
    public PlanetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Planet> getAllPlanets() {
        String url = "https://swapi.dev/api/planets/";
        PlanetWrapper response = restTemplate.getForObject(url, PlanetWrapper.class);
        
        return (response != null) ? response.getResults() : null;
    }

    public Planet getPlanetById(String id) {
        String url = "https://swapi.dev/api/planets/" + id + "/";
        return restTemplate.getForObject(url, Planet.class);
    }

    public Planet getPlanetByName(String name) {
        String url = "https://swapi.dev/api/planets/?search=" + name;
        PlanetWrapper response = restTemplate.getForObject(url, PlanetWrapper.class);
        
        return (response != null && !response.getResults().isEmpty()) ? response.getResults().get(0) : null;
    }

    public List<Planet> getPlanetsByClimate(String climate) {
        String url = "https://swapi.dev/api/planets/?climate=" + climate;
        PlanetWrapper response = restTemplate.getForObject(url, PlanetWrapper.class);
        
        return (response != null) ? response.getResults() : null;
    }

    private static class PlanetWrapper {
        private List<Planet> results;

        public List<Planet> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
		public void setResults(List<Planet> results) {
            this.results = results;
        }
    }
}
