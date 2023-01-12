package com.hyunki.pointapi.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int item_price;
}
