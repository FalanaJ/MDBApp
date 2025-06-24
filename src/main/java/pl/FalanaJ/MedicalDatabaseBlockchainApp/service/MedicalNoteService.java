package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalNote;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.MedicalNoteRepository;

@Service
@RequiredArgsConstructor
public class MedicalNoteService {
    private final MedicalNoteRepository medicalNoteRepository;

    public void save(MedicalNote medicalNote){
        medicalNoteRepository.save(medicalNote);
    }
}
