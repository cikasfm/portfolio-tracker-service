package org.vilutis.lt.pts.repository;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Holding;

@Repository
@Profile("!mock")
public interface HoldingRepository extends CrudRepository<Holding, String> {

    List<Holding> findByAccountNumber(@Param("accountNumber") String accountNumber, Pageable page);

}
