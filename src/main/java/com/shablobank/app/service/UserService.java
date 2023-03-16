package com.shablobank.app.service;

import com.shablobank.app.models.User;
import com.shablobank.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private IUserRepository repository;


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User authenticate(String email, String password) {
        User user = null;
        user = repository.findUserByEmail(email);

        if (user == null) {
            return null;
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }
}
