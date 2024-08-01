package org.example.compnay.Service;

import org.bson.types.ObjectId;
import org.example.compnay.Entity.Ticket;
import org.example.compnay.Entity.User;
import org.example.compnay.Repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepository;

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }
    public List<Ticket> getAllticket(){
       return  ticketRepository.findAll();
    }

    public void updateTicketStatus(String ticketId, String status) {
        // Convert the ticketId string to ObjectId
        ObjectId objectId = new ObjectId(ticketId);
        // Find the ticket by its ID
        Ticket ticket = ticketRepository.findById(objectId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        // Update the status
        ticket.setStatus(status);
        // Save the updated ticket back to the repository
        ticketRepository.save(ticket);
    }
    public List<Ticket> findByEmployee(User employee) {
        // Fetch tickets assigned to the provided employee
        return ticketRepository.findByEmployee(employee);
    }
    public Map<String, Long> getCompletedTicketsCountByEmployee() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream()
                .filter(ticket -> "completed".equals(ticket.getStatus()))
                .collect(Collectors.groupingBy(
                        ticket -> ticket.getEmployee().getUsername(),
                        Collectors.counting()
                ));
    }
}
