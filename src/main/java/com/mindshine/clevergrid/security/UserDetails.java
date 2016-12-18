package com.mindshine.clevergrid.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetails extends User {

	String displayName;
	String[] groups;
	
	private static final long serialVersionUID = 1L;

	public UserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

	public UserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

}
