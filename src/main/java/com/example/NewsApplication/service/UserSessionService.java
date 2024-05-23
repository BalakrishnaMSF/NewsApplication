package com.example.NewsApplication.service;


import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.dto.AuthRequest;
import com.example.NewsApplication.dto.UserSessionDTO;
import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.entity.UserSession;
import com.example.NewsApplication.exception.DuplicateEntryException;
import com.example.NewsApplication.exception.UserNotFoundException;
import com.example.NewsApplication.repository.UserRepository;
import com.example.NewsApplication.repository.UserSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserSessionService {
    @Autowired
    public JwtService jwtService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    public UserSessionRepository userSessionRepository;

    public UserSessionDTO authenticateUser(AuthRequest authRequest) {
        UserSessionDTO userSessionDto = new UserSessionDTO();
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String userName = authRequest.getUsername();
                userSessionDto.setUsername(userName);
                Long id = userRepository.findByMobileNoOrEmail(userName, userName).orElse(new User()).getId();
                userSessionDto.setId(id);
                userSessionDto.setExistingUserSession(userSessionRepository.findByUserId(id));
                return userSessionDto;
            }

        } catch (AuthenticationException e) {
            throw new UserNotFoundException(StringConstants.USER_NOT_FOUND);
        }
        return null;
    }

    public String loginUser(AuthRequest request) {
        UserSessionDTO exisitingUserSessionDto = authenticateUser(request);
        String username = exisitingUserSessionDto.getUsername();
        Long id = exisitingUserSessionDto.getId();
        if (id != null) {
            if (exisitingUserSessionDto.getExistingUserSession() == null) {
                String token = jwtService.generateToken(username);
                UserSession userSession = new UserSession();
                userSession.setUserId(id);
                userSession.setUsername(username);
                userSession.setToken(token);
                userSessionRepository.save(userSession);
                return token;
            } else {
                throw new DuplicateEntryException(StringConstants.LOGGED_IN);
            }
        } else {
            throw new UserNotFoundException(StringConstants.USER_NOT_FOUND);
        }
    }

    public void logoutUser(AuthRequest request) {
        UserSessionDTO existingUserSessionDto = authenticateUser(request);
        Long id = existingUserSessionDto.getId();
        if (id != null) {
            if (existingUserSessionDto.getExistingUserSession() != null) {
                userSessionRepository.deleteByUserId(id);
            } else {
                throw new DuplicateEntryException(StringConstants.USER_NOT_LOGGED_IN);
            }
        } else {
            throw new UserNotFoundException(StringConstants.USER_NOT_LOGGED_IN);
        }
    }

}



