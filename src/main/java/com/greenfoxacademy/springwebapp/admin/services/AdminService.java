package com.greenfoxacademy.springwebapp.admin.services;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;

import java.util.Optional;

public interface AdminService {

  AdminResponseDTO saveAdmin(AdminRequestDTO adminRequestDTO)
          throws AlreadyTakenNameException, InvalidPasswordException;

  void validateRegistration(AdminRequestDTO reg) throws AlreadyTakenNameException, InvalidPasswordException;

  String hashPassword(String password);

  AdminResponseDTO convert(Admin admin);

  Optional<Admin> findByUsername(String username);

}