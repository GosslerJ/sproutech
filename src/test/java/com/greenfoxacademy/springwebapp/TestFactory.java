  //package com.greenfoxacademy.springwebapp;
  //
  //import com.greenfoxacademy.springwebapp.building.models.Building;
  //import com.greenfoxacademy.springwebapp.kingdom.models.Kingdom;
  //import com.greenfoxacademy.springwebapp.admin.models.Admin;
  //import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  //
  //import java.util.ArrayList;
  //import java.util.Arrays;
  //
  //import static com.greenfoxacademy.springwebapp.building.models.BuildingType.*;
  //
  //public class TestFactory {
  //
  //  public static final UsernamePasswordAuthenticationToken playerWith0Resources() {
  //    Kingdom kingdom = Kingdom.builder()
  //            .id(1201)
  //            .buildingList(new ArrayList<>(Arrays.asList(
  //                    new Building(1205, TOWNHALL, 4, 100, 123456, 1234567),
  //                    new Building(1206, ACADEMY, 1, 100, 123456, 1234567)
  //            )))
  //            .build();
  //    Admin admin = Admin.builder()
  //            .id(1201)
  //            .kingdom(kingdom)
  //            .build();
  //    return new UsernamePasswordAuthenticationToken(admin, null, null);
  //  }
  //
  //}
