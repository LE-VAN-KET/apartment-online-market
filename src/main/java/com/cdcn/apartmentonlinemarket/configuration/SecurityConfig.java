package com.cdcn.apartmentonlinemarket.configuration;

import com.cdcn.apartmentonlinemarket.filter.AuthenticationTokenFilter;
import com.cdcn.apartmentonlinemarket.security.handler.AccessDeniedHandlerResolver;
import com.cdcn.apartmentonlinemarket.security.handler.UnauthorizedEntryPoint;
import com.cdcn.apartmentonlinemarket.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final AccessDeniedHandlerResolver accessDeniedHandlerResolver;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationTokenFilter authenticationJwtTokenFilter;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/actuator/health")
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/v1/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui/**");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(h -> h.contentSecurityPolicy("script-src 'self'"))
                .cors().and().csrf().disable()
            // enable cors and prevent CSRF
//            .cors().and().csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            // set session management stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // set unauthorized request exception handler
            .exceptionHandling(e -> e
                    .authenticationEntryPoint(this.unauthorizedEntryPoint)
                    .accessDeniedHandler(accessDeniedHandlerResolver)
            )
            .headers(h -> h
                    .frameOptions().sameOrigin()
            )
            // set permission on endpoints
            .authorizeHttpRequests( a -> a
                    .antMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
            )
            .logout(out -> out
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
            // Add jwt token filter to validate tokens with every request
            .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
