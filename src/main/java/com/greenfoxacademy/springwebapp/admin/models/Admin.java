package com.greenfoxacademy.springwebapp.admin.models;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String email = "";

  public Admin(String username, String password) {
    this.username = username;
    this.password = password;
  }

}