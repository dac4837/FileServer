package com.collins.fileserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Providing a custom WebSecurityConfigurerAdapter bean causes spring boot to
 * drop the default implementation (new in spring boot 2). So OAuth2 login has
 * to be reinstated here. See OAuth2WebSecurityConfiguration for default OAuth
 * security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Override
	    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	          .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
	          .and()
	          .withUser("user2").password(passwordEncoder().encode("password")).roles("USER")
	          .and()
	          .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
	    }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**",
            		"/pages/new",
            		"/files/*/new",
            		"/**/new"
            		).hasRole("ADMIN")
                .antMatchers(
                        "/actuator/health", 
                        "/login*",
                        "/css/**",
                        "/js/**",
                        "/favicon.ico"
                        ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //loginProcessingUrl("/perform_login")
                //.loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/files", true)
                .failureUrl("/loginError")
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
           // .and()
            //    .oauth2Login().loginPage("/oauth2/authorization/ping");
        // @formatter:on
    }
}