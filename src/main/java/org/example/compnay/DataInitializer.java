package org.example.compnay;

import org.example.compnay.Entity.Role;
import org.example.compnay.Repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepo roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                    new Role("SUPER_ADMIN"),
                    new Role("ADMIN"),
                    new Role("EMPLOYEE")
            ));
        }
    }
}