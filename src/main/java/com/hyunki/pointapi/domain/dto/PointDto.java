package com.hyunki.pointapi.domain.dto;

import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PointDto {
    private String username;
    private Long id;
    private int pointAmt;
    private int remainPointAmt;
    private PointType pointType;
    private PointStatus pointStatus;

    private LocalDate issueDate;

    @QueryProjection
    public PointDto(Long id, String username, int pointAmt, int remainPointAmt, PointType pointType, PointStatus pointStatus, LocalDate issueDate) {
        this.id = id;
        this.username = username;
        this.pointAmt = pointAmt;
        this.remainPointAmt = remainPointAmt;
        this.pointType = pointType;
        this.pointStatus = pointStatus;
        this.issueDate = issueDate;
    }
}
