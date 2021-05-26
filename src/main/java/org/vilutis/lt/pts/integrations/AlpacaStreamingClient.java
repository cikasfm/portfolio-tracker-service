package org.vilutis.lt.pts.integrations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.jacobpeterson.abstracts.websocket.exception.WebsocketException;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.websocket.marketdata.listener.MarketDataListener;
import net.jacobpeterson.alpaca.websocket.marketdata.listener.MarketDataListenerAdapter;
import net.jacobpeterson.alpaca.websocket.marketdata.message.MarketDataMessageType;
import net.jacobpeterson.domain.alpaca.marketdata.realtime.MarketDataMessage;
import net.jacobpeterson.domain.alpaca.marketdata.realtime.bar.BarMessage;
import net.jacobpeterson.domain.alpaca.marketdata.realtime.quote.QuoteMessage;
import net.jacobpeterson.domain.alpaca.marketdata.realtime.trade.TradeMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.vilutis.lt.pts.dto.TradeDTO;
import org.vilutis.lt.pts.events.TradeEvent;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.services.api.StockService;

@Component
@Slf4j
@ConditionalOnProperty({"alpaca.api.key", "alpaca.api.secret"})
public class AlpacaStreamingClient implements InitializingBean, ApplicationEventPublisherAware {

    private final AlpacaAPI alpacaAPI;
    private final StockService stockService;
    private ApplicationEventPublisher publisher;

    @Autowired
    public AlpacaStreamingClient(StockService stockService,
      @Value("${alpaca.api.key}") String alpacaApiKey,
      @Value("${alpaca.api.secret}") String alpacaApiSecret
    ) {
        this.stockService = stockService;
        this.alpacaAPI = new AlpacaAPI(alpacaApiKey, alpacaApiSecret);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // Listen to TSLA quotes, trades, and minute bars and print their messages out
            List<Stock> activeStocks = stockService.getActiveStocks();
            HashSet<String> symbols = new HashSet<String>() {{
                activeStocks.forEach(s -> add(s.getStock()));
            }};

            MarketDataListener tradeListener = new MarketDataListenerAdapter(
              symbols,
              MarketDataMessageType.TRADE,
              MarketDataMessageType.BAR
            ) {
                @Override
                public void onStreamUpdate(MarketDataMessageType streamMessageType,
                  MarketDataMessage streamMessage) {
                    switch (streamMessageType) {
                        case TRADE:
                            TradeMessage tradeMessage = (TradeMessage) streamMessage;
                            log.trace(String.format("Trade[%s] Price=%.2f Size=%d Time=%s",
                              tradeMessage.getSymbol(),
                              tradeMessage.getPrice(), tradeMessage.getSize(),
                              tradeMessage.getTimestamp()));
                            publisher.publishEvent(TradeEvent.with(mapToTradeDto(tradeMessage)));
                            break;
                        case QUOTE:
                            QuoteMessage quoteMessage = (QuoteMessage) streamMessage;
                            log.trace(String.format(
                              "Quote[%s Ask Price=%.2f Ask Size=%d Bid Price=%.2f Bid Size=%d Time=%s",
                              quoteMessage.getSymbol(),
                              quoteMessage.getAskPrice(),
                              quoteMessage.getAskSize(),
                              quoteMessage.getBidPrice(),
                              quoteMessage.getBidSize(),
                              quoteMessage.getTimestamp()));
                            break;
                        case BAR:
                            BarMessage barMessage = (BarMessage) streamMessage;
                            log.trace(String.format("Bar[%s] O=%.2f H=%.2f L=%.2f C=%.2f Time=%s",
                              barMessage.getSymbol(),
                              barMessage.getOpen(),
                              barMessage.getHigh(),
                              barMessage.getLow(),
                              barMessage.getClose(),
                              barMessage.getTimestamp()));
                            break;
                    }
                }
            };

            // Add the 'MarketDataListener'
            // Note that when the first 'MarketDataListener' is added, the Websocket
            // connection is created.
            alpacaAPI.addMarketDataStreamListener(tradeListener);

            // Remove the 'MarketDataListener'
            // Note that when the last 'MarketDataListener' is removed, the Websocket
            // connection is closed.
            Thread hook = new Thread(() -> {
                try {
                    alpacaAPI.removeMarketDataStreamListener(tradeListener);
                } catch (WebsocketException e) {
                    log.error(e.getMessage(), e);
                }
            });
            Runtime.getRuntime().addShutdownHook(hook);
        } catch (WebsocketException exception) {
            exception.printStackTrace();
        }
    }

    private TradeDTO mapToTradeDto(TradeMessage tradeMessage) {
        return TradeDTO.builder()
          .stock(tradeMessage.getSymbol())
          .price(BigDecimal.valueOf(tradeMessage.getPrice()))
          .quantity(BigDecimal.valueOf(tradeMessage.getSize()))
          .timestamp(Date.from(tradeMessage.getTimestamp().toInstant()))
          .build();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
