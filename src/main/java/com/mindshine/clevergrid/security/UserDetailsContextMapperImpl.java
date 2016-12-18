package com.mindshine.clevergrid.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class UserDetailsContextMapperImpl implements UserDetailsContextMapper, Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DATA_SERVICES_ADMIN_AUTHORITY = "Data Services Admin";

	private String adminGroup;
	
	private String adminUser;

	
	public UserDetailsContextMapperImpl(String adminGroup, String adminUser) {
		super();
		this.adminGroup = adminGroup;
		this.adminUser = adminUser;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authorities) {
		List<GrantedAuthority> mappedAuthorities = new ArrayList<>();
		if ((StringUtils.isNotEmpty(adminUser) && adminUser.equalsIgnoreCase(username))
				|| (StringUtils.isNotEmpty(adminGroup) && authorities.stream().anyMatch(a -> a.getAuthority().equals(adminGroup)))) {
			mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		UserDetails userDetails = new UserDetails(username, "<abc123>", true, true, true, true, authorities);
		userDetails.setDisplayName(username);
		userDetails.setGroups(authorities.stream().map(a -> a.getAuthority()).toArray(size -> new String[size]));

		return userDetails;
	}

	@Override
	public void mapUserToContext(org.springframework.security.core.userdetails.UserDetails user,
			DirContextAdapter ctx) {
	}

}
