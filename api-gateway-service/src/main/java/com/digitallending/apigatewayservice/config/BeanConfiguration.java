package com.digitallending.apigatewayservice.config;

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class BeanConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterAfter(createPolicyEnforcerFilter(), BearerTokenAuthenticationFilter.class);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                                                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
        return  new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
            @Override
            public PolicyEnforcerConfig resolve(HttpRequest httpRequest) {
                try {
                    return JsonSerialization.readValue(getClass().getResourceAsStream("/policy-enforcer.json"), PolicyEnforcerConfig.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
        cv.setAuthorityPrefix("");
        cv.setAuthoritiesClaimName("roles");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(cv);
        return jwtAuthenticationConverter;
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler()
    {
        DefaultMethodSecurityExpressionHandler hanlder = new DefaultMethodSecurityExpressionHandler();
        hanlder.setDefaultRolePrefix("");
        return hanlder;
    }
}
