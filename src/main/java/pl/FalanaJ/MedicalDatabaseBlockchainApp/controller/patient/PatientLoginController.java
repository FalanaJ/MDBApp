package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.CustomUserDetailsService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientLoginController {
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientDashboard(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        model.addAttribute("user", user);
        return "patient/dashboard";
    }

    @GetMapping("/medicalHistory")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientMedicalHistory(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        if (user.getPatient() != null) {
            model.addAttribute("medicalHistories", user.getPatient().getMedicalHistories());
        } else {
            model.addAttribute("medicalHistories", Collections.emptyList());
        }

        return "patient/medicalHistory";
    }


}
