package com.greenfoxacademy.springwebapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.admin.services.AdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private JwtSystemKeys jwtSystemKeys;
  private AdminService adminService;
  private ObjectMapper objectMapper;

  @Autowired
  public JwtFilter(JwtSystemKeys jwtSystemKeys, AdminService adminService) {
    this.jwtSystemKeys = jwtSystemKeys;
    this.adminService = adminService;
    this.objectMapper = new ObjectMapper();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException, ResponseStatusException {
    if (shouldNotFilter(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      authenticationError(response);
      return;
    }
    String jwt = request.getHeader("Authorization").substring(7).trim();
    try {
      setAuthToSecurityContext(jwt);
    } catch (Exception e) {
      authorizationError(response);
      return;
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthToSecurityContext(String jwt) {
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(getAdminFromToken(jwt, jwtSystemKeys.getKey()), null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private void authorizationError(HttpServletResponse response) {
    try {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getOutputStream()
          .println(objectMapper.writeValueAsString(new ErrorDTO("Authentication token is invalid!")));
    } catch (Exception e) {
      System.err.println("Unable to send `Authentication token is invalid!` error");
    }
  }

  private void authenticationError(HttpServletResponse response) {
    try {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getOutputStream()
          .println(objectMapper.writeValueAsString(new ErrorDTO("No authentication token is provided!")));
    } catch (Exception e) {
      System.err.println("Unable to send `No authentication token is provided!` error");
    }
  }

  private Admin getAdminFromToken(String jwt, SecretKey secretKey) throws JwtException, NoSuchElementException {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
    if (claims.getExpiration().before(new Date())) throw new RuntimeException("Token is expired.");
    String username = String.valueOf(claims.get("username"));
    Optional<Admin> admin = adminService.findByUsername(username);
    if (!admin.isPresent()) {
      throw new NoSuchElementException("No such admin");
    }
    return admin.get();
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String reqPath = request.getServletPath();
    return (!reqPath.startsWith("/api"));
  }

}
