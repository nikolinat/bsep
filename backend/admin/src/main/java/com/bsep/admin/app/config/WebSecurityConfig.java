package com.bsep.admin.app.config;

import com.bsep.admin.app.security.auth.RestAutheticationEntryPoint;
import com.bsep.admin.app.security.auth.TokenAuthenticationFilter;
import com.bsep.admin.app.service.contract.IInvalidTokenService;
import com.bsep.admin.app.service.implementation.UserDetailServiceCustom;
import com.bsep.admin.app.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAutheticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Lazy
    private UserDetailServiceCustom jwtUserDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private IInvalidTokenService invalidTokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/login").permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService, invalidTokenService),
                                BasicAuthenticationFilter.class);
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/v1/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET,"/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
