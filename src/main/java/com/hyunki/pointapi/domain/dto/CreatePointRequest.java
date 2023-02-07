package com.hyunki.pointapi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CreatePointRequest {
    private String username;
    private int pointAmt;
}
