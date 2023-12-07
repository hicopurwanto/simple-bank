package nobicode.restful.service;

import jakarta.transaction.Transactional;
import nobicode.restful.dto.CreateTransactionRequest;
import nobicode.restful.dto.TransactionResponse;
import nobicode.restful.entity.Account;
import nobicode.restful.entity.Transaction;
import nobicode.restful.entity.User;
import nobicode.restful.repository.AccountRepository;
import nobicode.restful.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TransactionResponse create(User user, CreateTransactionRequest request) {
        validationService.validate(request);

        Account account = accountRepository.findFirstByUserAndId(user, request.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "kontak tidak ditemukan"));

        Transaction transactions = new Transaction();
        transactions.setId(UUID.randomUUID().toString());
        transactions.setAccount(account);
        transactions.setToAccount(request.getToAccount());
        transactions.setAmount(request.getAmount());
        transactions.setCreatedAt(request.getCreatedAt());
        transactionRepository.save(transactions);

        return toTransactionsResponse(transactions);
    }

    public TransactionResponse get(User user, String accountId, String transactionId) {
        Account account = accountRepository.findFirstByUserAndId(user, accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "kontak tidak ditemukan"));

        Transaction transactions = transactionRepository.findFirstByAccountAndId(account, transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaksi tidak ditemukan"));

        return toTransactionsResponse(transactions);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TransactionResponse> list(User user, String accountId) {
        Account account = accountRepository.findFirstByUserAndId(user, accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "kontak tidak ditemukan"));

        List<Transaction> transactions = transactionRepository.findAllByAccount(account);
        return transactions.stream().map(this::toTransactionsResponse).toList();
    }

    private TransactionResponse toTransactionsResponse(Transaction transactions) {
        return TransactionResponse.builder()
                .id(transactions.getId())
                .toAccount(transactions.getToAccount())
                .amount(transactions.getAmount())
                .createdAt(transactions.getCreatedAt())
                .build();
    }
}
