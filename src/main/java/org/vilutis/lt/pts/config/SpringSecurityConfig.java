package org.vilutis.lt.pts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests(a -> a
            .antMatchers("/", "/error", "/webjars/**").permitAll()
            // forces to authorize before accessing "/api/"
            .antMatchers("/api/**").authenticated()
          )
          .logout(l -> l
            .logoutSuccessUrl("/")
            .logoutUrl("/logout")
            .permitAll()
          )
          .csrf(c -> c
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
          )
          .oauth2Login(o -> o
            .failureHandler((request, response, exception) -> {
                request.getSession().setAttribute("error.message", exception.getMessage());
                response.sendRedirect("/");
            })
          )
          .exceptionHandling(e -> e
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
          )
          .oauth2Login();
    }

}