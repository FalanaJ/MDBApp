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

    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MedicalNoteService medicalNoteService;

    private final AppointmentRepository appointmentRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    @GetMapping("/admin/doctor-list")
    public String getAllDoctorsView(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "admin/doctor-list";
    }

    @GetMapping("/admin/add-doctor")
    public String addDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/add-doctor";
    }

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
        return "doctor/start-appointment";
    }

    //TODO tu do zmiany na confirm
    @PostMapping("/doctor/start-appointment-confirm")
    public String saveMedicalNote(@ModelAttribute("medicalNote") MedicalNote note) {
        medicalNoteService.save(note);
        return "redirect:/doctor/dashboard";
    }


    @PostMapping("/admin/add-doctor")
    public String processAddNewDoctorForm(@ModelAttribute("doctor") @Valid Doctor doctor, Errors errors, Model model) {
        if(errors.hasErrors()) return "admin/add-doctor";

        if (userService.existsByUsername(doctor.getEmail())) {
            model.addAttribute("errorMessage", "Użytkownik o podanym adresie e-mail już istnieje.");
            return "admin/add-doctor";
        }

        User user = new User();
        user.setUsername(doctor.getEmail());
        user.setPassword(passwordEncoder.encode(doctor.getPeselNumber())); // TODO TYMCZASOWE!
        user.setRole(Role.DOCTOR);

        doctor.setUser(user);
        user.setDoctor(doctor);

        doctor.setCreatedAt(new Date());
        doctorService.save(doctor);

        log.info("Nowy doktor został dodany: " + doctor);
        return "admin/doctor-registered";
    }
}
