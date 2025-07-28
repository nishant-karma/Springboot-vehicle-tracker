package com.vehicleTracker.vehicleTracker.controller;

import com.vehicleTracker.vehicleTracker.DTO.PolygonDTO;
import com.vehicleTracker.vehicleTracker.DTO.PolygonRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.PolygonRepository;
import com.vehicleTracker.vehicleTracker.service.PolygonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/polygons")
public class PolygonController {

    private PolygonRepository polygonRepository;
    private final PolygonService polygonService;

    public PolygonController(PolygonRepository polygonRepository, PolygonService polygonService){
        this.polygonRepository = polygonRepository;
        this.polygonService = polygonService;
    }

    @PostMapping("/save")
    public ResponseEntity<PolygonDTO> savePolygon(@RequestBody PolygonRequestDTO polygonDTO){
        return ResponseEntity.ok(polygonService.savePolygon(polygonDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<PolygonDTO>> getAllPolygons(){
        return ResponseEntity.ok(polygonService.getPolygons());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PolygonDTO> editPolygon(@PathVariable String id,  @RequestBody PolygonRequestDTO polygonRequestDTO){
        return ResponseEntity.ok(polygonService.updatePolygon(id, polygonRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PolygonDTO> deletePolygon(@PathVariable String id){
        return ResponseEntity.ok(polygonService.deletePolygon(id));
    }


}
