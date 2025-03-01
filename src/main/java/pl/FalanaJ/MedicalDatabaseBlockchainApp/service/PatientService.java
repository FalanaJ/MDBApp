package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }

    public Optional<Patient> findById(Long id){
        return patientRepository.findById(id);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }
}