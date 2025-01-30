package com.lucas.employeeManagement.configs;

import com.lucas.employeeManagement.entities.Role;
import com.lucas.employeeManagement.entities.User;
import com.lucas.employeeManagement.repositories.RoleRepository;
import com.lucas.employeeManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class SuperuserConfig implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var roleSuperuser = roleRepository.findByName(Role.Values.SUPERUSER.name());

    var superuser = userRepository.findByUsername("superuser");

    superuser.ifPresentOrElse((user) -> {
      System.out.println("SUPERUSER ALREADY EXIST");
    }, () -> {
      var user = new User();
      user.setUsername("superuser");
      user.setEmail("superuser@email.com");
      user.setPassword(bCryptPasswordEncoder.encode("123"));
      user.setRoles(Set.of(roleSuperuser));
      userRepository.save(user);
    });
  }
}
