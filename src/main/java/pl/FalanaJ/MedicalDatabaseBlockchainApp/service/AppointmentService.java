package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public Optional<Appointment> getById(Long appointmentId){return appointmentRepository.findById(appointmentId);}
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
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        MedicalHistory history = new MedicalHistory();
        history.setType(MedicalHistoryType.APPOINTMENT);
        history.setDescription("Zaplanowana wizyta u " + availability.getDoctor().getSpeciality() +
                " " + availability.getDoctor().getLastName());
        history.setDate(appointment.getDate());
        history.setAppointment(appointment);
        history.setPatient(user.getPatient());
        history.setCreatedAt(LocalDateTime.now());
        history.setStatus(MedicalHistoryStatus.SCHEDULED);

        doctor.getAppointments().add(appointment);
        patient.getAppointments().add(appointment);
        patient.getMedicalHistories().add(history);

        appointmentRepository.save(appointment);
        doctorAvailabilityRepository.delete(availability);
        log.info("Nowy termin wizyty został zarezerwowany");

    }

    @Transactional
    public void cancelAppointment(Long appointmentId, User user){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wizyty o id: " + appointmentId));

        Patient patient = patientRepository.findWithAppointmentsById(user.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pacjenta"));

        Doctor doctor = doctorRepository.findWithAppointmentsById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono doktora"));

        String doctorFirstName = appointment.getDoctor().getFirstName();
        String doctorLastName = appointment.getDoctor().getLastName();
        String doctorSpeciality = appointment.getDoctor().getSpeciality();

        appointment.setStatus(AppointmentStatus.CANCELED);

        MedicalHistory history = new MedicalHistory();
        history.setType(MedicalHistoryType.APPOINTMENT);
        history.setDescription("Anulowana wizyta u " + doctorFirstName + " " + doctorLastName
                + " (" + doctorSpeciality + ") w dniu " + appointment.getDate());
        history.setDate(appointment.getDate());
        history.setAppointment(appointment);
        history.setPatient(user.getPatient());
        history.setCreatedAt(LocalDateTime.now());
        history.setStatus(MedicalHistoryStatus.CANCELED);

        patient.getMedicalHistories().add(history);
        patient.getAppointments().remove(appointment);
        doctor.getAppointments().remove(appointment);
        appointmentRepository.save(appointment);

        log.info("Wizyta została anulowana przez użytkownika: " + patient.getLastName());
    }
}
