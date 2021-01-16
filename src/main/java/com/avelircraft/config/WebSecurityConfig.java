package com.avelircraft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/index", "/index.html", "/",
                        "/guidmenu", "/guidmenu.html",
                        "/guid*", "/guid.html*",
                        "/donate", "/donate.html",
                        "/news*", "news.html*",
                        "/image*", "/user/icon*")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/lk",true)
                .failureUrl("/nolog");
    }
}
