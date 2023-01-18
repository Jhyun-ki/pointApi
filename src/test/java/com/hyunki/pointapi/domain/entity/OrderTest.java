package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.OrderStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("보유 포인트가 부족하면 Exception이 발생한다.")
    public void createOrderThrowsExceptionTest1() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        account.plusTotalPointAmt(5000);

        OrderItem orderItem1 = OrderItem.createOrderItem(new Item(), 1000, 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(new Item(), 3000, 3);


        //when & then
        assertThrows(IllegalStateException.class, () -> {
            Order.createOrder(account, orderItem1, orderItem2);
        });
    }
    
    @Test
    @DisplayName("1개의 point로 상품 주문 테스트")
    public void createOrderTest1() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        Point point = Point.createPoint(account, 7000, PointType.PAY);
        account.addPoint(point);

        OrderItem orderItem1 = OrderItem.createOrderItem(new Item(), 1000, 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(new Item(), 1000, 3);

        //when
        Order order = Order.createOrder(account, orderItem1, orderItem2);

        //then
        assertEquals(1, order.getPointOrders().size(), "1개의 포인트가 주문금액보다 클 경우 PointOrder 내역은 1개가 쌓인다.");
        assertEquals(2, order.getOrderItems().size(), "한번의 주문에 다른 2개의 아이템을 주문할 경우 ItemOrder 내역은 2개가 쌓인다.");
        assertEquals(3000, point.getRemainPointAmt(), "주문시에 포인트 금액이 올바르게 차감된다.");
        assertEquals(orderItem1.getTotalPrice() + orderItem2.getTotalPrice(), order.getOrderPrice(), "주문금액은 OrderItems의 (아이템 * 개수)를 모두 더한 값이다.");
    }

    @Test
    @DisplayName("1개 이상의 point로 상품 주문 테스트")
    public void createOrderTest2() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        Point point1 = Point.createPoint(account, 2000, PointType.PAY);
        Point point2 = Point.createPoint(account, 300, PointType.PAY);
        Point point3 = Point.createPoint(account, 10000, PointType.PAY);

        account.addPoint(point1);
        account.addPoint(point2);
        account.addPoint(point3);

        OrderItem orderItem1 = OrderItem.createOrderItem(new Item(), 1000, 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(new Item(), 1200, 3);
        OrderItem orderItem3 = OrderItem.createOrderItem(new Item(), 1500, 3);

        //when
        Order order = Order.createOrder(account, orderItem1, orderItem2, orderItem3);

        //then
        assertEquals(3, order.getPointOrders().size(), "주문시에 3개의 포인트가 필요할 경우 PointOrder 내역은 3개가 쌓인다.");
        assertEquals(0, account.getPoints().get(0).getRemainPointAmt(), "point는 먼저 추가된 순서대로 차감되며, 주문시에 포인트 금액이 올바르게 차감된다.");
        assertEquals(0, account.getPoints().get(1).getRemainPointAmt(), "point는 먼저 추가된 순서대로 차감되며, 주문시에 포인트 금액이 올바르게 차감된다.");
        assertEquals(3200, account.getPoints().get(2).getRemainPointAmt(), "point는 먼저 추가된 순서대로 차감되며, 주문시에 포인트 금액이 올바르게 차감된다.");
    }

    @Test
    @DisplayName("1개 point로 주문된 상품 취소 테스트")
    public void cancelOrderTest1() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        Point point = Point.createPoint(account, 7000, PointType.PAY);

        account.addPoint(point);

        OrderItem orderItem1 = OrderItem.createOrderItem(new Item(), 1000, 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(new Item(), 1000, 3);

        Order order = Order.createOrder(account, orderItem1, orderItem2);

        //when
        Order.cancelOrder(order);

        //then
        assertEquals(7000, point.getRemainPointAmt(), "주문시에 사용됐던 remainPointAmt가 원복 되어야 한다.");
        assertEquals(order.getOrderStatus(), OrderStatus.CANCEL, "주문 취소시에 OrderStatus 는 CANCEL 이다.");
    }

    @Test
    @DisplayName("1개 이상의 point로 주문된 상품 취소 테스트")
    public void cancelOrderTest2() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        Point point = Point.createPoint(account, 5000, PointType.PAY);
        Point point1 = Point.createPoint(account, 3000, PointType.PAY);
        Point point2 = Point.createPoint(account, 6000, PointType.PAY);
        account.addPoint(point, point1, point2);

        OrderItem orderItem = OrderItem.createOrderItem(new Item(), 3000, 1);
        OrderItem orderItem1 = OrderItem.createOrderItem(new Item(), 1000, 3);
        OrderItem orderItem2 = OrderItem.createOrderItem(new Item(), 4000, 1);

        Order order = Order.createOrder(account, orderItem, orderItem1, orderItem2);

        //when
        Order.cancelOrder(order);

        //then
        assertEquals(5000, order.getPointOrders().get(0).getPoint().getRemainPointAmt(), "주문 취소시에 remainPointAmt가 모두 원복 된다.");
        assertEquals(3000, order.getPointOrders().get(1).getPoint().getRemainPointAmt(), "주문 취소시에 remainPointAmt가 모두 원복 된다.");
        assertEquals(6000, order.getPointOrders().get(2).getPoint().getRemainPointAmt(), "주문 취소시에 remainPointAmt가 모두 원복 된다.");
        assertEquals(order.getOrderStatus(), OrderStatus.CANCEL, "주문 취소시에 OrderStatus 는 CANCEL 이다.");
    }
}