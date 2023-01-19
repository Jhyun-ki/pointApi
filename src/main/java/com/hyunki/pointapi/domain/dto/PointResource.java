package com.hyunki.pointapi.domain.dto;

import com.hyunki.pointapi.controller.PointApiController;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PointResource extends EntityModel<CreatePointResponse> {
    public PointResource(CreatePointResponse createPointResponse) {
        super(createPointResponse);
        add(linkTo(PointApiController.class).slash(createPointResponse.getPointId()).withSelfRel());
    }
}
