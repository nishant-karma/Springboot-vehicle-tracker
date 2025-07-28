package com.vehicleTracker.vehicleTracker.Repository;

import com.vehicleTracker.vehicleTracker.model.Polygon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolygonRepository extends MongoRepository<Polygon, String> {
    @Override
    List<Polygon> findAll();
}
