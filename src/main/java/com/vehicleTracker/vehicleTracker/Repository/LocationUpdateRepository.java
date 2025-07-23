package com.vehicleTracker.vehicleTracker.Repository;

import com.mongodb.client.model.geojson.Point;
import com.vehicleTracker.vehicleTracker.DTO.VehicleLineStringDTO;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface LocationUpdateRepository extends MongoRepository<LocationUpdate, String> {
    List<LocationUpdate> findTop10ByVehicleIdOrderByTimestampDesc(String vehicleId);
    LocationUpdate findTop1ByVehicleIdOrderByTimestampDesc(String vehicleId);

    @Query("{ 'location': { $nearSphere: { $geometry: ?0, $maxDistance: ?1 } } }")
    List<LocationUpdate> findNearbyVehicles(GeoJsonPoint point, double maxDistance);

    @Aggregation(pipeline = {
            "{ $match: { vehicleNumber: ?0 } }",
            "{ $lookup: { " +
                    "from: 'locationUpdate', " +
                    "let: { vehicleIdStr: { $toString: '$_id' } }, " +
                    "pipeline: [ " +
                    "{ $match: { $expr: { $and: [ " +
                    "{ $eq: ['$vehicleId', '$$vehicleIdStr'] }, " +
                    "{ $gte: ['$timestamp', ?1] }, " +
                    "{ $lte: ['$timestamp', ?2] } " +
                    "] } } }, " +
                    "{ $sort: { timestamp: 1 } }, " +
                    "{ $project: { coordinates: '$location.coordinates', timestamp: 1 } } " +
                    "], " +
                    "as: 'locationUpdate' } }",
            "{ $project: { _id: 0, vehicleId: '$_id', vehicleNumber: 1, " +
                    "lineString: { $map: { input: '$locationUpdate', as: 'loc', in: '$$loc.coordinates' } } } }"
    })
    List<VehicleLineStringDTO> getLineStringForVehicle(String vehicleNumber, Date from, Date to);

}
