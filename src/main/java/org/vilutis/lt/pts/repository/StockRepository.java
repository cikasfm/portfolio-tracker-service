package org.vilutis.lt.pts.repository;

import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Stock;

@Repository
@Profile("!mock")
public interface StockRepository extends CrudRepository<Stock, String> {

    /**
     * Find a stock by stock name
     * @param stock
     * @return
     */
    Optional<Stock> findOneByStock(@Param("stock") String stock);

}
