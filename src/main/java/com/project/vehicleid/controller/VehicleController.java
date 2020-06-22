package com.project.vehicleid.Controller;

import com.project.vehicleid.Model.Vehicle;
import com.project.vehicleid.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;


    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam(required = false) String model) {
        try {
            List<Vehicle> vehicles = new ArrayList<Vehicle>();

            if (model == null)
                vehicleRepository.findAll().forEach(vehicles::add);
            else
                vehicleRepository.findByModelContaining(model).forEach(vehicles::add);

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") long id) {
        Optional<Vehicle> vehicleData = vehicleRepository.findById(id);

        if (vehicleData.isPresent()) {
            return new ResponseEntity<>(vehicleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> createvehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle _vehicle = vehicleRepository
                    .save(new Vehicle(vehicle.getModel(), vehicle.getDescription(), false));
            return new ResponseEntity<>(_vehicle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") long id, @RequestBody Vehicle vehicle) {
        Optional<Vehicle> vehicleData = vehicleRepository.findById(id);

        if (vehicleData.isPresent()) {
            Vehicle _vehicle = vehicleData.get();
            _vehicle.setModel(vehicle.getModel());
            _vehicle.setDescription(vehicle.getDescription());
            _vehicle.setRegistered(vehicle.isRegistered());
            return new ResponseEntity<>(vehicleRepository.save(_vehicle), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("vehicles/{id}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") long id) {
        try {
            vehicleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/vehicles")
    public ResponseEntity<HttpStatus> deleteAllVehicles() {
        try {
            vehicleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }


    @GetMapping("/vehicles/registered")
    public ResponseEntity<List<Vehicle>> findByRegistered() {
        try {
            List<Vehicle> vehicles = vehicleRepository.findByRegistered(true);

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
