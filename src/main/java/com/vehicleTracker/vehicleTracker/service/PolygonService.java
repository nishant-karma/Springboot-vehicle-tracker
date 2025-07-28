package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.PolygonDTO;
import com.vehicleTracker.vehicleTracker.DTO.PolygonRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.PolygonRepository;
import com.vehicleTracker.vehicleTracker.exceptions.PolygonNotFoundException;
import com.vehicleTracker.vehicleTracker.model.Polygon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolygonService {

    private final PolygonRepository polygonRepository;

    public PolygonService(PolygonRepository polygonRepository){
        this.polygonRepository = polygonRepository;
    }

    public PolygonDTO savePolygon(PolygonRequestDTO polygonRequestDTO){
        System.out.println("I am being called");
        Polygon polygon = new Polygon();
        polygon.setCoordinates(polygonRequestDTO.getCoordinates());
        polygonRepository.save(polygon);
        return toDTO(polygon);
    }


    public List<PolygonDTO> getPolygons(){

       List<Polygon> polygons = polygonRepository.findAll();
       return polygons.stream().map(p->{
           PolygonDTO polygonDTO = new PolygonDTO();
           polygonDTO.setPolygonId(p.getPolygonId());
           polygonDTO.setType(p.getType());
           polygonDTO.setCoordinates(p.getCoordinates());
           return polygonDTO;
       }).toList();
    }

    public PolygonDTO updatePolygon(String id,PolygonRequestDTO polygonRequestDTO){
        Polygon polygon = polygonRepository.findById(id).orElseThrow(()-> new PolygonNotFoundException("Polygon not found"));
        polygon.setCoordinates(polygonRequestDTO.getCoordinates());
        polygonRepository.save(polygon);

        return toDTO(polygon);
    }

    public PolygonDTO deletePolygon(String id){
        Polygon polygon = polygonRepository.findById(id).orElseThrow(()-> new PolygonNotFoundException("Polygon Not Found"));
        polygon.setDeleted(true);

        polygonRepository.save(polygon);

        return toDTO(polygon);

    }

    public PolygonDTO toDTO(Polygon polygon){
        PolygonDTO polygonDTO = new PolygonDTO();
        polygonDTO.setPolygonId(polygon.getPolygonId());
        polygonDTO.setType(polygon.getType());
        polygonDTO.setCoordinates(polygon.getCoordinates());

        return polygonDTO;

    }
}
