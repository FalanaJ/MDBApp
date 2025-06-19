package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.DoctorAvailability;

import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctor(Doctor doctor);
}
