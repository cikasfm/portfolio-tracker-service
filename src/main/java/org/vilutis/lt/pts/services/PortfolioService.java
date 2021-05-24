package org.vilutis.lt.pts.services;

import org.vilutis.lt.pts.dto.PortfolioDTO;

public interface PortfolioService {

    PortfolioDTO getAccountPortfolio(String accountNumber);

}
