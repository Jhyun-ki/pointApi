package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.dto.CreatePointRequest;
import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.domain.entity.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class PointServiceTest {

    @Autowired
    PointService pointService;


    @Test
    @Transactional
    //@Rollback(value = false)
    public void createPointTest() throws Exception {
        //given
        CreatePointRequest createPointRequest1 = CreatePointRequest.builder()
                .pointAmt(2000)
                .username("hkjung")
                .build();
        CreatePointRequest createPointRequest2 = CreatePointRequest.builder()
                .pointAmt(5000)
                .username("hkjung")
                .build();

        //when
        Point point1 = pointService.createPoint(createPointRequest1);
        Point point2 = pointService.createPoint(createPointRequest2);

        //then
        assertThat(point1).isNotNull();
        assertThat(point1.getPointAmt()).isEqualTo(2000);
        assertThat(point1.getAccount().getUsername()).isEqualTo("hkjung");
        assertThat(point1.getAccount().getPoints()).isNotEmpty();
        assertThat(point1.getAccount().getPoints().size()).isEqualTo(2);
        assertThat(point1.getAccount().getTotalPointAmt()).isEqualTo(point1.getRemainPointAmt() + point2.getRemainPointAmt());
    }
}