package org.example.compnay.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.compnay.Entity.Role;
import org.example.compnay.Entity.User;
import org.example.compnay.Repo.BranchRepo;
import org.example.compnay.Repo.RoleRepo;
import org.example.compnay.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginAccess {
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private BranchRepo branchRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/login")
    public String access() {
        return "index.html";
    }

    @GetMapping("/Userlogin")
    public String userLog() {
        return "login.html";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("SUPER_ADMIN")) {
            return "redirect:/superadmin";
        } else if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        } else if (request.isUserInRole("EMPLOYEE")) {
            return "redirect:/employee";
        } else {
            return "redirect:/";
        }
    }
//
//    @GetMapping("/superadmin")
//    public String superAdmin() {
//        return "SuperAdmin.html";
//    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin.html";
    }

//    @GetMapping("/employee")
//    public String employee() {
//        return "employee";
//    }




    @GetMapping("/signuphere")
    public String showSignupForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("branches", branchRepository.findAll());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("branches", branchRepository.findAll());
            return "signup";
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        // Check if roleId is provided
        // Validate and set Role
        if (userDTO.getRoleId() == null || userDTO.getRoleId().isEmpty()) {
            result.rejectValue("roleId", "error.userDTO", "Role is required.");
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("branches", branchRepository.findAll());
            return "signup";
        }
        roleRepository.findById(userDTO.getRoleId()).ifPresentOrElse(role -> {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }, () -> {
            result.rejectValue("roleId", "error.userDTO", "Invalid role selected.");
        });

        // Validate and set Branch
        if (userDTO.getBranchId() != null && !userDTO.getBranchId().isEmpty()) {
            branchRepository.findById(userDTO.getBranchId()).ifPresent(user::setBranch);
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("branches", branchRepository.findAll());
            return "signup";
        }

        userRepository.save(user);
        return "redirect:/Userlogin";
    }
}
