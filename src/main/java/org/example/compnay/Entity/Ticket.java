package org.example.compnay.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id

    private ObjectId id;

    private String ticketDesc;
    private  String task;
    private String status;

    @DBRef
    private User employee;

    @DBRef
    private User originalEmployee;

    private boolean reassigned;

}
