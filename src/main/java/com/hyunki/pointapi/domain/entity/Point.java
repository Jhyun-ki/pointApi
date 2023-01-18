package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.PointStatus;
import com.hyunki.pointapi.domain.enums.PointType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    @ManyToOne(fetch = LAZY, cascade = ALL) // Point 지급 시에 Account의 totalPointAmt가 수정되어야 한다.
    @JoinColumn(name = "user_id")
    private Account account;

    private int pointAmt;

    private int remainPointAmt;

    @Enumerated(STRING)
    private PointType pointType;

    @Enumerated(STRING)
    private PointStatus pointStatus;

    @OneToMany(mappedBy = "point", cascade = ALL)
    List<PointOrder> pointOrders = new ArrayList<>();

    //포인트 적립 생성자
    private Point(Account account, int pointAmt, PointType pointType) {
        this.account = account;
        this.pointAmt = pointAmt;
        this.remainPointAmt = pointAmt;
        this.pointType = pointType;
        this.pointStatus = PointStatus.NORMAL;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addPointOrder(PointOrder pointOrder) {
        this.pointOrders.add(pointOrder);
        pointOrder.setPoint(this);
    }

    /**
     * 남은 포인트 금액 차감 메서드
     */
    private void minusRemainPointAmt(int usePointAmt) {
        int remainPointAmt = this.remainPointAmt - usePointAmt;

        if(usePointAmt < 0) {
            throw new IllegalStateException("사용 금액에 마이너스 금액이 입력될 수 없습니다.");
        }

        if(remainPointAmt < 0) {
            throw new IllegalStateException("남은 포인트 금액은 마이너스 금액이 될 수 없습니다.");
        }

        this.remainPointAmt = remainPointAmt;
    }

    /**
     * 남은 포인트 금액 원복 메서드
     */
    public void plusRemainPointAmt(int addPointAmt) {
        int remainPointAmt = this.remainPointAmt + addPointAmt;

        if(remainPointAmt > this.pointAmt) {
            throw new IllegalStateException("남은 포인트 금액이 포인트 금액 보다 클 수 없습니다.");
        }

        if(remainPointAmt < 0) {
            throw new IllegalStateException("남은 포인트 금액은 마이너스 금액이 될 수 없습니다.");
        }

        this.remainPointAmt = remainPointAmt;
    }

    /**
     * 포인트 적립 메서드
     */
    public static Point createPoint(Account account, int pointAmt, PointType pointType) {
        Point point = new Point(account, pointAmt, pointType);
        account.plusTotalPointAmt(pointAmt);

        return point;
    }

    /**
     * 포인트 차감 메서드
     */
    public void usePoint(PointOrder... pointOrders) {
        int totalUsePointAmt = 0;
        for (PointOrder pointOrder : pointOrders) {
            this.addPointOrder(pointOrder);
            totalUsePointAmt += pointOrder.getUsePointAmt();
        }

        this.minusRemainPointAmt(totalUsePointAmt);
        this.getAccount().minusTotalPointAmt(totalUsePointAmt);

    }
}
