package com.vehicleTracker.vehicleTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LoginResponseDTO {

        private String token;

        public LoginResponseDTO setToken(String token) {
            this.token = token;
            return this;
        }
    }

