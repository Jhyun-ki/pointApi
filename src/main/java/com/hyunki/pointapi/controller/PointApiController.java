package com.hyunki.pointapi.controller;

import com.hyunki.pointapi.domain.dto.*;
import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.exception.custom.ErrorDto;
import com.hyunki.pointapi.repository.PointRepository;
import com.hyunki.pointapi.service.PointService;
import com.hyunki.pointapi.exception.validator.CreatePointValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/point")
public class PointApiController {
    private final PointService pointService;
    private final CreatePointValidator createPointValidator;
    private final PointRepository pointRepository;

    @PostMapping
    public ResponseEntity point(@RequestBody @Valid PointRequestDto pointRequestDto,
                                Errors errors) {

        createPointValidator.validate(pointRequestDto, errors);
        if(errors.hasErrors()) {
            return badRequest(errors);
        }

        Point createdPoint = pointService.createPoint(pointRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PointResponseDto(createdPoint));
    }

    @GetMapping
    public ResponseEntity queryPoint(PointSearchCondition pointSearchCondition, Pageable pageable) {

        return ResponseEntity.ok().body(pointRepository.search(pointSearchCondition, pageable));
    }

    private static ResponseEntity badRequest(Errors errors) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(-1)
                .errorCode(errors.getFieldError().getCode())
                .message(errors.getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.badRequest().body(errorDto);
    }
}
