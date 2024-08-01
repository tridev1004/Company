package org.example.compnay.Repo;

import org.bson.types.ObjectId;
import org.example.compnay.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
}
