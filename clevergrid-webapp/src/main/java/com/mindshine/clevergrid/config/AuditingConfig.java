package com.mindshine.clevergrid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuditingConfig {
	@Bean
	public AuditorAware<String> createAuditorProvider() {
		return new SecurityAuditor();
	}

	public static class SecurityAuditor implements AuditorAware<String> {
		@Override
		public String getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			return username;
		}
	}
}
