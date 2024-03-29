package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.repository.AccountRepository;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.swing.text.html.Option;
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
            Account account = accountRepository.save(Account.createAccount(username));
            findAccount = accountRepository.findByUsername(account.getUsername());
        }

        if(findAccount.isEmpty()) {
            throw new InternalException("계정 찾기, 생성 오류가 발생 했습니다.");
        }

        return findAccount.get();
    }
}
