package com.vehicleTracker.vehicleTracker.controller;

import com.vehicleTracker.vehicleTracker.DTO.GeometryDTO;
import com.vehicleTracker.vehicleTracker.DTO.GeometryRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.GeometryRepository;
import com.vehicleTracker.vehicleTracker.service.GeometryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/polygons")
public class GeometryController {

    private GeometryRepository geometryRepository;
    private final GeometryService geometryService;

    public GeometryController(GeometryRepository geometryRepository, GeometryService geometryService){
        this.geometryRepository = geometryRepository;
        this.geometryService = geometryService;
    }

    @PostMapping("/save")
    public ResponseEntity<GeometryDTO> savePolygon(@RequestBody GeometryRequestDTO polygonDTO){
        return ResponseEntity.ok(geometryService.savePolygon(polygonDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<GeometryDTO>> getAllPolygons(){
        return ResponseEntity.ok(geometryService.getPolygons());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<GeometryDTO> editPolygon(@PathVariable String id, @RequestBody GeometryRequestDTO geometryRequestDTO){
        return ResponseEntity.ok(geometryService.updatePolygon(id, geometryRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeometryDTO> deletePolygon(@PathVariable String id){
        return ResponseEntity.ok(geometryService.deletePolygon(id));
    }


}
