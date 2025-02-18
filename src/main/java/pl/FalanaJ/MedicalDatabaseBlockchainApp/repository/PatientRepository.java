package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {}
