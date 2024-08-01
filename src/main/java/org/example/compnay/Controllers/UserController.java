package org.example.compnay.Controllers;

import org.example.compnay.Config.CustomUserDetailsService;
import org.example.compnay.Entity.Role;
import org.example.compnay.Entity.Ticket;
import org.example.compnay.Entity.User;
import org.springframework.ui.Model;
import org.example.compnay.Repo.BranchRepo;
import org.example.compnay.Repo.RoleRepo;
import org.example.compnay.Repo.UserRepo;
import org.example.compnay.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private BranchRepo branchRepository;

//
//    @PostMapping("/api")
//    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//
//        // Fetch roles from the repository using their IDs
//        Set<Role> roles = userDTO.getRoles().stream()
//                .map(roleDTO -> {
//                    try {
//                        return roleRepository.findById(roleDTO.getId())
//                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleDTO.getId()));
//                    } catch (RuntimeException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .collect(Collectors.toSet());
//
//        user.setRoles(roles);
//
//        // Save user directly with roles
//        User createdUser = userRepository.save(user);
//
//        return ResponseEntity.ok(createdUser);
//    }

    @GetMapping("/ticket")
    public List<Ticket> getdetail(){
       return ticketService.getAllticket();
    }



}


