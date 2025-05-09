package vn.graybee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.graybee.custom.CustomAccessDenied;
import vn.graybee.custom.CustomAuthenticationEndpoint;
import vn.graybee.filters.JwtFilter;
import vn.graybee.serviceImps.users.UserDetailServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class WebConfig {

    private final JwtFilter jwtFilter;

    private final ApiConfig apiConfig;

    private final UserDetailServiceImpl userDetailService;

    private final CustomAccessDenied customAccessDenied;

    private final CustomAuthenticationEndpoint customAuthenticationEndpoint;

    public WebConfig(JwtFilter jwtFilter, ApiConfig apiConfig, UserDetailServiceImpl userDetailService, CustomAccessDenied customAccessDenied, CustomAuthenticationEndpoint customAuthenticationEndpoint) {
        this.jwtFilter = jwtFilter;
        this.apiConfig = apiConfig;
        this.userDetailService = userDetailService;
        this.customAccessDenied = customAccessDenied;
        this.customAuthenticationEndpoint = customAuthenticationEndpoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(
                        cors -> cors.configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        endpoint -> endpoint
                                .requestMatchers(apiConfig.getAdmin() + "/**").permitAll()
                                .requestMatchers("/api/v1/account/**").authenticated()
                                .requestMatchers("/api/v1/public/**").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()

                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailService)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(customAuthenticationEndpoint)
                        .accessDeniedHandler(customAccessDenied))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of(
                "http://localhost:3100",
                "http://localhost:3000"
        ));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        cors.setExposedHeaders(Collections.singletonList("Authorization"));
        cors.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
