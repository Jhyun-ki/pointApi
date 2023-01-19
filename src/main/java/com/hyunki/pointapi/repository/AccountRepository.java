package com.hyunki.pointapi.repository;

import com.hyunki.pointapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
