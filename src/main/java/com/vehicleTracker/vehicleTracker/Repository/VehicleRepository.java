package com.vehicleTracker.vehicleTracker.Repository;


import com.vehicleTracker.vehicleTracker.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

}
