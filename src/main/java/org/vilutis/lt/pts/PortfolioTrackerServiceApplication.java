package org.vilutis.lt.pts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"org.vilutis.lt.pts"})
public class PortfolioTrackerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioTrackerServiceApplication.class, args);
    }

}