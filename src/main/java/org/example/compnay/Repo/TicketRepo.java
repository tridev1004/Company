package org.example.compnay.Repo;

import org.bson.types.ObjectId;
import org.example.compnay.Entity.Ticket;
import org.example.compnay.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepo extends MongoRepository<Ticket, ObjectId> {

    List<Ticket> findByEmployee(User employee);
}
