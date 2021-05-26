package org.vilutis.lt.pts.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Holding;
import org.vilutis.lt.pts.repository.HoldingRepository;
import org.vilutis.lt.pts.services.api.HoldingsService;

@Service
@Profile("!mock")
public class HoldingsServiceImpl implements HoldingsService {

    private final HoldingRepository repository;

    @Autowired
    public HoldingsServiceImpl(HoldingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Holding> getHoldings(String accountNumber, Pageable page) {
        return repository.findByAccountNumber(accountNumber, page);
    }
}
