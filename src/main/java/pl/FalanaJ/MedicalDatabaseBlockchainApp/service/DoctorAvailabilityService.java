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

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

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

    @Transactional
    public void reserveAppointment(Long availabilityId, User user){
        DoctorAvailability availability = doctorAvailabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono dostępności"));

        Patient patient = patientRepository.findWithAppointmentsById(user.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pacjenta"));

        Doctor doctor = doctorRepository.findWithAppointmentsById(availability.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono doktora"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(availability.getDoctor());
        appointment.setPatient(user.getPatient());
        appointment.setDate(availability.getDate());
        appointment.setStartTime(availability.getStartTime());
        appointment.setEndTime(availability.getEndTime());

        MedicalHistory history = new MedicalHistory();
        history.setType(MedicalHistoryType.APPOINTMENT);
        history.setDescription("Wizyta u " + availability.getDoctor().getSpeciality() +
                " " + availability.getDoctor().getLastName());
        history.setDate(appointment.getDate());
        history.setAppointment(appointment);
        history.setPatient(user.getPatient());

        doctor.getAppointments().add(appointment);
        patient.getAppointments().add(appointment);
        patient.getMedicalHistories().add(history);

        appointmentRepository.save(appointment);
        doctorAvailabilityRepository.delete(availability);
        log.info("Nowy termin wizyty został zarezerwowany");

    }


}
