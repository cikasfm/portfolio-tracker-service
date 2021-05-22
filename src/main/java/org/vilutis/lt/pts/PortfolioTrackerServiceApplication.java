package org.vilutis.lt.pts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"org.vilutis.lt.pts"})
public class PortfolioTrackerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioTrackerServiceApplication.class, args);
    }

}