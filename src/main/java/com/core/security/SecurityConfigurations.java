package com.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("https://front-proj-ku7s.onrender.com");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.addAllowedHeader("*");
                    return corsConfiguration;
                }))
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/address").permitAll()
                        .requestMatchers(HttpMethod.GET, "/address").permitAll()
                        .requestMatchers(HttpMethod.GET, "/offered-service").permitAll()
                        .requestMatchers(HttpMethod.GET, "/offered-service/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/service-provider").permitAll()
                        .requestMatchers(HttpMethod.GET, "/service-provider/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    // throws Exception {
    // return httpSecurity
    // .cors().and().csrf().disable()
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .authorizeHttpRequests(authorize -> authorize
    // .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
    // .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
    // .requestMatchers(HttpMethod.POST, "/address").permitAll()
    // .requestMatchers(HttpMethod.GET, "/address").permitAll()
    // .requestMatchers(HttpMethod.GET, "/offered-service").permitAll()
    // .requestMatchers(HttpMethod.GET, "/offered-service/**").permitAll()
    // .requestMatchers(HttpMethod.GET, "/service-provider").permitAll()
    // .requestMatchers(HttpMethod.GET, "/service-provider/**").permitAll()
    // .requestMatchers(HttpMethod.GET, "/**").permitAll()
    // // .requestMatchers(HttpMethod.POST, "/service-provider").hasRole("USER")
    // .anyRequest().authenticated())
    // .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    // .build();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://front-proj-ku7s.onrender.com"); // Frontend URL
        configuration.addAllowedMethod("*"); // Permitir todos os métodos HTTP
        configuration.addAllowedHeader("*"); // Permitir todos os cabeçalhos
        configuration.setAllowCredentials(true); // Permitir cookies e autenticação
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
