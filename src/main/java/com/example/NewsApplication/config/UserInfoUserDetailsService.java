package com.example.NewsApplication.config;

import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfoByMobileNo = repository.findByMobileNo(username);
        if (userInfoByMobileNo.isPresent()) {
            return new UserInfoUserDetails(userInfoByMobileNo.get());
        }
        Optional<User> userInfoByEmail = repository.findByEmail(username);
        return userInfoByEmail.map(userInfo -> new UserInfoUserDetails(userInfo, true))
                .orElseThrow(() -> new UsernameNotFoundException(StringConstants.USER_NOT_FOUND + username));
    }
}

