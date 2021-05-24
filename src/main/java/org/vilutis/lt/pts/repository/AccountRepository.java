package org.vilutis.lt.pts.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Account;

@Repository
@Profile("!mock")
public interface AccountRepository extends CrudRepository<Account, String> {

}
