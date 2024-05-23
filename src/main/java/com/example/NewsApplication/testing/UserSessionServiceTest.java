package com.example.NewsApplication.testing;

import com.example.NewsApplication.dto.AuthRequest;
import com.example.NewsApplication.dto.UserSessionDTO;
import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.entity.UserSession;
import com.example.NewsApplication.repository.UserRepository;
import com.example.NewsApplication.repository.UserSessionRepository;
import com.example.NewsApplication.service.JwtService;
import com.example.NewsApplication.service.UserSessionService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.when;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class UserSessionServiceTest {

    @InjectMocks
    private UserSessionService userSessionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSessionRepository userSessionRepository;

    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    public void testLoginUser_Success() {
        AuthRequest authRequest = new AuthRequest("username", "password");

        UserSessionDTO mockUserSessionDTO = new UserSessionDTO();
        mockUserSessionDTO.setUsername(authRequest.getUsername());
        mockUserSessionDTO.setId(1L);
        mockUserSessionDTO.setExistingUserSession(null);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findByMobileNoOrEmail(authRequest.getUsername(), authRequest.getUsername())).thenReturn(java.util.Optional.of(mockUser));

        JwtService jwtService = Mockito.mock(JwtService.class);
        when(jwtService.generateToken(authRequest.getUsername())).thenReturn("fakeToken");
        userSessionService.jwtService = jwtService;

        String result = userSessionService.loginUser(authRequest);

        Mockito.verify(userSessionRepository, Mockito.times(1)).save(Mockito.any(UserSession.class));
        assert result != null;
        log.info("Login User mock test successful");
    }

    @Test
    public void testLogoutUser_Success() {
        AuthRequest authRequest = new AuthRequest("username", "password");

        UserSessionDTO mockUserSessionDTO = new UserSessionDTO();
        mockUserSessionDTO.setUsername(authRequest.getUsername());
        mockUserSessionDTO.setId(1L);
        mockUserSessionDTO.setExistingUserSession(new UserSession());

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findByMobileNoOrEmail(authRequest.getUsername(), authRequest.getUsername())).thenReturn(java.util.Optional.of(mockUser));

        UserSession mockUserSession = new UserSession();
        mockUserSession.setUserId(1L);
        mockUserSession.setUsername(authRequest.getUsername());
        mockUserSession.setToken("fakeToken");
        when(userSessionRepository.findByUserId(1L)).thenReturn(mockUserSession);

        userSessionService.logoutUser(authRequest);

        Mockito.verify(userSessionRepository, Mockito.times(1)).deleteByUserId(1L);
        log.info("Logout User mock test successful");
    }
}
