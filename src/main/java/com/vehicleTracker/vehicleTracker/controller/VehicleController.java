package com.vehicleTracker.vehicleTracker.controller;


import com.vehicleTracker.vehicleTracker.DTO.VehicleLocationDTO;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import com.vehicleTracker.vehicleTracker.service.LocationBroadcastService;
import com.vehicleTracker.vehicleTracker.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final LocationBroadcastService locationBroadcastService;

    public VehicleController(VehicleService vehicleService, LocationBroadcastService locationBroadcastService) {
        this.vehicleService = vehicleService;
        this.locationBroadcastService = locationBroadcastService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle){
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateLocation(@RequestBody VehicleLocationDTO dto) {
        locationBroadcastService.broadcastLocation(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/latest-location")
    public ResponseEntity<LocationUpdate> getLatest(@PathVariable String id){
        return ResponseEntity.ok(vehicleService.getLatestLocation(id));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<LocationUpdate>> getNearBy(@RequestParam double lon, @RequestParam double lat , @RequestParam(defaultValue = "5000") double radius){
        return ResponseEntity.ok(vehicleService.getNearByVehicles(lon,lat,radius));
    }



}

