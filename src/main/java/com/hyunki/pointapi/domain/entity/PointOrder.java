package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.compositepk.PointOrderPk;
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

    private int pointUseAmt;
}
