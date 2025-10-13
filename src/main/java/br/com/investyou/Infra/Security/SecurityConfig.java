package br.com.investyou.Infra.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter filter;

    ExpressionParser expressionParser = new SpelExpressionParser();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sm) -> {
                    sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests((req) -> {
//                    req.requestMatchers(HttpMethod.PUT, "/auth/user").permitAll();
//                    req.requestMatchers(HttpMethod.PUT, "/auth/admin").permitAll();
//                    req.requestMatchers(HttpMethod.POST, "/user").permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/product").permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/product/{id}").permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/auth/logged").permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/auth/logout").permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/admin/current").permitAll();
                    req.anyRequest().permitAll();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
