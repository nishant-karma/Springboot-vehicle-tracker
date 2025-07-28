package com.vehicleTracker.vehicleTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Polygon {
    @Id
    private String polygonId;

    private String type = "Polygon";

    private List<List<List<Double>>> coordinates;

    private boolean isDeleted = false;


}
