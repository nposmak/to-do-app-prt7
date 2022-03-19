package com.nposmak.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String redirectUrl = request.getContextPath();
        String userName = userDetails.getUsername();//test
        System.out.println(userName);
        if (userDetails.hasRole("user")) {
            redirectUrl += "/users/";//test
            redirectUrl += userName;
            //redirectUrl+="/";
            //System.out.println(redirectUrl);
        }
        if (userDetails.hasRole("admin")) ;
        redirectUrl += "/";
        response.sendRedirect(redirectUrl);
    }
}
