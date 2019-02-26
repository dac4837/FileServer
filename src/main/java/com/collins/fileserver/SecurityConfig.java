package com.collins.fileserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Providing a custom WebSecurityConfigurerAdapter bean causes spring boot to
 * drop the default implementation (new in spring boot 2). So OAuth2 login has
 * to be reinstated here. See OAuth2WebSecurityConfiguration for default OAuth
 * security config.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(
                        "/actuator/health", 
                        "/login*")
                        .permitAll()
                .anyRequest().authenticated();
           // .and()
            //    .oauth2Login().loginPage("/oauth2/authorization/ping");
        // @formatter:on
    }
}