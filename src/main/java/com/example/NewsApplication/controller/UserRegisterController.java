package com.example.NewsApplication.controller;



import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.service.RegisterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(StringConstants.USER)
public class UserRegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping(StringConstants.REGISTER)
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User addedUser = registerService.registerUser(user);
        return ResponseEntity.ok(addedUser);
    }
}



