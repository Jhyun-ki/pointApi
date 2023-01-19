package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.dto.CreatePointRequest;
import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.domain.enums.PointType;
import com.hyunki.pointapi.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final AccountService accountService;

    @Transactional
    public Point createPoint(CreatePointRequest createPointRequest) {
        Account findAccount = accountService.getOrCreateAccount(createPointRequest.getUsername());
        Point point = Point.createPoint(findAccount, createPointRequest.getPointAmt(), PointType.PAY);

        Point savedPoint = pointRepository.save(point);

        return savedPoint;
    }

}
