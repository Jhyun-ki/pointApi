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

    public Account(String userName) {
        this.userName = userName;
    }

    public void addPoint(Point point) {
        this.points.add(point);
        point.setAccount(this);
    }

    public void plusTotalPointAmt(int pointAmt) {
        this.totalPointAmt += pointAmt;
    }

    public void minusTotalPointAmt(int pointAmt) {
        int totalPointAmt = this.totalPointAmt - pointAmt;
        if(totalPointAmt < 0) {
            throw new IllegalStateException("-금액은 불가");
        }

        this.totalPointAmt = totalPointAmt;
    }

    public static Account createAccount(String userName) {
        Account account = new Account(userName);
        return account;
    }
}
