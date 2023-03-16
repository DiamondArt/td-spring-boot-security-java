package com.shablobank.app.config.jwt;

import javax.servlet.http.HttpServletRequest;

import com.shablobank.app.models.User;
import com.shablobank.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
@Service
public class JwtUserDetailsService implements  UserDetailsService{

    private IUserRepository userRepository;
    private HttpServletRequest servletRequest;
    @Autowired
    public JwtUserDetailsService(IUserRepository userRepository, HttpServletRequest servletRequest) {
        this.userRepository = userRepository;
        this.servletRequest = servletRequest;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            getLogger().warn("User not found with username : [{}]", email);
            throw new UsernameNotFoundException(String.format("User not found with username: [%s]", email));
        }
        return JwtUserDetailsImpl.build(user, servletRequest);
    }
    private org.slf4j.Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass().getSimpleName());
    }
}
