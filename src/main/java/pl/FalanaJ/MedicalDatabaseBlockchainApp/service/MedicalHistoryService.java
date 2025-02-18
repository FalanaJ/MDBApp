package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.MedicalHistoryRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.PatientRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Transactional
    public void save(MedicalHistory medicalHistory){medicalHistoryRepository.save(medicalHistory);}

    public Optional<MedicalHistory> findById(Long id){
        return medicalHistoryRepository.findById(id);
    }

    public List<MedicalHistory> findAll(){
        return medicalHistoryRepository.findAll();
    }


}