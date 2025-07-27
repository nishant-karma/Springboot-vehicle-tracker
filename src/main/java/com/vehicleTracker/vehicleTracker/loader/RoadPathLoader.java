package com.vehicleTracker.vehicleTracker.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RoadPathLoader {

    private final List<List<List<Double>>> roadPaths = new ArrayList<>();
    private final Random random = new Random();

    @PostConstruct
    public void loadPaths() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/coords.json"); // in src/main/resources
        roadPaths.addAll(mapper.readValue(is, new TypeReference<>() {}));
        System.out.println("✅ Loaded " + roadPaths.size() + " road paths");
    }

    public List<List<List<Double>>> getAllPaths() {
        return roadPaths;
    }

    public List<List<Double>> getRandomPath() {
        return roadPaths.get(random.nextInt(roadPaths.size()));
    }
}
