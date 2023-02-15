package com.hyunki.pointapi.controller;

import com.hyunki.pointapi.domain.dto.*;
import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.exception.validator.ErrorValidateResource;
import com.hyunki.pointapi.repository.PointRepository;
import com.hyunki.pointapi.service.PointService;
import com.hyunki.pointapi.exception.validator.CreatePointValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.Link.of;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/point", produces = MediaTypes.HAL_JSON_VALUE)
public class PointApiController {
    private final PointService pointService;
    private final CreatePointValidator createPointValidator;
    private final PointRepository pointRepository;

    @PostMapping
    public ResponseEntity point(@RequestBody @Valid CreatePointRequest createPointRequest,
                                Errors errors) {

        createPointValidator.validate(createPointRequest, errors);
        if(errors.hasErrors()) {
            return badRequest(errors);
        }

        Point createdPoint = pointService.createPoint(createPointRequest);
        CreatePointResponse createPointResponse = new CreatePointResponse(createdPoint);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PointApiController.class).slash(createPointResponse.getPointId());
        URI createURI = selfLinkBuilder.toUri();
        PointResource pointResource = new PointResource(createPointResponse);
        pointResource.add(linkTo(PointApiController.class).withRel("query-events"));
        pointResource.add(selfLinkBuilder.withRel("update-event"));
        pointResource.add(of("/docs/index.html#resources-point-create", "profile"));

        return ResponseEntity.created(createURI).body(pointResource);
    }

    @GetMapping
    public ResponseEntity queryPoint(PointSearchCondition pointSearchCondition,
                                     Pageable pageable) {

        return ResponseEntity.ok().body(pointRepository.search(pointSearchCondition, pageable));
    }

    private static ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorValidateResource(errors));
    }
}
