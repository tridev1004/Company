package org.example.compnay.Repo;

import org.bson.types.ObjectId;
import org.example.compnay.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo extends MongoRepository<Role, String> {
}
