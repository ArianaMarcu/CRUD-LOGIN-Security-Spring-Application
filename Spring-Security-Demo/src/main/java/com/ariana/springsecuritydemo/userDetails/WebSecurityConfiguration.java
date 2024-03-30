package com.ariana.springsecuritydemo.userDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                 = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/home")
                .hasAuthority("USER")
                /*.antMatchers("/home/getAllOrderss")
                .hasAuthority("USER")
                .antMatchers("/home/addOrder")
                .hasAuthority("USER")
                .antMatchers("/home/order{ID_Comanda}")
                .hasAuthority("USER")*/
                .antMatchers("/admin")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/getAll")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/add")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/{id}")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/getAllMenus")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/addMenu")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/menu{id}")
                .hasAuthority("ADMIN")
                .antMatchers("/admin/{startDate}/{endDate}")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        http.cors().and().csrf().disable();
    }
}
