package com.hyunki.pointapi.repository;


import com.hyunki.pointapi.domain.dto.PointResponseDto;
import com.hyunki.pointapi.domain.dto.PointSearchCondition;
import com.hyunki.pointapi.domain.dto.QPointResponseDto;
import com.hyunki.pointapi.domain.enums.PointType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hyunki.pointapi.domain.entity.QAccount.account;
import static com.hyunki.pointapi.domain.entity.QPoint.point;
import static com.querydsl.core.types.Order.*;
import static org.springframework.util.StringUtils.hasText;

public class PointRepositoryImpl implements PointRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public PointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<PointResponseDto> search(PointSearchCondition condition, Pageable pageable) {
        List<PointResponseDto> content = queryFactory
                .select(new QPointResponseDto(
                        point.id.as("pointId"),
                        account.username,
                        point.pointAmt,
                        point.remainPointAmt,
                        point.pointType,
                        point.pointStatus,
                        point.issueDate
                ))
                .from(point)
                .join(point.account, account)
                .where(
                        usernameEq(condition.getUsername()),
                        pointTypeEq(condition.getPointType()),
                        issueDateGoe(condition.getFromIssueDate()),
                        issueDateLoe(condition.getToIssueDate())
                )
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(point.count())
                .from(point)
                .where(
                        usernameEq(condition.getUsername()),
                        pointTypeEq(condition.getPointType()),
                        issueDateGoe(condition.getFromIssueDate()),
                        issueDateLoe(condition.getToIssueDate())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression issueDateLoe(String toIssueDate) {
        return toIssueDate != null ? point.issueDate.loe(LocalDate.parse(toIssueDate)) : null;
    }

    private BooleanExpression issueDateGoe(String fromIssueDate) {
        return fromIssueDate != null ? point.issueDate.goe(LocalDate.parse(fromIssueDate)) : null;
    }

    private BooleanExpression pointTypeEq(PointType pointType) {
        return (pointType != null) ? point.pointType.eq(pointType) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? account.username.eq(username) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? ASC : DESC;
            String property = order.getProperty();
            PathBuilder pathBuilder = new PathBuilder<>(point.getClass(), "point");
            orders.add(new OrderSpecifier(direction, pathBuilder.get(property)));
        });

        return orders;
    }
}
