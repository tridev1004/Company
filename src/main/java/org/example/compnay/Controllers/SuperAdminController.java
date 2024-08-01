package org.example.compnay.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.compnay.Config.CustomUserDetailsService;
import org.example.compnay.Entity.Ticket;
import org.example.compnay.Entity.User;
import org.example.compnay.Repo.TicketRepo;
import org.example.compnay.Repo.UserRepo;
import org.example.compnay.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SuperAdminController {
    @Autowired
    private TicketService TicketService;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @PostMapping("/assignTask")
    public String assignTask(
            @RequestParam("task") String task,
            @RequestParam("employee") String employee,
            @RequestParam("ticketDesc") String ticketDesc,
            RedirectAttributes redirectAttributes) {

        Ticket ticket = new Ticket();
        ticket.setTask(task);
        ticket.setTicketDesc(ticketDesc);
        User assignedEmployee = customUserDetailsService.findByUsername(employee); // Assuming a method to get a User object
        ticket.setEmployee(assignedEmployee);

        TicketService.save(ticket);

        redirectAttributes.addFlashAttribute("message", "Ticket has been successfully created!");
        return "redirect:/superadmin";
    }

    @GetMapping("/superadmin")
    public String superAdmin(Model model) throws JsonProcessingException {
        // Fetch all users and filter out SUPER_ADMIN
        List<User> nonSuperAdmins = userRepository.findAll().stream()
                .filter(user -> user.getRoles() != null && user.getRoles().stream()
                        .filter(role -> role != null && role.getName() != null) // Check for null values
                        .noneMatch(role -> role.getName().equals("SUPER_ADMIN")))
                .collect(Collectors.toList());

        // Add the filtered list to the model
        model.addAttribute("employees", nonSuperAdmins);
        List<Ticket> tickets = TicketService.getAllticket(); // Assuming you have a service to fetch all tickets
        model.addAttribute("tickets", tickets);

        Map<String, Long> completedTicketsCount = TicketService.getCompletedTicketsCountByEmployee();
        // Convert Map to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String completedTicketsCountJson = mapper.writeValueAsString(completedTicketsCount);
        model.addAttribute("completedTicketsCountJson", completedTicketsCountJson);
        // Return the view
        return "SuperAdmin.html";
    }


}
