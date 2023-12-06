package nobicode.restful.repository;

import nobicode.restful.entity.Account;
import nobicode.restful.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findFirstByAccountAndId(Account account, String id);
}
