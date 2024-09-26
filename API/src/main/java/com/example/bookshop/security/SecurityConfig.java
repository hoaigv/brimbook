package com.example.bookshop.security;

import com.example.bookshop.authentication.config.CustomJwtDecoder;
import com.example.bookshop.authentication.config.JwtAuthenticationEntryPoint;
import com.example.bookshop.authentication.config.RestAccessDeniedHandler;
import com.example.bookshop.utils.enums.Authentication;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    String[] PUBLIC_POST_ENDPOINT = {"/api/users/sign-up","/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh"};
    String[] PRIVATE_PUT_ENDPOINT = {"/api/users/update","/api/users/update/image"};
    String[] PRIVATE_ENDPOINT= {"/api/admin/**"};
    String[] PRIVATE_GET_ENDPOINT = {"/api/users/me"};
    CustomJwtDecoder jwtDecoder;
    RestAccessDeniedHandler restAccessDeniedHandler;
     static  String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };


    public SecurityConfig(CustomJwtDecoder jwtDecoder, RestAccessDeniedHandler restAccessDeniedHandler) {
        this.jwtDecoder = jwtDecoder;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors(Customizer.withDefaults()) // Thêm dòng này
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request ->
                request
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINT)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET,PRIVATE_GET_ENDPOINT)
                    .hasAnyAuthority(Authentication.ROLE_USER.name(),Authentication.ROLE_ADMIN.name())
                    .requestMatchers(HttpMethod.PUT,PRIVATE_PUT_ENDPOINT)
                    .hasAnyAuthority(Authentication.ROLE_USER.name(),Authentication.ROLE_ADMIN.name())
                    .requestMatchers(PRIVATE_ENDPOINT)
                    .hasAuthority(Authentication.ROLE_ADMIN.name())
                    .requestMatchers(SWAGGER_WHITELIST)
                .permitAll()
                        .anyRequest()
                        .authenticated())
            .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .exceptionHandling(handler -> handler.accessDeniedHandler(restAccessDeniedHandler))
        ;

        httpSecurity.csrf(AbstractHttpConfigurer::disable);


        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Cân nhắc giới hạn nguồn gốc cho môi trường sản xuất
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
