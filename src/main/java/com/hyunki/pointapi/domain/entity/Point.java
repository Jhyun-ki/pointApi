package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
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
public class Point extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Account account;

    private String userName;

    private int pointAmt;

    private int remainPointAmt;

    @Enumerated(STRING)
    private PointType pointType;

    @Enumerated(STRING)
    private PointStatus pointStatus;

    @OneToMany(mappedBy = "point")
    List<PointOrder> pointOrders = new ArrayList<>();

    public void addPointOrder(PointOrder pointOrder) {
        this.pointOrders.add(pointOrder);
        pointOrder.setPoint(this);
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
