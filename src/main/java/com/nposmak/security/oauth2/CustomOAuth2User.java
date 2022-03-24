package com.nposmak.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User oAuth2User;

	private String username;

	public CustomOAuth2User(OAuth2User oAuth2User) {
		this.oAuth2User = oAuth2User;
	}

	public String getUsername() {
		return oAuth2User.getAttribute("email");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oAuth2User.getAttribute("email");
	}

	public String getEmail() {
		return oAuth2User.<String>getAttribute("name");
	}

}
