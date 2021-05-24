package org.vilutis.lt.pts.services;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.vilutis.lt.pts.model.Holding;

public interface HoldingsService {

    Pageable FIRST_TEN = PageRequest.of(0, 20, Sort.by(Direction.ASC, "stock"));

    default List<Holding> getHoldings(String accountNumber) {
        return this.getHoldings(accountNumber, FIRST_TEN);
    }

    /**
     * Find account stock holdings by account number and stock name
     *
     * @param accountNumber
     * @param page
     * @return
     */
    List<Holding> getHoldings(String accountNumber, Pageable page);

}
