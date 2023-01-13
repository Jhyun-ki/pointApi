package com.hyunki.pointapi.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String userName;

    private int totalPointAmt;

    @OneToMany(mappedBy = "account")
    private List<Point> points = new ArrayList<>();

    public void addPoint(Point point) {
        this.points.add(point);
        point.setAccount(this);
    }
}
