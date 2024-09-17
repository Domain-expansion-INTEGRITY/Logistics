package com.domain_expansion.integrity.slack.common.security;

import com.domain_expansion.integrity.slack.common.jwt.JwtUtils;
import com.domain_expansion.integrity.slack.presentation.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtils);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
            (auth) ->
                auth
                    .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations())
                    .permitAll() // resources 접근 허용 설정
//                    .requestMatchers(
//                        "/api/v1/ais/hub-delivery/slack", "/api/v1/ais/company-delivery/slack",
//                        "/api/v1/ais/company-delivery/sequence"
//                    ) // 내부 서비스에서 ai 호출
//                    .permitAll()
                    .anyRequest()
                    .authenticated()
        );

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
