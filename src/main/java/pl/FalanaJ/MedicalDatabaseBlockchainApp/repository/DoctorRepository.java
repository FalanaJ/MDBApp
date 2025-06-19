package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
}
