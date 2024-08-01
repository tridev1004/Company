package org.example.compnay.Controllers;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String roleId;
    private String branchId;
}
