package com.shibam.swapicacheengine.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Character;

import java.util.List;


@Service
public class CharacterService {

    private final RestTemplate restTemplate;

    @Autowired
    public CharacterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Character> getAllCharacters() {
        String url = "https://swapi.dev/api/people/";
        CharacterWrapper response = restTemplate.getForObject(url, CharacterWrapper.class);
        
        return (response != null) ? response.getResults() : null;
    }

    public Character getCharacterByName(String name) {
        String url = "https://swapi.dev/api/people/?search=" + name;
        CharacterWrapper response = restTemplate.getForObject(url, CharacterWrapper.class);
        
        return (response != null && !response.getResults().isEmpty()) ? response.getResults().get(0) : null;
    }

   

    private static class CharacterWrapper {
        private List<Character> results;

        public List<Character> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
		public void setResults(List<Character> results) {
            this.results = results;
        }
    }
}
