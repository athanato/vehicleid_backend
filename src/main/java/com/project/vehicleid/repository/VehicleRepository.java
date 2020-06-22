package com.project.vehicleid.Repository;

import com.project.vehicleid.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
//public interface VehicleRepository extends JpaRepository<Vehicle, String> {}

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByRegistered(boolean registered);

    List<Vehicle> findByModelContaining(String model);
}