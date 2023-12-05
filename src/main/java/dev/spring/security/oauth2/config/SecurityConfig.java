package dev.spring.security.oauth2.config;

import dev.spring.security.oauth2.security.filter.GlobalLoggerFilter;
import dev.spring.security.oauth2.security.filter.JwtAuthenticationFilter;
import dev.spring.security.oauth2.security.filter.JwtExceptionFilter;
import dev.spring.security.oauth2.security.handler.jwt.JwtAccessDeniedHandler;
import dev.spring.security.oauth2.security.handler.jwt.JwtAuthEntryPoint;
import dev.spring.security.oauth2.security.handler.signin.DefaultFailureHandler;
import dev.spring.security.oauth2.security.handler.signin.DefaultSuccessHandler;
import dev.spring.security.oauth2.security.handler.signout.CustomLogoutHandler;
import dev.spring.security.oauth2.security.handler.signout.CustomLogoutSuccessHandler;
import dev.spring.security.oauth2.security.service.CustomUserDetailsService;
import dev.spring.security.oauth2.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .anyRequest().permitAll()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/auth/sign-in")
                                .usernameParameter("social_id")
                                .passwordParameter("password")
                                .successHandler(defaultSuccessHandler)
                                .failureHandler(defaultFailureHandler)
                )
                .logout((logout) ->
                        logout
                                .logoutUrl("/auth/sign-out")
                                .addLogoutHandler(customLogoutHandler)
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                .exceptionHandling((configurer) ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailsService),
                        LogoutFilter.class
                )
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class
                )
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class
                )
                .getOrBuild();
    }
}
