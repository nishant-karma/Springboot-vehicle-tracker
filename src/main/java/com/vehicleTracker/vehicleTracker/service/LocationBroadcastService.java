package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.VehicleLocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class LocationBroadcastService {


    private final SimpMessagingTemplate messagingTemplate;

    public LocationBroadcastService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastLocation(VehicleLocationDTO dto) {
        System.out.println("Broadcasting location: " + dto);
        messagingTemplate.convertAndSend("/topic/locations", dto);
    }
}