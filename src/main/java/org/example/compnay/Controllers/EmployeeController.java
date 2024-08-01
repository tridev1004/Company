package org.example.compnay.Controllers;

import org.example.compnay.Config.CustomUserDetailsService;
import org.example.compnay.Entity.Ticket;
import org.example.compnay.Entity.User;
import org.example.compnay.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping("/dashboard")
    public String employeeDashboard(Model model) {
        // Get the currently logged-in user's username
        String username = getCurrentUsername();

        // Fetch the user details using the username
        User currentUser = userService.findByUsername(username);

        // Fetch the tickets assigned to the current user
        List<Ticket> tickets = ticketService.findByEmployee(currentUser);

        // Add tickets to the model
        model.addAttribute("tickets", tickets);

        // Return the view for the employee dashboard
        return "employee";
    }

    private String getCurrentUsername() {
        // Get the authentication object
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @PostMapping("/updateTicketStatus")
    public String updateTicketStatus(@RequestParam("ticketId") String ticketId,
                                     @RequestParam("status") String status) {
        ticketService.updateTicketStatus(ticketId, status);
        return "redirect:/employee/dashboard";
    }

    @GetMapping
    public String employee() {
        // Redirect to the dashboard by default
        return "redirect:/employee/dashboard";
    }
}
