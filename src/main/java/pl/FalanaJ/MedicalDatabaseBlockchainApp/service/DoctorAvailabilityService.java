package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.AppointmentRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.DoctorAvailabilityRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.DoctorRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.PatientRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Slf4j
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

    public Optional<DoctorAvailability> getById(Long id){
        return doctorAvailabilityRepository.findById(id);
    }


}
