package org.vilutis.lt.pts.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Holding;

@Repository
public interface HoldingRepository extends CrudRepository<Holding, String> {

    List<Holding> findByAccountNumber(@Param("accountNumber") String accountNumber, Pageable page);

}
