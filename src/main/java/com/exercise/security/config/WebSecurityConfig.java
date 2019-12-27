package com.exercise.security.config;


import com.exercise.quotes.utils.ApiQuotesConstants;
import com.exercise.security.config.services.CustomUserDetailsService;
import com.exercise.security.config.services.TokenAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.exercise.security.ApiUsersConstants.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



   private CustomUserDetailsService userService;
   private TokenAuthService tokenAuthService;

   @Autowired
    public WebSecurityConfig(CustomUserDetailsService userService, TokenAuthService tokenAuthService) {
        this.userService = userService;
        this.tokenAuthService = tokenAuthService;
    }

    public WebSecurityConfig(boolean disableDefaults, CustomUserDetailsService userService, TokenAuthService tokenAuthService) {
        super(disableDefaults);
        this.userService = userService;
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(new StatelessAuthFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll();
//                .antMatchers(SIGNIN).permitAll()
//                .antMatchers(SIGNUP).permitAll()
//                .antMatchers(DELETE_USER).hasAnyRole("ADMIN")
//                .antMatchers(REFRESH_TOKEN).hasAnyRole("USER","ADMIN")
//                .antMatchers(ApiQuotesConstants.GET_ALL_QUOTES).authenticated()
//                .antMatchers(ApiQuotesConstants.GET_QUOTE).authenticated()
//                .antMatchers(ApiQuotesConstants.ADD_QUOTE).hasAnyRole("USER", "ADMIN")
//                .antMatchers(ApiQuotesConstants.UPDATE_QUOTE).hasAnyRole("USER", "ADMIN")
//                .antMatchers(ApiQuotesConstants.DELETE_QUOTE).hasAnyRole("USER", "ADMIN")
//                .antMatchers(ApiQuotesConstants.GET_ALL_ITEMS).authenticated()
//                .antMatchers(ApiQuotesConstants.GET_ITEM).authenticated()
//                .antMatchers(ApiQuotesConstants.ADD_ITEM).hasAnyRole("USER", "ADMIN")
//                .antMatchers(ApiQuotesConstants.UPDATE_ITEM).hasAnyRole("USER", "ADMIN")
//                .antMatchers(ApiQuotesConstants.DELETE_ITEM).hasAnyRole("USER", "ADMIN");

    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(H2_CONSOLE);

    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

}
