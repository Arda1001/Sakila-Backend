package com.example.sakila.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable Short id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
    }

    @PostMapping
    public Language createLanguage(@RequestBody LanguageInput languageInput) {
        Language language = new Language();
        language.setName(languageInput.getName());
        return languageRepository.save(language);
    }

    @PutMapping("/{id}")
    public Language updateLanguage(@PathVariable Short id, @RequestBody LanguageInput updatedLanguageInput) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
        language.setName(updatedLanguageInput.getName());
        return languageRepository.save(language);
    }

    @PatchMapping("/{id}")
    public Language patchLanguage(@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));

        updates.forEach((key, value) -> {
            if (key.equals("name")) {
                language.setName((String) value);
            }
            else {
                throw new IllegalArgumentException("Unknown attribute: " + key);
            }
        });

        return languageRepository.save(language);
    }

    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable Short id) {
        languageRepository.deleteById(id);
    }
}

