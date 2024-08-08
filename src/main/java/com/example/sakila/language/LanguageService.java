package com.example.sakila.language;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LanguageService {

    private static final String LANGUAGE_NOT_FOUND = "Language not found with id: ";

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<LanguageResponse> readAllLanguages() {
        return languageRepository.findAll()
                .stream()
                .map(LanguageResponse::new)
                .toList();
    }

    public LanguageResponse readLanguageById(Short id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(LANGUAGE_NOT_FOUND + id));
        return new LanguageResponse(language);
    }

    public LanguageResponse createLanguage(LanguageInput data) {
        Language language = new Language();
        language.setName(data.getName());
        return new LanguageResponse(languageRepository.save(language));
    }

    public LanguageResponse updateLanguage(Short id, LanguageInput updatedLanguageInput) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(LANGUAGE_NOT_FOUND + id));
        language.setName(updatedLanguageInput.getName());
        return new LanguageResponse(languageRepository.save(language));
    }

    public LanguageResponse patchLanguage(Short id, Map<String, Object> updates) {

        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(LANGUAGE_NOT_FOUND + id));

        updates.forEach((key, value) -> {
            if (key.equals("name")) {
                language.setName((String) value);
            }
            else {
                throw new IllegalArgumentException("Unknown attribute: " + key);
            }
        });

        return new LanguageResponse(languageRepository.save(language));
    }

    public void deleteLanguage(Short id) {
        languageRepository.deleteById(id);
    }




























}
