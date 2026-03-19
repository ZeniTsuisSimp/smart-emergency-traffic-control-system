package org.SmartEmergencyTrafficControl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_users")
public class VehicleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String vehicleType; // AMBULANCE, POLICE, FIRE_TRUCK

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    public VehicleUser() {
    }

    public VehicleUser(String username, String password, String vehicleType, String registrationNumber) {
        this.username = username;
        this.password = password;
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}