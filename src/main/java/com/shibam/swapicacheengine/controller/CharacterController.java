package com.shibam.swapicacheengine.controller;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shibam.swapicacheengine.cache.SwapiCache;
import com.shibam.swapicacheengine.service.CharacterService;
import com.shibam.swapicacheengine.model.Character;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final SwapiCache swapiCache;

    @Autowired
    public CharacterController(CharacterService characterService, SwapiCache swapiCache) {
        this.characterService = characterService;
        this.swapiCache = swapiCache;
    }

    @GetMapping
    public List<Character> getAllCharacters() {
        String cacheKey = "all_characters";
        @SuppressWarnings("unchecked")
        List<Character> characters = (List<Character>) swapiCache.get(cacheKey);
        
        if (characters == null) {
            characters = characterService.getAllCharacters();
            swapiCache.put(cacheKey, characters);
        }
        
        return characters;
    }

    @GetMapping("/{name}")
    public Character getCharacterByName(@PathVariable String name) {
        String cacheKey = "character_" + name;
        Character character = (Character) swapiCache.get(cacheKey);
        
        if (character == null) {
            character = characterService.getCharacterByName(name);
            swapiCache.put(cacheKey, character);
        }
        
        return character;
    }

    
}

