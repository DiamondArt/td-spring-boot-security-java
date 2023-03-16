package com.shablobank.app.config;

import com.shablobank.app.config.jwt.JwtAuthenticationEntryPoint;
import com.shablobank.app.config.jwt.JwtRequestFilter;
import com.shablobank.app.config.jwt.JwtUserDetailsService;
import com.shablobank.app.models.ERole;
import com.shablobank.app.models.Role;
import com.shablobank.app.models.User;
import com.shablobank.app.repository.IRoleRepository;
import com.shablobank.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

import static org.apache.logging.log4j.LogManager.getLogger;

@Configuration
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserRepository userRepository;

    @Bean
    public static BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and().
                csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/v1/api/rest/auth/signin")
                .permitAll()
                .anyRequest() // all other requests need to be authenticated
                .authenticated()
                .and()
                // make sure we're using a stateless session; the session will not be used for
                // store user state.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate tokens on each request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

       initData();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager to know where to load from user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
    }


    @Async
    @Description("")
    void initData() {
        getLogger().info("[START APPLICATION DATA INITIALISATION]");

        createDefaultRole();
        createDefaultUser();
    }

    private void createDefaultUser() {
        try {
            Optional<Role> roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
            User user = User.superAdmin(roleAdmin, bcryptPasswordEncoder());
            if (!userRepository.existsByEmail(user.getEmail())) {
                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().error(e.getMessage());
        }
    }

    private void createDefaultRole() {
        try {
                roleRepository.save(Role.roleAdmin());

                roleRepository.save(Role.roleModerator());

        } catch (Exception e) {
            e.printStackTrace();
            getLogger().error(e.getMessage());
        }
    }
}
