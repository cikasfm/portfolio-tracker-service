package org.vilutis.lt.pts.repository;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vilutis.lt.pts.model.DirectionEnum;
import org.vilutis.lt.pts.model.Trade;

@Repository
@Profile("!mock")
public interface TradeRepository extends CrudRepository<Trade, String> {

    List<Trade> findByAccountNumberAndStockOrderByTimestampDesc(
      @Param("accountNumber") String accountNumber,
      @Param("stock") String stock,
      Pageable page);

    List<Trade> findByAccountNumberAndDirectionOrderByTimestampDesc(
      @Param("accountNumber") String accountNumber,
      @Param("direction") DirectionEnum direction,
      Pageable page);

}
