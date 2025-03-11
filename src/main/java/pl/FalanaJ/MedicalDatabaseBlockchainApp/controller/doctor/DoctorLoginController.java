package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorLoginController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('DOCTOR')")
    public String adminDashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "doctor/dashboard";
    }
}
