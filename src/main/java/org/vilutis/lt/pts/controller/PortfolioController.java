package org.vilutis.lt.pts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.PortfolioDTO;
import org.vilutis.lt.pts.services.PortfolioService;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public PortfolioDTO getPortfolio() {
        // TODO resolve accountNumber from JWT header?
        return portfolioService.getAccountPortfolio("acc1");
    }

}