package org.vilutis.lt.pts.repository;

import java.util.Date;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;

@Repository
@Profile("!mock")
public interface StockPriceRepository extends CrudRepository<StockPrice, String> {

    /**
     * Find a stock price for the given date
     * @param stock
     * @param date
     * @return
     */
    Optional<StockPrice> findOneByStockAndDate(@Param("stock") String stock, @Param("date") Date date);

}
