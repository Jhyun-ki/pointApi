package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PointTest {

    @Test
    @DisplayName("포인트 생성 테스트")
    public void pointConstructor() throws Exception {
        //given & when
        Point point = Point.createPoint(Account.createAccount("hkjung"), 1000, PointType.PAY);

        //then
        assertThat(point).isNotNull();
        assertThat(point.getPointAmt()).isEqualTo(point.getRemainPointAmt());
        assertThat(point.getPointStatus()).isEqualTo(PointStatus.NORMAL);
    }

    @Test
    @DisplayName("Point와 PointOrder의 연관관계가 올바르게 설정되어 입력이 되는지 확인하는 테스트")
    public void addPointOrderTest() throws Exception {
        //given
        Point point =  Point.createPoint(Account.createAccount("hkjung"), 10000, PointType.PAY);
        PointOrder pointOrder = new PointOrder();
        pointOrder.setPoint(point);

        //when
        point.addPointOrder(pointOrder);
        
        //then
        assertThat(point).isNotNull();
        assertThat(point.getPointOrders().size()).isNotEqualTo(0);
        assertThat(point.getPointOrders()).isNotNull();
    }

    @Test
    @DisplayName("포인트 적립 메서드 테스트")
    public void createPointTest() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");

        //when
        Point point1 = Point.createPoint(account, 10000, PointType.PAY);
        Point point2 = Point.createPoint(account, 5000, PointType.PAY);

        //then
        assertThat(point1.getPointAmt()).isEqualTo(10000);
        assertThat(point2.getPointAmt()).isEqualTo(5000);
        assertThat(point1.getPointAmt() + point2.getPointAmt()).isEqualTo(15000);
        assertThat(account.getTotalPointAmt()).isEqualTo(15000);
    }

    @Test
    @DisplayName("주문 시 포인트 차감 테스트")
    public void usePointTest() throws Exception {
        //given
        PointOrder[] pointOrders = new PointOrder[3];
        pointOrders[0] = PointOrder.createPointOrder(new Order(), 3000);
        pointOrders[1] = PointOrder.createPointOrder(new Order(), 5000);
        pointOrders[2] = PointOrder.createPointOrder(new Order(), 500);

        Point point = Point.createPoint(Account.createAccount("hkjung"), 15000, PointType.PAY);

        //when
        Point usedPoint = Point.usePoint(point, pointOrders);

        
        //then
        assertThat(usedPoint.getRemainPointAmt()).isEqualTo(6500);
    }

    @Test
    @DisplayName("남은 포인트 금액 차감 시 마이너스 금액이 될 경우 Exception을 발생 시킨다.")
    public void usePoint_input_minus_amount() throws Exception {
        //given
        Point point = Point.createPoint(Account.createAccount("hkjung"), 5000, PointType.PAY);

        PointOrder[] pointOrders = new PointOrder[1];
        pointOrders[0] = PointOrder.createPointOrder(new Order(), -3000);

        //when & then
        assertThrows(IllegalStateException.class, () -> {
            Point.usePoint(point, pointOrders);
        });
    }

    @Test
    @DisplayName("남은 포인트 금액 차감 시 마이너스 금액이 될 경우 Exception을 발생 시킨다.")
    public void usePoint_remain_minus_amount() throws Exception {
        //given
        Point point = Point.createPoint(Account.createAccount("hkjung"), 5000, PointType.PAY);

        PointOrder[] pointOrders = new PointOrder[3];
        pointOrders[0] = PointOrder.createPointOrder(new Order(), 3000);
        pointOrders[1] = PointOrder.createPointOrder(new Order(), 5000);
        pointOrders[2] = PointOrder.createPointOrder(new Order(), 500);

        //when & then
        assertThrows(IllegalStateException.class, () -> {
            Point.usePoint(point, pointOrders);
        });
    }
}