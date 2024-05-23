package com.example.NewsApplication.controller;

import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.dto.AuthRequest;
import com.example.NewsApplication.service.UserSessionService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(StringConstants.SESSION)
public class UserSessionController {

    @Autowired
    UserSessionService userSessionService;

    @PostMapping(StringConstants.LOGIN)
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JSONException {
        String token = userSessionService.loginUser(authRequest);
        return ResponseEntity.ok(token);
    }


    @PostMapping(StringConstants.LOGOUT)
    public ResponseEntity<String> userLogout(@RequestBody AuthRequest authRequest) {
        userSessionService.logoutUser(authRequest);
        return ResponseEntity.ok(StringConstants.USER_LOGOUT);
    }
}
