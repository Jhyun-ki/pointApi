package com.hyunki.pointapi.repository;

import com.hyunki.pointapi.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
