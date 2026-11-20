package com.tavarlabs.tavarsystem.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class UpperCaseConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : attribute.toUpperCase(Locale.ROOT);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
