package com.hyunki.pointapi.service;

import com.hyunki.pointapi.domain.entity.Account;
import com.hyunki.pointapi.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@DataJpaTest
@SpringBootTest
@Transactional
class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("계정이 존재하지 않을 경우에는 새로운 계정을 만들어준다.")
    public void getOrCreateAccountTest1() throws Exception {
        //given
        String username2 = "testUser";
        String username = "hkjung";
        accountRepository.save(Account.createAccount(username2));

        //when
        Account account = accountService.getOrCreateAccount(username);
        List<Account> findAccounts = accountRepository.findAll();

        //then
        assertThat(findAccounts.size()).isEqualTo(2);
        assertThat(account).isNotNull();
    }

    @Test
    @DisplayName("계정이 존재할 경우 기존 계정을 조회한다.")
    public void getOrCreateAccountTest2() throws Exception {
        //given
        String username = "hkjung";
        accountRepository.save(Account.createAccount(username));

        //when
        Account account = accountService.getOrCreateAccount(username);
        List<Account> findAccounts = accountRepository.findAll();

        //then
        assertThat(findAccounts.size()).isEqualTo(1);
        assertThat(account).isNotNull();
    }
}