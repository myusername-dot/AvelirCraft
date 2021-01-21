package com.avelircraft.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                //.requiresChannel().anyRequest().requiresSecure()
                //.and()
                .authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/index", "/index.html", "/",
                        "/guidmenu", "/guidmenu.html",
                        "/guid*", "/guid.html*",
                        "/donate", "/donate.html",
                        "/news*", "/news.html*",
                        "/upload/image*", "/upload/user/icon*")
                .permitAll()
                .antMatchers("/error", "/error.html",
                        "/404", "404.html")
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
