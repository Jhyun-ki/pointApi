package com.hyunki.pointapi.repository;

import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.domain.enums.PointType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PointRepositoryTest {

    @Autowired
    PointRepository pointRepository;

    @Test
    public void PointRepositoryTest() throws Exception {
        //given
        Point point = pointRepository.save(Point.createPoint(Account.createAccount("hkjung"), 1000, PointType.PAY));

        //when

        //then
    }
}