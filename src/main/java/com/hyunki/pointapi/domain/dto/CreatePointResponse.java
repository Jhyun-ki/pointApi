package com.hyunki.pointapi.domain.dto;

import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter
public class CreatePointResponse extends RepresentationModel<CreatePointResponse> {
    private Long pointId;
    private String username;
    private int pointAmt;
    private PointType pointType;
    private PointStatus pointStatus;

    public CreatePointResponse(Point point) {
        pointId = point.getId();
        username = point.getAccount().getUsername();
        pointAmt = point.getPointAmt();
        pointType = point.getPointType();
        pointStatus = point.getPointStatus();
    }
}
