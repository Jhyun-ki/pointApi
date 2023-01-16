package com.hyunki.pointapi.repository;


import com.hyunki.pointapi.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
