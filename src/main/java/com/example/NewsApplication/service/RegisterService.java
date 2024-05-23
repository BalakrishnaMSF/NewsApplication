package com.example.NewsApplication.service;

import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.exception.DuplicateEntryException;
import com.example.NewsApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        Optional<User> mobileExist = userRepository.findByMobileNo(user.getMobileNo());
        Optional<User> emailExist = userRepository.findByEmail(user.getEmail());

        if (mobileExist.isPresent()) {
            throw new DuplicateEntryException(StringConstants.DUPLICATE_MOB);
        } else if (emailExist.isPresent()) {
            throw new DuplicateEntryException(StringConstants.DUPLICATE_EMAIL);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

}
