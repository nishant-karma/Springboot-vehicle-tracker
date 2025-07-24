package com.vehicleTracker.vehicleTracker.Repository;


import com.vehicleTracker.vehicleTracker.DTO.LiveVehicleDTO;
import com.vehicleTracker.vehicleTracker.DTO.VehicleLineStringDTO;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

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
    List<VehicleLineStringDTO> getLineStringForVehicle(String vehicleNumber, LocalDateTime from, LocalDateTime to);


    @Aggregation(pipeline = {
            "{ '$match': { 'isActive': true } }",
            "{ '$lookup': { " +
                    "  'from': 'locationUpdate', " +
                    "  'let': { 'vehicleIdStr': { '$toString': '$_id' } }, " +
                    "  'pipeline': [" +
                    "    { '$match': { '$expr': { '$eq': [ '$vehicleId', '$$vehicleIdStr' ] } } }," +
                    "    { '$sort': { 'timestamp': -1 } }," +
                    "    { '$limit': 1 }" +
                    "  ]," +
                    "  'as': 'latestLocation'" +
                    "} }",
            "{ '$unwind': '$latestLocation' }",
            "{ '$project': { " +
                    "  'vehicleNumber': 1, " +
                    "  'longitude': { '$arrayElemAt': [ '$latestLocation.location.coordinates', 0 ] }, " +
                    "  'latitude': { '$arrayElemAt': [ '$latestLocation.location.coordinates', 1 ] }, " +
                    "  'timestamp': '$latestLocation.timestamp' " +
                    "} }"
    })
    List<LiveVehicleDTO> findAllLiveVehiclePositions();



}




