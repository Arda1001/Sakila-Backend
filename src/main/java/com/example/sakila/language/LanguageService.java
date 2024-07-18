package com.example.sakila.language;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> readAllLanguages() {
        return languageRepository.findAll();
    }

    public Language readlanguageById(Short id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
    }

    public Language createLanguage(LanguageInput data) {
        Language language = new Language();
        language.setName(data.getName());
        return languageRepository.save(language);
    }

    public Language updateLanguage(Short id, LanguageInput updatedLanguageInput) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
        language.setName(updatedLanguageInput.getName());
        return languageRepository.save(language);
    }

    public Language patchLanguage(Short id, Map<String, Object> updates) {

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

    public void deleteLanguage(Short id) {
        languageRepository.deleteById(id);
    }




























}
