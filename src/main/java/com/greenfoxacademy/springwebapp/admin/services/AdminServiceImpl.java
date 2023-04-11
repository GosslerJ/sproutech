package com.greenfoxacademy.springwebapp.admin.services;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.greenfoxacademy.springwebapp.admin.repositories.AdminRepository;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;

  public AdminServiceImpl(AdminRepository adminRepository) {
    this.adminRepository = adminRepository;
  }

  @Override
  public AdminResponseDTO saveAdmin(AdminRequestDTO reg)
          throws AlreadyTakenNameException, InvalidPasswordException {
    validateRegistration(reg);
    Admin admin = new Admin(reg.getUsername(), hashPassword(reg.getPassword()));
    adminRepository.save(admin);
    return convert(admin);
  }

  @Override
  public void validateRegistration(AdminRequestDTO reg)
          throws AlreadyTakenNameException, InvalidPasswordException {
    if (adminRepository.findByUsername(reg.getUsername()).isPresent())
      throw new AlreadyTakenNameException("Username is already taken.");
    if (reg.getPassword() == null || reg.getPassword().trim().length() < 8)
      throw new InvalidPasswordException("Password must be at least 8 characters.");
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public AdminResponseDTO convert(Admin admin) {
    if (admin == null) return null;
    AdminResponseDTO responseDTO = new AdminResponseDTO();
    responseDTO.setId(admin.getId());
    responseDTO.setUsername(admin.getUsername());
    return responseDTO;
  }

  @Override
  public Optional<Admin> findByUsername(String username) {
    return adminRepository.findByUsername(username);
  }

}
