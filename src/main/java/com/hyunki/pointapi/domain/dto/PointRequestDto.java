package com.hyunki.pointapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PointRequestDto {
    private String username;
    private int pointAmt;
}
