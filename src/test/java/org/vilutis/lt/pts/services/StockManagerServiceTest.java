package org.vilutis.lt.pts.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMostOnce;
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
import org.vilutis.lt.pts.services.api.StockService;

class StockManagerServiceTest {

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
        when(stockService.getPreviousPrice(eq("TSLA")))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.handleTradeEvent(makeEvent(1100));

        verify(alertService).priceIncreased(any(), any(), any());
        verify(alertService, never()).priceDecreased(any(), any(), any());
    }

    @Test
    void handleTradeEvent_increaseWithinThreshold() {
        when(stockService.getPreviousPrice(eq("TSLA")))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.handleTradeEvent(makeEvent(920));
        stockManagerService.handleTradeEvent(makeEvent(1000));
        stockManagerService.handleTradeEvent(makeEvent(1090));

        verify(alertService, never()).priceIncreased(any(), any(), any());
        verify(alertService, never()).priceDecreased(any(), any(), any());
    }

    @Test
    void handleTradeEvent_increaseAboveThreshold_negative() {
        when(stockService.getPreviousPrice(eq("TSLA")))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.handleTradeEvent(makeEvent(900));

        verify(alertService, never()).priceIncreased(any(), any(), any());
        verify(alertService).priceDecreased(any(), any(), any());
    }

    private TradeEvent makeEvent(double newPrice) {
        return TradeEvent.with(
          TradeDTO.builder()
            .stock("TSLA")
            .price(BigDecimal.valueOf(newPrice))
            .timestamp(new Date())
            .build()
        );
    }

    @Test
    void getCurrentPrices_cache() {
        when(stockService.getCurrentPrice(eq("TSLA")))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.getCurrentPrices(Arrays.asList("TSLA"));
        stockManagerService.getCurrentPrices(Arrays.asList("TSLA"));
        stockManagerService.getCurrentPrices(Arrays.asList("TSLA"));

        verify(stockService, atMostOnce()).getCurrentPrice(eq("TSLA"));
        verify(stockService, never()).getPreviousPrice(eq("TSLA"));
    }

    @Test
    void getPreviousPrices_cache() {
        when(stockService.getPreviousPrice(eq("TSLA")))
          .thenReturn(BigDecimal.valueOf(1000));

        stockManagerService.getPreviousPrices(Arrays.asList("TSLA"));
        stockManagerService.getPreviousPrices(Arrays.asList("TSLA"));
        stockManagerService.getPreviousPrices(Arrays.asList("TSLA"));

        verify(stockService, atMostOnce()).getPreviousPrice(eq("TSLA"));
        verify(stockService, never()).getCurrentPrice(eq("TSLA"));
    }

}