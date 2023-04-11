package com.greenfoxacademy.springwebapp.login.service;

import com.greenfoxacademy.springwebapp.common.exceptions.LoginFailureException;
import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import com.greenfoxacademy.springwebapp.login.models.TokenResponseDTO;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.admin.services.AdminService;
import com.greenfoxacademy.springwebapp.security.JwtSystemKeys;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

  private AdminService adminService;
  private JwtSystemKeys jwtSystemKeys;

  public LoginServiceImpl(AdminService adminService, JwtSystemKeys jwtSystemKeys) {
    this.adminService = adminService;
    this.jwtSystemKeys = jwtSystemKeys;
  }

  @Override
  public TokenResponseDTO login(LoginDTO loginDTO) throws LoginFailureException {
    Optional<Admin> admin = adminService.findByUsername(loginDTO.getUsername());
    if (!admin.isPresent() || !BCrypt.checkpw(loginDTO.getPassword(), admin.get().getPassword()))
      throw new LoginFailureException();
    String jws = createToken(admin.get());
    return new TokenResponseDTO(jws);
  }

  @Override
  public String createToken(Admin admin) {
    String jws = Jwts.builder()
          .claim("username", admin.getUsername())
          .claim("userId", admin.getId())
          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
          .signWith(jwtSystemKeys.getKey()).compact();
    return jws;
  }

}
