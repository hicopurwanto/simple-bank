package nobicode.restful.repository;

import nobicode.restful.entity.Account;
import nobicode.restful.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findFirstByUserAndId(User user, String id);

}
