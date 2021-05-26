package org.vilutis.lt.pts.controller;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.PortfolioDTO;
import org.vilutis.lt.pts.exception.AccountNotFoundException;
import org.vilutis.lt.pts.exception.UserNotAuthorizedException;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.services.api.AccountService;
import org.vilutis.lt.pts.services.api.PortfolioService;

@RestController
@RequestMapping("/api/portfolio")
@Slf4j
public class PortfolioController {

    private final AccountService accountService;
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(AccountService accountService,
      PortfolioService portfolioService) {
        this.accountService = accountService;
        this.portfolioService = portfolioService;
    }

    @GetMapping
    @Secured("ROLE_USER")
    public PortfolioDTO getPortfolio(@AuthenticationPrincipal OAuth2User principal) {
        String accountName = Optional.ofNullable(principal)
          .map(p -> (String) p.getAttribute("login"))
          .orElseThrow(() -> new UserNotAuthorizedException());

        if(log.isTraceEnabled()){
            StringBuilder sb = new StringBuilder("Principal attributes: ");
            principal.getAttributes()
              .forEach((key, value) -> sb.append(key + "=" + value + " "));
            log.trace(sb.toString());
        }

        Account account = accountService.getAccountByName(accountName)
          .orElseThrow(() -> new AccountNotFoundException(accountName));

        return portfolioService.getAccountPortfolio(account.getAccountNumber());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String handleException(UserNotAuthorizedException e) {
        return "User not authorized";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleException(AccountNotFoundException e) {
        return e.getMessage();
    }

}