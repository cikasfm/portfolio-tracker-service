package org.vilutis.lt.pts.services;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.vilutis.lt.pts.model.Trade;

/**
 * Service to retrieve Stock {@link Trade}s from storage
 */
public interface TradeService {

    Pageable TEN_LATEST = PageRequest.of(0, 10, Sort.by(Direction.DESC, "timestamp"));

    default List<Trade> getHistory(String accountNumber, String stock) {
        return this.getHistory(accountNumber, stock, TEN_LATEST);
    }
    List<Trade> getHistory(String accountNumber, String stock, Pageable page);

    default List<Trade> getPurchases(String accountNumber) {
        return this.getPurchases(accountNumber, TEN_LATEST);
    }
    List<Trade> getPurchases(String accountNumber, Pageable page);

    default List<Trade> getLiquidations(String accountNumber) {
        return this.getLiquidations(accountNumber, TEN_LATEST);
    }
    List<Trade> getLiquidations(String accountNumber, Pageable page);

}
