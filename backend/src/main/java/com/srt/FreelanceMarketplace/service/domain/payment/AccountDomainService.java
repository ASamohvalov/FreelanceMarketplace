package com.srt.FreelanceMarketplace.service.domain.payment;

import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.payment.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDomainService {
    private final AccountRepository repository;

    public void create(UserEntity user) {
        AccountEntity account = new AccountEntity(user);
        repository.save(account);
    }

    public void add(AccountEntity account, long amount) {
        account.setBalance(account.getBalance() + amount);
        repository.save(account);
    }

    public AccountEntity getByUser(UserEntity user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("user doesn't have an account"));
    }
}
