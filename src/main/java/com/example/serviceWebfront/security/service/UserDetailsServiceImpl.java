package com.example.serviceWebfront.security.service;



import com.example.serviceUser.User;
import com.example.serviceUser.UserResponseLogin;

import com.example.serviceWebfront.security.UserDetailsImpl;
import com.example.serviceWebfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserResponseLogin userResponse = userService.loginUser(name);
        return new UserDetailsImpl(userResponse);
    }
}
