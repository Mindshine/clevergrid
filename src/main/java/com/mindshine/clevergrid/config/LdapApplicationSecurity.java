package com.mindshine.clevergrid.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.mindshine.clevergrid.security.CsrfTokenCookieWriter;
import com.mindshine.clevergrid.security.UserDetailsContextMapperImpl;

@Configuration
@Profile("LDAP")
@EnableWebSecurity
public class LdapApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
	private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

	@Value("${ldap.domain}")
	private String domain;

	@Value("${ldap.url}")
	private String url;

	@Value("${ldap.rootDn}")
	private String rootDn;

	@Value("${http.port}")
	private int httpPort;

	@Value("${https.port}")
	private int httpsPort;

	@Value("${security.admin.group}")
	private String adminGroup;
	
	@Value("${security.admin.user}")
	private String adminUser;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfTokenRepository.setHeaderName(LdapApplicationSecurity.CSRF_HEADER_NAME);
		http.csrf().csrfTokenRepository(csrfTokenRepository);

		http.authorizeRequests().antMatchers("/js/**", "/css/**", "/webjars/**", "/login/**").permitAll().anyRequest()
				.fullyAuthenticated();

		http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login?error");

		http.httpBasic();

		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");

		http.headers().defaultsDisabled().contentTypeOptions().and().xssProtection().and().httpStrictTransportSecurity()
				.and().addHeaderWriter(
						new CsrfTokenCookieWriter(csrfTokenRepository, LdapApplicationSecurity.CSRF_COOKIE_NAME));
		/*
		 * Use HTTPs for ALL requests
		 */
		http.requiresChannel().anyRequest().requiresSecure();
		http.portMapper().http(httpPort).mapsTo(httpsPort);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
				.userDetailsService(userDetailsService());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
	}

	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() {
		return new UserDetailsContextMapperImpl(adminGroup, adminUser);
	}

	@Bean
	public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(domain, url,
				rootDn);
		provider.setConvertSubErrorCodesToExceptions(true);
		provider.setUseAuthenticationRequestCredentials(true);
		provider.setUserDetailsContextMapper(userDetailsContextMapper());
		return provider;
	}
}
