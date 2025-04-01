package pl.FalanaJ.MedicalDatabaseBlockchainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {}
