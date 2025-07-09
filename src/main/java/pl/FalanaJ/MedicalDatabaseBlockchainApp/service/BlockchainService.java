package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.MedicalHistoryRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.utils.BlockchainUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockchainService {
    private final MedicalHistoryRepository medicalHistoryRepository;

    public void signMedicalHistory(MedicalHistory history, Patient patient){
        Optional<MedicalHistory> lastMedicalRecord = medicalHistoryRepository.findTopByPatientOrderByCreatedAtDesc(patient);
        String previousHash = lastMedicalRecord.map(MedicalHistory::getHash).orElse("0");
        history.setPreviousHash(previousHash);

        String blockchainHashData = BlockchainUtil.prepareMedicalHistoryData(history, previousHash);
        String hash = BlockchainUtil.generateHash(blockchainHashData);
        history.setHash(hash);
    }
}


