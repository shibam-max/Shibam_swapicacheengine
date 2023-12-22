package com.shibam.swapicacheengine.controller;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.FilmService;
import com.shibam.swapicacheengine.model.Film;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;
    private final SwapiCache swapiCache;

    @Autowired
    public FilmController(FilmService filmService, SwapiCache swapiCache) {
        this.filmService = filmService;
        this.swapiCache = swapiCache;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        String cacheKey = "all_films";
        @SuppressWarnings("unchecked")
		List<Film> films = (List<Film>) swapiCache.get(cacheKey);
        
        if (films == null) {
            films = filmService.getAllFilms();
            swapiCache.put(cacheKey, films);
        }
        
        return films;
    }

    @GetMapping("/{title}")
    public Film getFilmByTitle(@PathVariable String title) {
        String cacheKey = "film_" + title;
        Film film = (Film) swapiCache.get(cacheKey);
        
        if (film == null) {
            film = filmService.getFilmByTitle(title);
            swapiCache.put(cacheKey, film);
        }
        
        return film;
    }
}
