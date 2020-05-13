package com.gmail.bishoybasily.quarkus.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gmail.bishoybasily.quarkus.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.Set;

public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> value) {
        if (value == null)
            return null;
        return JsonUtils.json(value);
    }

    @Override
    public Set<String> convertToEntityAttribute(String value) {
        if (value == null)
            return null;
        return JsonUtils.json(value, new TypeReference<Set<String>>() {
        });
    }

}
