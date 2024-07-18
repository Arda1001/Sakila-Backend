package com.example.sakila.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Language> readAllLanguages() {
        return languageService.readAllLanguages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Language readLanguageById(@PathVariable Short id) {
        return languageService.readlanguageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language createLanguage(@RequestBody LanguageInput data) {
        return languageService.createLanguage(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Language updateLanguage(@PathVariable Short id, @RequestBody LanguageInput updatedLanguageInput) {
        return languageService.updateLanguage(id, updatedLanguageInput);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Language patchLanguage(@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        return languageService.patchLanguage(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLanguage(@PathVariable Short id) {
        languageService.deleteLanguage(id);
    }
}

