package com.hyunki.pointapi.domain.dto;

import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PointResponseDto {
    private String username;
    private Long id;
    private int pointAmt;
    private int remainPointAmt;
    private PointType pointType;
    private PointStatus pointStatus;

    private LocalDate issueDate;


    public PointResponseDto(Point point) {
        this.username = point.getAccount().getUsername();
        this.id = point.getId();
        this.pointAmt = point.getPointAmt();
        this.remainPointAmt = point.getRemainPointAmt();
        this.pointType = point.getPointType();
        this.pointStatus = point.getPointStatus();
        this.issueDate = point.getIssueDate();
    }

    @QueryProjection
    public PointResponseDto(Long id, String username, int pointAmt, int remainPointAmt, PointType pointType, PointStatus pointStatus, LocalDate issueDate) {
        this.id = id;
        this.username = username;
        this.pointAmt = pointAmt;
        this.remainPointAmt = remainPointAmt;
        this.pointType = pointType;
        this.pointStatus = pointStatus;
        this.issueDate = issueDate;
    }
}
