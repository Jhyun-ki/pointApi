package com.hyunki.pointapi.repository;

import com.hyunki.pointapi.domain.dto.PointResponseDto;
import com.hyunki.pointapi.domain.dto.PointSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointRepositoryCustom {
    Page<PointResponseDto> search(PointSearchCondition pointSearchCondition, Pageable pageable);
}
