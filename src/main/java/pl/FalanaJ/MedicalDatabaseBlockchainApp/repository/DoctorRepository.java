package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
