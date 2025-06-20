package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    //to load data with appointments
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments WHERE p.id = :id")
    Optional<Patient> findWithAppointmentsById(@Param("id") Long id);
}
