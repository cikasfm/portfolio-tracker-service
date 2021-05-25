package org.vilutis.lt.pts.services;

import java.util.List;
import java.util.Map;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;

/**
 * Service providing access to {@link org.vilutis.lt.pts.model.Stock}s and {@link StockPrice}s
 */
public interface StockService {

    /**
     * Provides current ( latest ) stock prices by stock
     * @param stocks
     * @return
     */
    Map<String, StockPrice> getCurrentPrices(List<String> stocks);

    /**
     * Resolves currently active stocks in the system
     * @return
     */
    List<Stock> getActiveStocks();
}
