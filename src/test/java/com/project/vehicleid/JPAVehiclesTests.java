package com.project.vehicleid;
import com.project.vehicleid.model.Vehicle;
import com.project.vehicleid.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class JPAVehiclesTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    VehicleRepository vehicleRepository;

    @Test
    public void find_no_vehicles_if_repository_is_empty() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void store_a_vehicle() {
        Vehicle vehicle = vehicleRepository.save(new Vehicle("Honda", "Hatchback", true));
        assertThat(vehicle).hasFieldOrPropertyWithValue("model", "Honda");
        assertThat(vehicle).hasFieldOrPropertyWithValue("description", "Hatchback");
        assertThat(vehicle).hasFieldOrPropertyWithValue("registered",true);
    }

    @Test
    public void find_all_vehicles() {
        Vehicle vehicle1 = new Vehicle("Bentley", "Coupe", true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("Mercedes", "SUV", true);
        entityManager.persist(vehicle2);
        Vehicle vehicle3 = new Vehicle("Mustang","Sports Car",false);
        entityManager.persist(vehicle3);

        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(4).contains(vehicle1,vehicle2,vehicle3);
    }

    @Test
    public void find_vehicle_by_id() {
        Vehicle vehicle1 = new Vehicle("Ford","SUV",true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("Kia","Hatchback",true);
        entityManager.persist(vehicle2);

        Vehicle foundVehicle = vehicleRepository.findById(vehicle1.getId()).get();
        assertThat(foundVehicle).isEqualTo(vehicle1);
    }

    @Test
    public void find_registered_vehicles() {
        Vehicle vehicle1 = new Vehicle("Alfa Romeo","Coupe",true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("BMW","Sports Car",false);
        entityManager.persist(vehicle2);

        Iterable<Vehicle> vehicles = vehicleRepository.findByRegistered(true);
        assertThat(vehicles).hasSize(2).contains(vehicle1);
    }

    @Test
    public void find_vehicles_by_model() {
        Vehicle vehicle1 = new Vehicle("Alfa Romeo", "Coupe", true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("BMW", "Sports Car", false);
        entityManager.persist(vehicle2);

        Iterable<Vehicle> vehicles = vehicleRepository.findByModelContaining("BMW");
        assertThat(vehicles).hasSize(1).contains(vehicle2);
    }

    @Test
    public void update_vehicle_by_id() {
        Vehicle vehicle1 = new Vehicle("Alfa Romeo","Coupe",true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("BMW","Sports Car",false);
        entityManager.persist(vehicle2);

        Vehicle updatedVehicle = new Vehicle("Updated model", "Updated description",true);

        Vehicle vehicle = vehicleRepository.findById(vehicle1.getId()).get();
        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setDescription(updatedVehicle.getDescription());
        vehicle.setRegistered(updatedVehicle.isRegistered());
        vehicleRepository.save(vehicle);

        Vehicle checkVehicle = vehicleRepository.findById(vehicle1.getId()).get();
        assertThat(checkVehicle.getId()).isEqualTo(vehicle1.getId());
        assertThat(checkVehicle.getModel()).isEqualTo(vehicle1.getModel());
        assertThat(checkVehicle.getDescription()).isEqualTo(vehicle1.getDescription());
        assertThat(checkVehicle.isRegistered()).isEqualTo(vehicle1.isRegistered());
    }

    @Test
    public void delete_vehicle_by_id() {
        Vehicle vehicle1 = new Vehicle("Alfa Romeo","Coupe",true);
        entityManager.persist(vehicle1);
        Vehicle vehicle2 = new Vehicle("BMW","Sports Car",false);
        entityManager.persist(vehicle2);

        vehicleRepository.deleteById(vehicle2.getId());

        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(2).contains(vehicle1);
    }

    @Test
    public void delete_all_vehicles() {
        entityManager.persist(new Vehicle("Audi","Coupe",true));
        entityManager.persist(new Vehicle("Chevrolet","Sports Car",false));

        vehicleRepository.deleteAll();
        assertThat(vehicleRepository.findAll()).isEmpty();
    }
}