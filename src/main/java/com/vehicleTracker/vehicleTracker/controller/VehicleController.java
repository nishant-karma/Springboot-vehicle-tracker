package com.vehicleTracker.vehicleTracker.controller;


import com.vehicleTracker.vehicleTracker.DTO.LiveVehicleDTO;
import com.vehicleTracker.vehicleTracker.DTO.VehicleLocationDTO;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import com.vehicleTracker.vehicleTracker.service.LocationBroadcastService;
import com.vehicleTracker.vehicleTracker.service.VehicleService;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/path")
    public ResponseEntity<GeoJsonLineString> getVehiclePath(
            @RequestParam String vehicleNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {



        GeoJsonLineString path = vehicleService.getVehiclePathAsLineString(vehicleNumber, from, to);
        return ResponseEntity.ok(path);
    }

    @GetMapping("/live")
    public ResponseEntity<List<LiveVehicleDTO>> getAllLiveVehiclePositions() {
        List<LiveVehicleDTO> list = vehicleService.getAllLiveVehicleData();
        System.out.println("From Controller; " + list);
        return ResponseEntity.ok(list);
    }





}

