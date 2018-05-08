package com.bazalytskyi.coursework.configuration;

import com.bazalytskyi.coursework.services.CustomUserDetailsService;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    SecurityProperties security;


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/",true)
                .loginProcessingUrl("/login").and()
                .csrf().disable();
        //.invalidateHttpSession(true)
        //.deleteCookies("JSESSIONID");

    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService();
    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(encoder());
//        return authenticationProvider;
//    }




}

