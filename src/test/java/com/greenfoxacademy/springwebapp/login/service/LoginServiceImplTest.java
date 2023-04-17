package com.greenfoxacademy.springwebapp.login.service;

import com.greenfoxacademy.springwebapp.common.exceptions.LoginFailureException;
import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import com.greenfoxacademy.springwebapp.login.models.TokenResponseDTO;
import com.greenfoxacademy.springwebapp.security.JwtSystemKeys;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.admin.services.AdminService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceImplTest {
  private LoginService loginService;
  private AdminService adminService;
  private JwtSystemKeys jwtSystemKeys;

  @BeforeEach
  public void setup() {
    adminService = mock(AdminService.class);
    jwtSystemKeys = mock(JwtSystemKeys.class);
    loginService = new LoginServiceImpl(adminService, jwtSystemKeys);
  }

  @Test
  public void testLogin() throws LoginFailureException {
    Admin admin = new Admin("username", BCrypt.hashpw("password", BCrypt.gensalt()));
    when(adminService.findByUsername("username")).thenReturn(Optional.of(admin));

    LoginDTO loginDTO = new LoginDTO("username", "password");

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    when(jwtSystemKeys.getKey()).thenReturn(key);

    TokenResponseDTO tokenResponse = loginService.login(loginDTO);

    String expectedToken = Jwts.builder()
            .claim("username", admin.getUsername())
            .claim("userId", admin.getId())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    assertEquals(expectedToken, tokenResponse.getToken());
  }

  @Test
  public void testLoginInvalidCredentials() {
    when(adminService.findByUsername("nonexistent")).thenReturn(Optional.empty());

    LoginDTO loginDTO = new LoginDTO("nonexistent", "password");

    assertThrows(LoginFailureException.class, () -> loginService.login(loginDTO));
  }

}
