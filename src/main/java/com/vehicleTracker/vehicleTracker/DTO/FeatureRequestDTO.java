package com.vehicleTracker.vehicleTracker.DTO;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.client.model.geojson.*;
import com.vehicleTracker.vehicleTracker.enums.FeatureType;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureRequestDTO {


    private String featureTypeSetupId;

    @JsonProperty("featureType")
    private FeatureType featureType;

    @JsonProperty("point")

    private List<Double> point;

    @JsonProperty("lineString")
    private List<List<Double>> lineString;


    @JsonProperty("polygon")
    private List<List<List<Double>>>polygon;

    public Geometry getGeometry() {
        switch (featureType) {
            case Point:
                return new Point(new Position(point));
            case LineString:
                return new LineString(lineString.stream()
                        .map(Position::new)
                        .collect(Collectors.toList()));
            case Polygon:
                if (polygon.size() > 1) {
                    switch (polygon.size()) {

                        case 2:
                            return new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(1).stream().map(Position::new).collect(Collectors.toList()));

                        case 3:
                            return new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(1).stream().map(Position::new).collect(Collectors.toList()), polygon.get(2).stream().map(Position::new).collect(Collectors.toList()));
                        case 4:
                            return  new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(1).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(2).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(3).stream().map(Position::new).collect(Collectors.toList()));
                        case 5:
                            return new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(1).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(2).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(3).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(4).stream().map(Position::new).collect(Collectors.toList()));
                        case 6:
                            return new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(1).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(2).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(3).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(4).stream().map(Position::new).collect(Collectors.toList())
                                    , polygon.get(5).stream().map(Position::new).collect(Collectors.toList()));

                    }
                } else {
                    return new Polygon(polygon.get(0).stream().map(Position::new).collect(Collectors.toList()));


                }
        }
        return null;
    }

    public List<Double> reverse(List<Double> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
        return list;
    }
}
