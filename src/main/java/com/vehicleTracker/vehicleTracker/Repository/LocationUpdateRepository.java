package com.vehicleTracker.vehicleTracker.Repository;

import com.mongodb.client.model.geojson.Point;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationUpdateRepository extends MongoRepository<LocationUpdate, String> {
    List<LocationUpdate> findTop10ByVehicleIdOrderByTimestampDesc(String vehicleId);
    LocationUpdate findTop1ByVehicleIdOrderByTimestampDesc(String vehicleId);

    @Query("{ 'location': { $nearSphere: { $geometry: ?0, $maxDistance: ?1 } } }")
    List<LocationUpdate> findNearbyVehicles(GeoJsonPoint point, double maxDistance);


}
