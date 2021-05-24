package org.vilutis.lt.pts.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.exception.AccountNotFoundException;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.repository.AccountRepository;
import org.vilutis.lt.pts.repository.StockRepository;
import org.vilutis.lt.pts.services.AccountService;
import org.vilutis.lt.pts.services.StockService;

@Service
@Profile("!mock")
public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    @Autowired
    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StockPrice> getCurrentPrices(List<String> stocks) {
        return null;
    }
}
