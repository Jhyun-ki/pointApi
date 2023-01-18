package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Account account;

    private int orderPrice;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<PointOrder> pointOrders = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(Account account) {
        this.account = account;
        this.orderStatus = OrderStatus.ORDER;
    }

    private void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
    private void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addPointOrder(PointOrder pointOrder) {
        this.pointOrders.add(pointOrder);
        pointOrder.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // == 비즈니스 메서드 == //
    public static Order createOrder(Account account, OrderItem... orderItems) {
        Order order = new Order(account);

        int orderPrice = 0;

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            orderPrice += orderItem.getTotalPrice();
        }

        order.setOrderPrice(orderPrice);

        if(account.getTotalPointAmt() < orderPrice) {
            throw new IllegalStateException("보유 포인트가 부족 합니다.");
        }

        //포인트 차감
        for(Point point : account.getPoints()) {
            if(orderPrice <= point.getRemainPointAmt()) {
                PointOrder pointOrder = PointOrder.createPointOrder(order, orderPrice);

                order.addPointOrder(pointOrder);
                point.usePoint(pointOrder);

                break;
            }
            else {
                int remainPointAmt = point.getRemainPointAmt();
                PointOrder pointOrder = PointOrder.createPointOrder(order, remainPointAmt);

                order.addPointOrder(pointOrder);
                point.usePoint(pointOrder);

                orderPrice = orderPrice - remainPointAmt;
            }
        }

        return order;
    }

    public static void cancelOrder(Order order) {
        //포인트 원복
        for(PointOrder pointOrder : order.getPointOrders()) {
            pointOrder.getPoint().plusRemainPointAmt(pointOrder.getUsePointAmt());
        }

        //취소 상태 처리
        order.setOrderStatus(OrderStatus.CANCEL);
    }
}
