package org.example.compnay.Repo;

import org.bson.types.ObjectId;
import org.example.compnay.Entity.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepo extends MongoRepository<Branch, String> {

}
