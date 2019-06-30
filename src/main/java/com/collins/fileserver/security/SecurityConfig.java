package com.collins.fileserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.collins.fileserver.service.FileUserDetailsService;
import com.collins.fileserver.user.MyAuthenticationSuccessHandler;
import com.collins.fileserver.user.MyLogoutSuccessHandler;

/**
 * Providing a custom WebSecurityConfigurerAdapter bean causes spring boot to
 * drop the default implementation (new in spring boot 2). So OAuth2 login has
 * to be reinstated here. See OAuth2WebSecurityConfiguration for default OAuth
 * security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	 private FileUserDetailsService userDetailsService;
	
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;
	
	 @Override
	    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		 /*
	        auth.inMemoryAuthentication()
	          .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
	          .and()
	          .withUser("user2").password(passwordEncoder().encode("password")).roles("USER")
	          .and()
	          .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
	          
	          */
		 //auth.userDetailsService(userDetailsService);
		 auth.authenticationProvider(authProvider());
		 
	    }
	 /*
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 */


	
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
                        "/register",
                        "/register/success",
                        "/css/**",
                        "/js/**",
                        "/favicon.ico"
                        ).permitAll()
                .antMatchers(
                		"/users",
                		"/users/**")
                	.authenticated()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/files")
                .failureUrl("/loginError")
                //loginProcessingUrl("/perform_login")
                //.loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
            .permitAll()
            	.and()
            .logout()
            	.logoutSuccessHandler(myLogoutSuccessHandler)
            	.logoutUrl("/logout")
            	.deleteCookies("JSESSIONID")
            	.logoutSuccessUrl("/login");
           // .and()
            //    .oauth2Login().loginPage("/oauth2/authorization/ping");
        // @formatter:on
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}