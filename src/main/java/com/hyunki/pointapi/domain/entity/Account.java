package com.hyunki.pointapi.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private int totalPointAmt;

    @OneToMany(mappedBy = "account", cascade = ALL)
    private List<Point> points = new ArrayList<>();

    private Account(String username) {
        this.username = username;
    }

    public void addPoint(Point... points) {
        for (Point point : points) {
            this.points.add(point);
            point.setAccount(this);
        }
    }

    public void plusTotalPointAmt(int pointAmt) {
        this.totalPointAmt += pointAmt;
    }

    public void minusTotalPointAmt(int pointAmt) {
        int totalPointAmt = this.totalPointAmt - pointAmt;
        if(totalPointAmt < 0) {
            throw new IllegalStateException("- 금액은 불가");
        }

        this.totalPointAmt = totalPointAmt;
    }

    public static Account createAccount(String username) {
        Account account = new Account(username);
        return account;
    }
}
