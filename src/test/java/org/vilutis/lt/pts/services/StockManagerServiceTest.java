package org.vilutis.lt.pts.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.vilutis.lt.pts.dto.TradeDTO;
import org.vilutis.lt.pts.events.TradeEvent;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.services.api.StockService;

class StockManagerServiceTest {

    public static final String STOCK = "TSLA";

    @Mock
    StockService stockService;

    @Mock
    AlertService alertService;

    @InjectMocks
    StockManagerService stockManagerService;

    StockManagerServiceTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        openMocks(this);
        stockManagerService = new StockManagerService(stockService, alertService, BigDecimal.TEN);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleTradeEvent_increaseAboveThreshold() {
        BigDecimal price = BigDecimal.valueOf(1000);
        when(stockService.getPreviousPrice(eq(STOCK))).thenReturn(price);
        when(stockService.getStockPrice(eq(STOCK))).thenReturn(makeStockPrice(STOCK, price));

        stockManagerService.handleTradeEvent(makeEvent(1100));
        stockManagerService.handleTradeEvent(makeEvent(1111));
        stockManagerService.handleTradeEvent(makeEvent(1122));
        stockManagerService.handleTradeEvent(makeEvent(1133));

        verify(alertService).priceIncreased(any(), any(), any());
        verify(alertService, never()).priceDecreased(any(), any(), any());
    }

    @Test
    void handleTradeEvent_increaseWithinThreshold() {
        BigDecimal price = BigDecimal.valueOf(1000);
        when(stockService.getPreviousPrice(eq(STOCK))).thenReturn(price);
        when(stockService.getStockPrice(eq(STOCK))).thenReturn(makeStockPrice(STOCK, price));

        stockManagerService.handleTradeEvent(makeEvent(920));
        stockManagerService.handleTradeEvent(makeEvent(1000));
        stockManagerService.handleTradeEvent(makeEvent(1090));

        verify(alertService, never()).priceIncreased(any(), any(), any());
        verify(alertService, never()).priceDecreased(any(), any(), any());
    }

    private StockPrice makeStockPrice(String stock, BigDecimal price) {
        return StockPrice.builder()
          .stock(stock)
          .price(price)
          .date(StockService.stripTime(new Date()))
          .build();
    }

    @Test
    void handleTradeEvent_increaseAboveThreshold_negative() {
        BigDecimal price = BigDecimal.valueOf(1000);
        when(stockService.getPreviousPrice(eq(STOCK))).thenReturn(price);
        when(stockService.getStockPrice(eq(STOCK))).thenReturn(makeStockPrice(STOCK, price));

        stockManagerService.handleTradeEvent(makeEvent(900));
        stockManagerService.handleTradeEvent(makeEvent(888));
        stockManagerService.handleTradeEvent(makeEvent(777));
        stockManagerService.handleTradeEvent(makeEvent(666));

        verify(alertService, never()).priceIncreased(any(), any(), any());
        verify(alertService).priceDecreased(any(), any(), any());
    }

    private TradeEvent makeEvent(double newPrice) {
        return TradeEvent.with(
          TradeDTO.builder()
            .stock(STOCK)
            .price(BigDecimal.valueOf(newPrice))
            .timestamp(new Date())
            .build()
        );
    }

    @Test
    void getCurrentPrices_cache() {
        when(stockService.getCurrentPrice(eq(STOCK)))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.getCurrentPrices(Arrays.asList(STOCK));
        stockManagerService.getCurrentPrices(Arrays.asList(STOCK));
        stockManagerService.getCurrentPrices(Arrays.asList(STOCK));

        verify(stockService).getCurrentPrice(eq(STOCK));
        verify(stockService, never()).getPreviousPrice(eq(STOCK));
    }

    @Test
    void getPreviousPrices_cache() {
        when(stockService.getPreviousPrice(eq(STOCK)))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.getPreviousPrices(Arrays.asList(STOCK));
        stockManagerService.getPreviousPrices(Arrays.asList(STOCK));
        stockManagerService.getPreviousPrices(Arrays.asList(STOCK));

        verify(stockService).getPreviousPrice(eq(STOCK));
        verify(stockService, never()).getCurrentPrice(eq(STOCK));
    }

}