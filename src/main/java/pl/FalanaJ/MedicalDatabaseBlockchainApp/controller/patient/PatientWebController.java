package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.AppointmentRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.utils.BlockchainUtil;

import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PatientWebController {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
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
    @GetMapping("patient/medical-history")
    public String patientMedicalHistory(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        System.out.println("Blockchain pacjenta: " + user.getUsername());
        Patient patient = user.getPatient();
        List<MedicalHistory> histories = patient.getMedicalHistories();
        boolean isValid = BlockchainUtil.isChainValid(histories);

        model.addAttribute("medicalHistories", histories);
        model.addAttribute("isBlockchainValid", isValid);

        return "patient/medical-history";
    }
}
