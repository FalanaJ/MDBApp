package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.AppointmentRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PatientWebController {

    private final PatientService patientService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;
    @GetMapping("admin/patient-list")
    public String getAllPatientsView(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "admin/patient-list";
    }

    @GetMapping("admin/add-patient")
    public String AddNewPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "admin/add-patient";
    }

    @PostMapping("admin/add-patient")
    public String processAddNewPatientForm(@ModelAttribute("patient") @Valid Patient patient, Errors errors, Model model) {
        if(errors.hasErrors()) return "admin/add-patient";

        if (userService.existsByUsername(patient.getEmail())) {
            model.addAttribute("errorMessage", "Użytkownik o podanym adresie e-mail już istnieje.");
            return "admin/add-patient";
        }

        User user = new User();
        user.setUsername(patient.getEmail());
        user.setPassword(passwordEncoder.encode(patient.getPeselNumber())); // TODO TYMCZASOWE!
        user.setRole(Role.PATIENT);

        patient.setUser(user);
        user.setPatient(patient);

        patient.setCreatedAt(new Date());
        patientService.save(patient);

        log.info("Nowy pacjent został dodany: " + patient);
        return "admin/patient-registered";
    }

    @Transactional
    @GetMapping("patient/appointments")
    public String viewPatientAppointments(@RequestParam(defaultValue = "date") String sort,
                                          @RequestParam(defaultValue = "asc") String dir,
                                          Model model,
                                          @AuthenticationPrincipal CustomUserDetails userDetails){
        Patient patient = userDetails.getUser().getPatient();
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);

        Comparator<Appointment> comparator = switch (sort) {
            case "doctor" -> Comparator.comparing(a -> a.getDoctor().getLastName(), String.CASE_INSENSITIVE_ORDER);
            case "status" -> Comparator.comparing(Appointment::getStatus);
            case "time" -> Comparator.comparing(Appointment::getStartTime);
            default -> Comparator.comparing(Appointment::getDate);
        };

        if (dir.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        appointments.sort(comparator);

        model.addAttribute("appointments", appointments);
        model.addAttribute("AppointmentStatus", AppointmentStatus.class);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "patient/appointments";
    }

    @Transactional
    @GetMapping("patient/appointment-details/{id}")
    public String viewPatientAppointmentDetails(Model model,
                                                @PathVariable Long id){

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointment", appointment);
        return "patient/appointment-details";
    }
}
