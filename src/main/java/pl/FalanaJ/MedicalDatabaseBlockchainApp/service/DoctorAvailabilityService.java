package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.DoctorAvailability;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.DoctorAvailabilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public void save(DoctorAvailability doctorAvailability){
        doctorAvailabilityRepository.save(doctorAvailability);
    }

    public List<DoctorAvailability> findAllByDoctor(Doctor doctor){
        return doctorAvailabilityRepository.findByDoctor(doctor);
    }

    public List<DoctorAvailability> findAll(){
        return doctorAvailabilityRepository.findAll();
    }

}
