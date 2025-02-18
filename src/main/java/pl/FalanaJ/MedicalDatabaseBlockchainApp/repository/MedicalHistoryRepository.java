package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {}
