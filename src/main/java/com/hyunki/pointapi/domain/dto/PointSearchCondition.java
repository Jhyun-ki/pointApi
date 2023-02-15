package com.hyunki.pointapi.domain.dto;

import com.hyunki.pointapi.domain.enums.PointType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PointSearchCondition {
    private String username;
    private PointType pointType;
    private String fromIssueDate;
    private String toIssueDate;
}
