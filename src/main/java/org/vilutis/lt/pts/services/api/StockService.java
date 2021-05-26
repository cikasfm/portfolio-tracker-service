package org.vilutis.lt.pts.services.api;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;

/**
 * Service providing access to {@link org.vilutis.lt.pts.model.Stock}s and {@link StockPrice}s
 */
public interface StockService {

    /**
     * Provides current ( latest ) stock price by stock
     * @param stock
     * @return
     */
    BigDecimal getCurrentPrice(String stock);

    /**
     * Provides current ( latest ) {@link StockPrice} object by stock
     * @param stock
     * @return
     */
    StockPrice getStockPrice(String stock);

    /**
     * Resolves currently active stocks in the system
     */
    List<Stock> getActiveStocks();

    // Old school java time :)
    static Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    void update(String stock, Date date, BigDecimal price);

    BigDecimal getPreviousPrice(String stock);

    void setIncreaseAlertSent(String stock, Date date);

    void setDecreaseAlertSent(String stock, Date date);
}
