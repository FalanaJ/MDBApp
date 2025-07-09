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
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.PatientRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.BlockchainService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.utils.BlockchainUtil;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
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
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        model.addAttribute("user", user);
        return "doctor/dashboard";
    }

    @GetMapping("/patient/dashboard")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientDashboard(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + principal.getName()));

        Patient patientWithAppointments = patientRepository.findWithAppointmentsById(user.getPatient().getId())
                        .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Appointment> upcomingAppointments = patientWithAppointments.getAppointments().stream()
                .filter(x -> x.getStatus() == AppointmentStatus.SCHEDULED)
                .sorted(Comparator.comparing(Appointment::getDate)
                        .thenComparing(Appointment::getStartTime))
                .limit(4)
                .toList();

        model.addAttribute("appointments", upcomingAppointments);
        model.addAttribute("user", user);
        return "patient/dashboard";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "admin/dashboard";
    }
}
