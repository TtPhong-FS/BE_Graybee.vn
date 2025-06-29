package vn.graybee.auth.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import vn.graybee.auth.config.OriginProperties;
import vn.graybee.auth.custom.CustomAccessDenied;
import vn.graybee.auth.custom.CustomAuthenticationEndpoint;
import vn.graybee.auth.filter.JwtFilter;
import vn.graybee.common.config.ApiProperties;
import vn.graybee.modules.account.security.UserDetailService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OriginProperties originProperties;

    private final JwtFilter jwtFilter;

    private final ApiProperties apiProperties;

    private final UserDetailService userDetailService;

    private final CustomAccessDenied customAccessDenied;

    private final CustomAuthenticationEndpoint customAuthenticationEndpoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(
                        cors -> cors.configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(customAuthenticationEndpoint)
                        .accessDeniedHandler(customAccessDenied))
                .authorizeHttpRequests(
                        endpoint -> endpoint
                                .requestMatchers(apiProperties.getAdminApi().getBase() + "/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(apiProperties.getPrivateApi().getAccount() + "/**").authenticated()
                                .requestMatchers(apiProperties.getPublicApi().getBase() + "/**").permitAll()
                                .anyRequest().permitAll()
                )
                .userDetailsService(userDetailService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of(
                originProperties.getTechstore(),
                originProperties.getTechstoreDashboard()
        ));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        cors.setExposedHeaders(Collections.singletonList("Authorization"));
        cors.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", cors);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

}
