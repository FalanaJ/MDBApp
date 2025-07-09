package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPatientId(Long patientId);
    Optional<MedicalHistory> findTopByPatientOrderByCreatedAtDesc(Patient patient);

}
