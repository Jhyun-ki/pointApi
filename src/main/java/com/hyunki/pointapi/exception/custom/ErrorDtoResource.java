package com.hyunki.pointapi.exception.custom;

import com.hyunki.pointapi.controller.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorDtoResource extends EntityModel<ErrorDto> {
    public ErrorDtoResource(ErrorDto errorDto) {
        super(errorDto);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
