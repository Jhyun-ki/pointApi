package com.hyunki.pointapi.domain.entity;


import lombok.Getter;

import java.io.Serializable;

@Getter
public class PointOrderPk implements Serializable {
    private Long point;
    private Long order;
}
