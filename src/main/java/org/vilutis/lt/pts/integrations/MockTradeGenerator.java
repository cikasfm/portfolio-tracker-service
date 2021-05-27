package org.vilutis.lt.pts.integrations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.vilutis.lt.pts.dto.TradeDTO;
import org.vilutis.lt.pts.events.TradeEvent;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.services.api.StockService;

@Component
@Slf4j
@ConditionalOnMissingBean(AlpacaStreamingClient.class)
public class MockTradeGenerator implements InitializingBean, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicBoolean run = new AtomicBoolean(false);

    private final StockService stockService;

    @Autowired
    public MockTradeGenerator(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        run.set(true);
        final LinkedList<Stock> activeStocks = new LinkedList<>(stockService.getActiveStocks());

        new Thread(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            Iterator<Stock> iterator = activeStocks.iterator();
            log.info("--- Generator started ---");
            while (run.get()) {
                try {
                    String stock = iterator.next().getStock();
                    if (!iterator.hasNext()) {
                        // restart iterator
                        iterator = activeStocks.iterator();
                    }

                    // range between -0.5% and +0.5%
                    BigDecimal change = BigDecimal.valueOf((random.nextDouble() * 10 - 5) / 100);

                    BigDecimal newPrice = stockService.getCurrentPrice(stock)
                      .multiply(BigDecimal.ONE.add(change))
                      .setScale(4, RoundingMode.HALF_UP);

                    executorService.submit(() -> publisher.publishEvent(TradeEvent.with(
                      TradeDTO.builder()
                        .stock(stock)
                        .price(newPrice)
                        .quantity(BigDecimal.valueOf(random.nextInt(100)))
                        .timestamp(new Date())
                        .build()
                    )));
                    Thread.sleep(random.nextInt(1_000));
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
            log.info("--- Generator stopped ---");
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> run.set(false)));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.shutdown()));
    }
}
