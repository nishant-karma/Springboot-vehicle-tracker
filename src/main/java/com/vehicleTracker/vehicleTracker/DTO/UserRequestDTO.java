package com.vehicleTracker.vehicleTracker.DTO;

import com.vehicleTracker.vehicleTracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String email;
    private String password;
    private Role Role;


}
