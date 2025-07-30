package com.vehicleTracker.vehicleTracker.controller;

import com.vehicleTracker.vehicleTracker.DTO.FeatureResponseDTO;
import com.vehicleTracker.vehicleTracker.DTO.FeatureRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.FeatureRepository;
import com.vehicleTracker.vehicleTracker.service.FeatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/features")
public class FeatureController {

    private FeatureRepository featureRepository;
    private final FeatureService featureService;

    public FeatureController(FeatureRepository featureRepository, FeatureService featureService){
        this.featureRepository = featureRepository;
        this.featureService = featureService;
    }

    @PostMapping("/save")
    public ResponseEntity<FeatureResponseDTO> savePolygon(@RequestBody FeatureRequestDTO featureDTO){
        return ResponseEntity.ok(featureService.saveFeature(featureDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<FeatureResponseDTO>> getAllPolygons(){
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FeatureResponseDTO> editPolygon(@PathVariable String id, @RequestBody FeatureRequestDTO featureRequestDTO){
        return ResponseEntity.ok(featureService.updateFeature(id, featureRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FeatureResponseDTO> deletePolygon(@PathVariable String id){
        return ResponseEntity.ok(featureService.deleteFeature(id));
    }


}
