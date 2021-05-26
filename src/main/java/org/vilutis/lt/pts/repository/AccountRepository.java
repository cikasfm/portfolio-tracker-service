package org.vilutis.lt.pts.repository;

import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Account;

@Repository
@Profile("!mock")
public interface AccountRepository extends CrudRepository<Account, String> {

    Optional<Account> findOneByName(@Param("accountName") String accountName);
}
