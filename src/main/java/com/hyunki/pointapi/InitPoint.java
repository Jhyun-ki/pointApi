package com.hyunki.pointapi;

import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.domain.entity.Point;
import com.hyunki.pointapi.domain.enums.PointType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitPoint {
    private final InitPointService initPointService;

    @PostConstruct
    public void init() {
        initPointService.init();
    }

    @Component
    static class InitPointService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Account hkjung = Account.createAccount("hkjung");
            Account jhk = Account.createAccount("jhk");
            Account lkw = Account.createAccount("lkw");
            Account kwk = Account.createAccount("kwk");

            for (int i = 0; i < 100; i++) {
                em.persist(Point.createPoint(hkjung, 1000, PointType.PAY));
                em.persist(Point.createPoint(jhk, 2000, PointType.PAY));
                em.persist(Point.createPoint(lkw, 3000, PointType.PAY));
                em.persist(Point.createPoint(kwk, 500, PointType.PAY));
            }

        }
    }
}
