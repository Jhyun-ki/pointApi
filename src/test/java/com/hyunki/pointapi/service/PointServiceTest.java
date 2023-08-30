package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.dto.PointRequestDto;
import com.hyunki.pointapi.domain.entity.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
        PointRequestDto pointRequestDto1 = PointRequestDto.builder()
                .pointAmt(2000)
                .username("hkjung")
                .build();
        PointRequestDto pointRequestDto2 = PointRequestDto.builder()
                .pointAmt(5000)
                .username("hkjung")
                .build();

        //when
        Point point1 = pointService.createPoint(pointRequestDto1);
        Point point2 = pointService.createPoint(pointRequestDto2);

        //then
        assertThat(point1).isNotNull();
        assertThat(point1.getPointAmt()).isEqualTo(2000);
        assertThat(point1.getAccount().getUsername()).isEqualTo("hkjung");
        assertThat(point1.getAccount().getPoints()).isNotEmpty();
        assertThat(point1.getAccount().getPoints().size()).isEqualTo(2);
        assertThat(point1.getAccount().getTotalPointAmt()).isEqualTo(point1.getRemainPointAmt() + point2.getRemainPointAmt());
    }
}