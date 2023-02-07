package com.hyunki.pointapi.exception.validator;

import com.hyunki.pointapi.controller.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorValidateResource extends EntityModel<Errors> {
    public ErrorValidateResource(Errors errors) {
        super(errors);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
