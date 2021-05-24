package org.vilutis.lt.pts.services;

import java.util.List;
import org.vilutis.lt.pts.model.StockPrice;

public interface StockService {

    List<StockPrice> getCurrentPrices(List<String> stocks);
}
