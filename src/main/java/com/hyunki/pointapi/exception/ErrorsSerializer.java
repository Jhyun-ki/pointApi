package com.hyunki.pointapi.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;


@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {


    @Override
    public void serialize(Errors value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}
