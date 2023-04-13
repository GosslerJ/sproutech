package com.greenfoxacademy.springwebapp.admin.repositories;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

  Optional<Admin> findByUsername(String username);

  List<Admin> findAll();

}
