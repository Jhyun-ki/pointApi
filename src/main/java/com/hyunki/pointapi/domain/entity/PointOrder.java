package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.entity.PointOrderPk;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@IdClass(PointOrderPk.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointOrder {
    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int usePointAmt;

    private void setPointUseAmt(int pointUseAmt) {
        this.usePointAmt = pointUseAmt;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public static PointOrder createPointOrder(Order order, int pointUseAmt) {
        PointOrder pointOrder = new PointOrder();
        pointOrder.setOrder(order);
        pointOrder.setPointUseAmt(pointUseAmt);

        return pointOrder;
    }
}
