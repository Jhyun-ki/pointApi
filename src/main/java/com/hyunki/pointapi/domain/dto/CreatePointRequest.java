package com.hyunki.pointapi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CreatePointRequest {
    @NotEmpty
    private String username;

    @Min(100)
    private int pointAmt;
}
