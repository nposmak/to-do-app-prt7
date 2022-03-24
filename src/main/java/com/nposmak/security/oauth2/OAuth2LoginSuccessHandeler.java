package com.nposmak.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class OAuth2LoginSuccessHandeler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    private SaveUserOAuth2Service saveByOAuth2Service;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String oA2username = oAuth2User.getName(); 
		System.out.println(oAuth2User);
		saveByOAuth2Service.processOAuth2PostLogin(oAuth2User);
		response.sendRedirect("/users/" + oA2username);
	}
	
	
}
