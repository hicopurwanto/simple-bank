package nobicode.restful.service;

import jakarta.transaction.Transactional;
import nobicode.restful.dto.AccountResponse;
import nobicode.restful.dto.CreateAccountRequest;
import nobicode.restful.entity.Account;
import nobicode.restful.entity.User;
import nobicode.restful.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public AccountResponse create(User user, CreateAccountRequest request) {
        validationService.validate(request);

        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setAccountNumber(request.getAccountNumber());
        account.setUser(user);

        accountRepository.save(account);

        return toAccountResponse(account);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AccountResponse get(User user, String id) {
        Account account = accountRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Akun tidak ditemukan"));

        return toAccountResponse(account);
    }

    private AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .build();
    }
}
