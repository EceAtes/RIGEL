package com.example.rigel_v1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class NullKeySerializer extends JsonSerializer<Map<?, ?>> {
    @Override
    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                jsonGenerator.writeFieldName(entry.getKey().toString());
                jsonGenerator.writeObject(entry.getValue());
            }
        }
        jsonGenerator.writeEndObject();
    }
}