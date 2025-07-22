package com.vehicleTracker.vehicleTracker.DTO;

import com.vehicleTracker.vehicleTracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String email;
    private Role role;
}
