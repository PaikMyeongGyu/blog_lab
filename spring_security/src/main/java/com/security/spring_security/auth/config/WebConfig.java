package com.security.spring_security.auth.config;

import com.security.spring_security.auth.resolver.AdminArgumentResolver;
import com.security.spring_security.auth.resolver.GuestArgumentResolver;
import com.security.spring_security.auth.resolver.ReIssueArgumentResolver;
import com.security.spring_security.auth.resolver.UserArgumentResolver;
import com.security.spring_security.auth.service.AuthService;
import com.security.spring_security.auth.util.TokenUtils;
import com.security.spring_security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenUtils tokenUtils;
    private final AuthService authService;

    @Bean
    public GuestArgumentResolver guestArgumentResolver() {
        return new GuestArgumentResolver(tokenUtils);
    }

    @Bean
    public UserArgumentResolver userArgumentResolver() {
        return new UserArgumentResolver(tokenUtils);
    }

    @Bean
    public ReIssueArgumentResolver reIssueArgumentResolver() {
        return new ReIssueArgumentResolver(tokenUtils, authService);
    }

    @Bean
    public AdminArgumentResolver adminArgumentResolver() {
        return new AdminArgumentResolver(tokenUtils);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver());
        argumentResolvers.add(guestArgumentResolver());
        argumentResolvers.add(reIssueArgumentResolver());
        argumentResolvers.add(adminArgumentResolver());
    }
}
