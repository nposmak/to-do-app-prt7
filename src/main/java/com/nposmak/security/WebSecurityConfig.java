package com.nposmak.security;


import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nposmak.security.oauth2.CustomOAuth2User;
import com.nposmak.security.oauth2.CustomOAuth2UserService;
import com.nposmak.security.oauth2.OAuth2LoginSuccessHandeler;
import com.nposmak.security.oauth2.SaveUserOAuth2Service;

//@EnableOAuth2Client
//@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//public class WebSecurityConfig {


    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    
    @Autowired
    private OAuth2LoginSuccessHandeler aouth2LoginSuccessHandeler;

    @Autowired
    private CustomOAuth2UserService oAuth2Service;
    
    //@Autowired
    //private SaveUserOAuth2Service saveByOAuth2Service;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests()
//                .antMatchers("/", "/login/**", "/login-myapp**", "/register", "/static/**",
//                        "/process_register").access("permitAll()")
//                .antMatchers("/users/*").access("isAuthenticated()")
//                .antMatchers("/admin**", "/users**").access("hasRole('admin')")
//                    .and()
//                    .formLogin()
//                    .loginPage("/login-myapp")
//                    .usernameParameter("email")
//                    .successHandler(loginSuccessHandler)
//                        .and()
//                        .oauth2Login()
//                        .loginPage("/login-oauth2")
//
//                            .and()
//                            .logout()
//                            .logoutSuccessUrl("/").permitAll()
//                                .deleteCookies("JSESSIONID")
//                                .and().build();
//    }




/** You can use code below instead of SecurityFilterChain, but in this case Security Configuration Class
 *  should  extend WebSecurityConfigurerAdapter*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
               http
                .authorizeRequests()
                .antMatchers("/", "/login**", "/login-myapp**", "/register", "/static/**",
                        "/process_register").access("permitAll()")
                .antMatchers("/users/*").access("isAuthenticated()")
                .antMatchers("/admin**", "/users**").access("hasRole('admin')")
                    .and()
                    .formLogin()
                    .loginPage("/login-myapp")
                    .usernameParameter("email")
                    .successHandler(loginSuccessHandler)
                        .and()
                        .oauth2Login()
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .userService(oAuth2Service)
                        .and()
                        .successHandler(aouth2LoginSuccessHandeler)
                        //.defaultSuccessUrl("/")
//                        .successHandler(new AuthenticationSuccessHandler() {
//							
//							@Override
//							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//									Authentication authentication) throws IOException, ServletException {
//								// TODO Auto-generated method stub
//								CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//								String oA2username = oAuth2User.getName(); 
//								System.out.println(oAuth2User);
//								//saveByOAuth2Service.processOAuth2PostLogin(oAuth2User.getName());
//								saveByOAuth2Service.processOAuth2PostLogin(oAuth2User);
//								response.sendRedirect("/users/" + oA2username);
//								
//							}
//						})

                            .and()
                            .logout()
                            .logoutSuccessUrl("/").permitAll()
                            .deleteCookies("JSESSIONID");

    }


}
