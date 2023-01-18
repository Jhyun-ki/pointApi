package com.hyunki.pointapi.domain.entity;

import com.hyunki.pointapi.domain.enums.PointType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    @DisplayName("계좌 생성 테스트")
    public void AccountTest() throws Exception {
        //given & when
        Account account = Account.createAccount("hkjung");

        //then
        assertThat(account).isNotNull();
    }

    @Test
    @DisplayName("Account <> Point 간의 연관관계 세팅이 되는지")
    public void addPointTest() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");
        Point point = Point.createPoint(account, 1000, PointType.PAY);

        //when
        account.addPoint(point);

        //then
        assertThat(account.getPoints().get(0)).isNotNull();
        assertThat(point.getAccount()).isNotNull();
    }

    @Test
    @DisplayName("totalPointAmt 누적이 되는지")
    public void plusTotalPointAmtTest() throws Exception {
        //given
        Account account = Account.createAccount("hkjung");

        //when
        account.plusTotalPointAmt(500);
        account.plusTotalPointAmt(100);
        account.plusTotalPointAmt(200);

        //then
        assertThat(account.getTotalPointAmt()).isEqualTo(800);
    }

    @Test
    @DisplayName("금액 차감시에 totalPointAmt가 0보다 작을 경우 Exception을 throw 하며, totalPointAmt가 정상적으로 차감 된다.")
    public void minusTotalPointAmt() throws Exception {
        //given
        Account account1 = Account.createAccount("hkjung");
        Account account2 = Account.createAccount("hkjung");

        account1.plusTotalPointAmt(5000);
        account2.plusTotalPointAmt(5000);

        //when & then
        account1.minusTotalPointAmt(4500);
        assertThat(account1.getTotalPointAmt()).isEqualTo(500);

        assertThrows(IllegalStateException.class, () -> {
            account2.minusTotalPointAmt(5500);
        });
    }

}