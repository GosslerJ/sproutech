package com.greenfoxacademy.springwebapp.login.service;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import com.greenfoxacademy.springwebapp.login.models.TokenResponseDTO;

public interface LoginService {

  TokenResponseDTO login(LoginDTO loginDTO);

  String createToken(Admin admin);

}
