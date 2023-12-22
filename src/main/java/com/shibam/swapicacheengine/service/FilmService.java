package com.shibam.swapicacheengine.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shibam.swapicacheengine.model.Film;
import java.util.List;

@Service
public class FilmService {

    private final RestTemplate restTemplate;

    @Autowired
    public FilmService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Film> getAllFilms() {
        String url = "https://swapi.dev/api/films/";
        FilmWrapper response = restTemplate.getForObject(url, FilmWrapper.class);
        
        return (response != null) ? response.getResults() : null;
    }

    public Film getFilmByTitle(String title) {
        String url = "https://swapi.dev/api/films/?search=" + title;
        FilmWrapper response = restTemplate.getForObject(url, FilmWrapper.class);
        
        return (response != null && !response.getResults().isEmpty()) ? response.getResults().get(0) : null;
    }

    private static class FilmWrapper {
        private List<Film> results;

        public List<Film> getResults() {
            return results;
        }

        @SuppressWarnings("unused")
		public void setResults(List<Film> results) {
            this.results = results;
        }
    }
}
