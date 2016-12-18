package com.mindshine.clevergrid.config;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Profile("Basic")
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class BasicApplicationSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(BasicApplicationSecurity.class);

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		LOG.debug("Configuring form-based authentication");
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf().disable();
    }
 
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOG.debug("Configuring in-memory authentication");
		auth.inMemoryAuthentication().withUser("admin").password("admin")
				.roles("ADMIN").and().withUser("user").password("user")
				.roles("USER");
	}
	
}