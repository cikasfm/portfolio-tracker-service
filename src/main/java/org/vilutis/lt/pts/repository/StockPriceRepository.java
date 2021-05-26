package org.vilutis.lt.pts.repository;

import java.util.Date;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * Finds the latest one
     * @param stock
     * @return
     */
    Optional<StockPrice> findOneByStockOrderByDateDesc(@Param("stock") String stock);

    /**
     * Finds the latest one before specified date
     * @param stock
     * @return
     */
    @Query("from StockPrice p where p.stock = :stock and p.date < :date order by p.date desc")
    Optional<StockPrice> findOneBeforeDate(@Param("stock") String stock, @Param("date") Date date);

}
