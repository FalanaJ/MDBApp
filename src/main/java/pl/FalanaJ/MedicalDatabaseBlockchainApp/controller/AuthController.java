package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword) {


        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error";
        }

        if (userRepository.findByUsername(email).isPresent()) {
            return "redirect:/register?error";
        }

        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/doctor/dashboard")
    @PreAuthorize("hasRole('DOCTOR')")
    public String doctorDashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "doctor/dashboard";
    }

    @GetMapping("/patient/dashboard")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientDashboard(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        model.addAttribute("user", user);
        return "patient/dashboard";
    }

    @GetMapping("patient/medical-history")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientMedicalHistory(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        if (user.getPatient() != null) {
            model.addAttribute("medicalHistories", user.getPatient().getMedicalHistories());
        } else {
            model.addAttribute("medicalHistories", Collections.emptyList());
        }

        return "patient/medical-history";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "admin/dashboard";
    }
}
