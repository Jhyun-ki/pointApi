package com.hyunki.pointapi.exception.validator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;


@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName("errors");
        gen.writeStartArray();
        errors.getFieldErrors().stream().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeNumberField("code", -1);
                gen.writeStringField("msg", e.getDefaultMessage());
                gen.writeStringField("errorCode", e.getCode());
                gen.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeNumberField("code", -1);
                gen.writeStringField("msg", e.getDefaultMessage());
                gen.writeStringField("errorCode", e.getCode());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        gen.writeEndArray();
    }
}
