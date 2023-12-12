package com.deepak.mlm.security;

import com.deepak.mlm.security.jwt.JwtAuthenticationEntryPoint;
import com.deepak.mlm.security.jwt.JwtAuthenticationFilter;
import com.deepak.mlm.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.deepak.mlm.constants.Constants.BASE_URL;
import static com.deepak.mlm.constants.Constants.URLConstants.LOGIN;
import static com.deepak.mlm.constants.Constants.URLConstants.REGISTER_USER;

/**
 * This is Security configuration class, which is responsible for all types of configuration of security
 * @author Sudo-Deepak
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(BASE_URL + REGISTER_USER, BASE_URL + LOGIN)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint);

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors()
                .configurationSource(exchange -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of(
                            "http://localhost:3000"
                    ));

                    // Set your allowed origins here
                    corsConfig.setAllowedHeaders(List.of("*")); // Set your allowed headers here
                    corsConfig.setAllowedMethods(List.of("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")); // Set your allowed HTTP methods here
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setExposedHeaders(List.of("Authorization,Link,X-total-count,Content-Disposition"));
                    UrlBasedCorsConfigurationSource source =
                            new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfig);
                    return corsConfig;
                });

        return http.build();
    }
}
