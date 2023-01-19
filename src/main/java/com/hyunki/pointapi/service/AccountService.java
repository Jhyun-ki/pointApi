package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Table;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Account getOrCreateAccount(String username) {
        //PointApi DB에 등록된 유저가 아닐 경우 새로운 계정을 만들어준다.
        Optional<Account> findAccount = accountRepository.findByUsername(username);
        if(findAccount.isEmpty()) {
            Account account = Account.createAccount(username);
            accountRepository.save(account);
            findAccount = accountRepository.findByUsername(username);
        }

        if(findAccount.isEmpty()) {
            throw new NoResultException("계정을 찾는데 오류가 발생 했습니다.");
        }

        return findAccount.get();
    }
}
