package org.SmartEmergencyTrafficControl.model;

public class TrafficLight {
    private String id;
    private String name;
    private Location location;
    private SignalStatus status;
    private String trafficDensity; // "HIGH", "MEDIUM", "LOW"

    public TrafficLight() {
        this.trafficDensity = "LOW"; // Default
    }

    public TrafficLight(String id, String name, Location location, SignalStatus status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
        this.trafficDensity = "LOW";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SignalStatus getStatus() {
        return status;
    }

    public void setStatus(SignalStatus status) {
        this.status = status;
    }

    public String getTrafficDensity() {
        return trafficDensity;
    }

    public void setTrafficDensity(String trafficDensity) {
        this.trafficDensity = trafficDensity;
    }
}