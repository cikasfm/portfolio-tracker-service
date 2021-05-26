package org.vilutis.lt.pts.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.DirectionEnum;
import org.vilutis.lt.pts.model.Trade;
import org.vilutis.lt.pts.repository.TradeRepository;
import org.vilutis.lt.pts.services.api.TradeService;

@Service
@Profile("!mock")
public class TradeServiceImpl implements TradeService {

    private final TradeRepository repository;

    @Autowired
    public TradeServiceImpl(TradeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Trade> getHistory(String accountNumber, String stock, Pageable page) {
        return repository.findByAccountNumberAndStockOrderByTimestampDesc(accountNumber, stock, page);
    }

    @Override
    public List<Trade> getPurchases(String accountNumber, Pageable page) {
        return repository
          .findByAccountNumberAndDirectionOrderByTimestampDesc(accountNumber,
            DirectionEnum.BUY, page);
    }

    @Override
    public List<Trade> getLiquidations(String accountNumber, Pageable page) {
        return repository
          .findByAccountNumberAndDirectionOrderByTimestampDesc(accountNumber,
            DirectionEnum.SELL, page);
    }

}
