package org.SmartEmergencyTrafficControl.service;

import org.SmartEmergencyTrafficControl.model.Location;
import org.SmartEmergencyTrafficControl.model.SignalStatus;
import org.SmartEmergencyTrafficControl.model.TrafficLight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class TrafficService {

    private final List<TrafficLight> trafficLights = new ArrayList<>();
    
    // Default thresholds
    private static final double HIGH_DENSITY_THRESHOLD = 800.0; // Clear traffic earlier
    private static final double LOW_DENSITY_THRESHOLD = 300.0;  // Standard
    
    // Store the previous distance of the ambulance to each traffic light to determine direction
    private final Map<String, Double> previousDistances = new HashMap<>();
    
    // Timestamp of the last processed location update to prevent out-of-order processing
    private long lastLocationTimestamp = 0;

    public TrafficService() {
        // Initial dummy data (will be overwritten by frontend)
        addLight("1", "Demo Light 1", 30.271591, 77.048158);
    }

    private void addLight(String id, String name, double lat, double lon) {
        TrafficLight light = new TrafficLight(id, name, new Location(lat, lon), SignalStatus.RED);
        light.setTrafficDensity("LOW"); // Default
        trafficLights.add(light);
    }

    // --- New Methods for Dynamic Route Support ---

    public void resetTrafficLights() {
        trafficLights.clear();
        previousDistances.clear();
        System.out.println("All traffic lights cleared.");
    }

    public void addTrafficLightsBatch(List<TrafficLight> newLights) {
        for (TrafficLight light : newLights) {
            if (light.getId() == null) {
                light.setId(UUID.randomUUID().toString());
            }
            if (light.getStatus() == null) {
                light.setStatus(SignalStatus.RED);
            }
            if (light.getTrafficDensity() == null) {
                light.setTrafficDensity("LOW");
            }
            trafficLights.add(light);
        }
        System.out.println("Added " + newLights.size() + " new traffic lights from route.");
    }

    // ---------------------------------------------

    public void updateAmbulanceLocation(Location ambulanceLocation) {
        // Check for out-of-order updates
        if (ambulanceLocation.getTimestamp() > 0) {
            if (ambulanceLocation.getTimestamp() < lastLocationTimestamp) {
                return;
            }
            lastLocationTimestamp = ambulanceLocation.getTimestamp();
        }

        for (TrafficLight light : trafficLights) {
            double currentDistance = calculateDistance(ambulanceLocation, light.getLocation());
            
            // Determine dynamic threshold based on density
            double effectiveThreshold = "HIGH".equalsIgnoreCase(light.getTrafficDensity()) 
                                        ? HIGH_DENSITY_THRESHOLD 
                                        : LOW_DENSITY_THRESHOLD;

            if (currentDistance <= effectiveThreshold) {
                if (light.getStatus() != SignalStatus.GREEN) {
                    light.setStatus(SignalStatus.GREEN);
                    System.out.println("Emergency! Ambulance approaching " + light.getName() + 
                                       " (Density: " + light.getTrafficDensity() + "). " +
                                       "Distance: " + currentDistance + "m -> GREEN");
                }
            } else {
                // Turn RED if passed (distance increasing or simply outside range)
                // Simple logic: if outside range, turn RED.
                if (light.getStatus() == SignalStatus.GREEN) {
                    light.setStatus(SignalStatus.RED);
                    System.out.println("Ambulance passed " + light.getName() + ". Distance: " + currentDistance + "m -> RED");
                }
            }
            previousDistances.put(light.getId(), currentDistance);
        }
    }

    public SignalStatus getSignalStatus() {
        return trafficLights.isEmpty() ? SignalStatus.RED : trafficLights.get(0).getStatus();
    }

    public List<TrafficLight> getAllTrafficLights() {
        return trafficLights;
    }

    public TrafficLight addTrafficLight(TrafficLight light) {
        if (light.getId() == null) {
            light.setId(UUID.randomUUID().toString());
        }
        if (light.getStatus() == null) {
            light.setStatus(SignalStatus.RED);
        }
        if (light.getTrafficDensity() == null) {
            light.setTrafficDensity("LOW");
        }
        trafficLights.add(light);
        return light;
    }

    public void setTrafficDensity(String density) {
        for (TrafficLight light : trafficLights) {
            light.setTrafficDensity(density);
        }
        System.out.println("Traffic Density set to " + density + " for all lights.");
    }
    
    public void setTrafficDensityForLight(String lightId, String density) {
        for (TrafficLight light : trafficLights) {
            if (light.getId().equals(lightId)) {
                light.setTrafficDensity(density);
                System.out.println("Traffic Density set to " + density + " for " + light.getName());
                return;
            }
        }
    }
    
    public void randomizeTrafficDensities() {
        String[] densities = {"LOW", "MEDIUM", "HIGH"};
        Random rand = new Random();
        for (TrafficLight light : trafficLights) {
            String density = densities[rand.nextInt(densities.length)];
            light.setTrafficDensity(density);
        }
    }

    private double calculateDistance(Location loc1, Location loc2) {
        final int R = 6371; 
        double latDistance = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double lonDistance = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }
}