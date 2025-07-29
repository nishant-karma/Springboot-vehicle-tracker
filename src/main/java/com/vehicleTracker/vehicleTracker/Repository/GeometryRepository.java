package com.vehicleTracker.vehicleTracker.Repository;

import com.vehicleTracker.vehicleTracker.model.Geometry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeometryRepository extends MongoRepository<Geometry, String> {
    @Override
    List<Geometry> findAll();
}
