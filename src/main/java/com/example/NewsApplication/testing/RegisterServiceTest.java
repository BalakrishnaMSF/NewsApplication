package com.example.NewsApplication.testing;

import com.example.NewsApplication.entity.User;
import com.example.NewsApplication.exception.InValidPasswordException;
import com.example.NewsApplication.repository.UserRepository;
import com.example.NewsApplication.service.RegisterService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        User user = new User();
        user.setMobileNo("1234567890");
        user.setEmail("test@example.com");
        user.setPassword("Passw@rd1");

        when(userRepository.findByMobileNo(user.getMobileNo())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        try {
            User registeredUser = registerService.registerUser(user);
            Mockito.verify(userRepository).save(user);

            assertEquals("encodedPassword", registeredUser.getPassword());
        } catch (InValidPasswordException e) {
            fail("Valid password, should not throw InValidPasswordException");
        }
    }
}
