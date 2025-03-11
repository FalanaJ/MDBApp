package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientLoginController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('PATIENT')")
    public String adminDashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "patient/dashboard";
    }
}
