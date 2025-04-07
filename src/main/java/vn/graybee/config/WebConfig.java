package vn.graybee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.graybee.serviceImps.users.UserDetailServiceImpl;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class WebConfig {

    private final UserDetailServiceImpl userDetailService;

    public WebConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        endpoint ->
                                endpoint.requestMatchers("/api/v1/public/**").permitAll()
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/api/v1/admin/**").permitAll()
                                        .requestMatchers("/api/v1/account/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailService)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
