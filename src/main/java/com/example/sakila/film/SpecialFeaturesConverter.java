package com.example.sakila.film;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SpecialFeaturesConverter implements AttributeConverter<Set<SpecialFeatures>, String> {

    @Override
    public String convertToDatabaseColumn(Set<SpecialFeatures> specialFeatures) {
        if (specialFeatures == null || specialFeatures.isEmpty()) {
            return "";
        }
        return specialFeatures.stream()
                .map(this::convertToDisplayName)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<SpecialFeatures> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(this::convertToSpecialFeature)
                .collect(Collectors.toSet());
    }

    private String convertToDisplayName(SpecialFeatures specialFeature) {
        return switch (specialFeature) {
            case TRAILERS -> "Trailers";
            case COMMENTARIES -> "Commentaries";
            case DELETED_SCENES -> "Deleted Scenes";
            case BEHIND_THE_SCENES -> "Behind the Scenes";
        };
    }

    private SpecialFeatures convertToSpecialFeature(String displayName) {
        return switch (displayName) {
            case "Trailers" -> SpecialFeatures.TRAILERS;
            case "Commentaries" -> SpecialFeatures.COMMENTARIES;
            case "Deleted Scenes" -> SpecialFeatures.DELETED_SCENES;
            case "Behind the Scenes" -> SpecialFeatures.BEHIND_THE_SCENES;
            default -> throw new IllegalArgumentException("Unknown special feature: " + displayName);
        };
    }
}


