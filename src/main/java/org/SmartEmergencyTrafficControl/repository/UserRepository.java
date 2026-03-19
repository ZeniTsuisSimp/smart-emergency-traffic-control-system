package org.SmartEmergencyTrafficControl.repository;

import org.SmartEmergencyTrafficControl.model.VehicleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<VehicleUser, Long> {
    Optional<VehicleUser> findByUsername(String username);
}