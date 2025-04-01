package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
