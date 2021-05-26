package org.vilutis.lt.pts.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.exception.UserNotAuthorizedException;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Optional.ofNullable(principal)
          .map(p -> p.getAttribute("name"))
          .map(userName -> Collections.singletonMap("name", userName))
          .orElseThrow(() -> new UserNotAuthorizedException());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String handleException(UserNotAuthorizedException e) {
        return "User not authorized";
    }

}
