package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.AppointmentRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.MedicalHistoryRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.AppointmentService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.MedicalNoteService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorWebController {
    private final MedicalNoteService medicalNoteService;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Transactional
    @GetMapping("/doctor/appointments")
    public String viewDoctorAppointments(Model model,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Doctor doctor = userDetails.getUser().getDoctor();
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        model.addAttribute("appointments", appointments);
        model.addAttribute("AppointmentStatus", AppointmentStatus.class);
        return "doctor/appointments";
    }
    @Transactional
    @GetMapping("/doctor/start-appointment/{id}")
    public String viewStartAppointment(@PathVariable Long id, Model model){
        MedicalNote note = new MedicalNote();
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        note.setAppointment(appointment);

        Patient patient = appointment.getPatient();
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findByPatientId(patient.getId());

        model.addAttribute("medicalNote", note);
        model.addAttribute("medicalHistoryList", medicalHistoryList);
        model.addAttribute("appointmentStatus", AppointmentStatus.class);
        return "doctor/start-appointment";
    }
    @PostMapping("/doctor/start-appointment")
    public String saveMedicalNote(@ModelAttribute("medicalNote") MedicalNote note) {
        appointmentService.finishAppointment(note.getAppointment().getId());
        medicalNoteService.save(note);
        return "redirect:/doctor/dashboard";
    }
    @Transactional
    @GetMapping("doctor/appointment-details/{id}")
    public String viewDoctorAppointmentDetails(Model model,
                                                @PathVariable Long id){

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointment", appointment);
        return "doctor/appointment-details";
    }
}
