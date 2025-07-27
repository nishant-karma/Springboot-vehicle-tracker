package com.vehicleTracker.vehicleTracker.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoadDataService {
    private final List<List<double[]>> roadPaths = new ArrayList<>();

    public RoadDataService() throws Exception {
        loadPathsFromJson();
    }

    private void loadPathsFromJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = new ClassPathResource("coords.json").getInputStream();

        List<List<List<Double>>> raw = mapper.readValue(is, List.class);
        for (List<List<Double>> line : raw) {
            List<double[]> path = new ArrayList<>();
            for (List<Double> point : line) {
                path.add(new double[]{point.get(0), point.get(1)}); // lon, lat
            }
            roadPaths.add(path);
        }

        System.out.println("✅ Loaded " + roadPaths.size() + " road paths from coords.json");
    }

    public List<List<double[]>> getRoadPaths() {
        return roadPaths;
    }
}
