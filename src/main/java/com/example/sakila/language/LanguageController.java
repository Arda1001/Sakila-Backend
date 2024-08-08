package com.example.sakila.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LanguageResponse> readAllLanguages() {
        return languageService.readAllLanguages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LanguageResponse readLanguageById(@PathVariable Short id) {
        return languageService.readLanguageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageResponse createLanguage(@RequestBody LanguageInput data) {
        return languageService.createLanguage(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LanguageResponse updateLanguage(@PathVariable Short id, @RequestBody LanguageInput updatedLanguageInput) {
        return languageService.updateLanguage(id, updatedLanguageInput);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LanguageResponse patchLanguage(@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        return languageService.patchLanguage(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLanguage(@PathVariable Short id) {
        languageService.deleteLanguage(id);
    }
}

